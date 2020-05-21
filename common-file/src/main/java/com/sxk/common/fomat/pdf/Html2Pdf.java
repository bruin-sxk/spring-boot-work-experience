package com.sxk.common.fomat.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

@Slf4j
public class Html2Pdf {

	private static final String BASE_URL = "file:///";

	public static OutputStream createPdf(Map<String, Object> data, String templateRoot, String htmlTemplate,
			String fontPath, String charSet, String waterMarkPath) {
		Configuration freemarkerCfg = initFreemarkerCfg(templateRoot);
		String content = freeMarkerRender(data, freemarkerCfg, htmlTemplate, charSet);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, output);

			PDFBuilder builder = new PDFBuilder(fontPath, waterMarkPath);
			writer.setPageEvent(builder);

			document.open();

			XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
			fontImp.register(fontPath);
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(content.getBytes()),
					Charset.forName(charSet), fontImp);

			document.close();
			return output;
		} catch (Exception ex) {
			log.error("html转换pdf失败!", ex);
			throw new RuntimeException("html转换pdf失败");
		}
	}

	private static String freeMarkerRender(Map<String, Object> data, Configuration freemarkerCfg, String htmlTemplate,
			String charSet) {
		try (Writer out = new StringWriter()) {
			Template template = freemarkerCfg.getTemplate(htmlTemplate);
			template.setEncoding(charSet);
			template.process(data, out);
			out.flush();
			return out.toString();
		} catch (Exception e) {
			log.error("HTML加载数据失败!", e);
			throw new RuntimeException("HTML加载数据失败");
		}
	}

	private static Configuration initFreemarkerCfg(String templateRoot) {
		Configuration freemarkerCfg = new Configuration();
		try {
			freemarkerCfg.setDirectoryForTemplateLoading(new File(templateRoot));
		} catch (IOException e) {
			log.error("模板根路径获取失败!" + templateRoot, e);
			throw new RuntimeException("模板根路径获取失败");
		}
		return freemarkerCfg;
	}


	/**
	 * 支持高级 CSS 样式的转换方法
	 */
	public static OutputStream createPdfByInputStream(Map<String, Object> dataMap, String templateFileName,
			InputStream templateInputStream, String fontPath, String charSet, String waterMarkPath) {
		String htmlContent = freeMarkerRender(dataMap, templateFileName, templateInputStream, charSet);
		ByteArrayOutputStream output = htmlToPdf(htmlContent, fontPath);
		return addWaterImage(output, waterMarkPath, fontPath);
	}

	private static OutputStream addWaterImage(ByteArrayOutputStream outputStream, String waterMarkPath, String fontPath) {
		if (StringUtils.isBlank(waterMarkPath)) {
			return outputStream;
		}
		com.lowagie.text.pdf.BaseFont baseFont = createFont(fontPath);
		try (InputStream input = new ByteArrayInputStream(outputStream.toByteArray());) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			PdfReader reader = new PdfReader(input);
			PdfStamper stamp = new PdfStamper(reader, output);
			PdfContentByte contentByte = null;
			int n = reader.getNumberOfPages();
			Image logo = Image.getInstance(waterMarkPath);
			for (int i = 1; i <= n; i++) {
				contentByte = stamp.getUnderContent(i);
				Rectangle rectangle = reader.getPageSize(i);
				float width = rectangle.getWidth();
				float height = rectangle.getHeight();
				logo.setAbsolutePosition(width / 2 - logo.getWidth() / 2, height / 2);
				contentByte.addImage(logo);

				contentByte.saveState();
				String text = "第 " + i + " 页 /共 " + n + " 页";
				contentByte.beginText();
				contentByte.setFontAndSize(baseFont, 12);
				contentByte.showTextAligned(Element.ALIGN_CENTER, text, (width / 2) - 6, 15, 0);
				contentByte.endText();
			}
			reader.close();
			stamp.close();
			return output;
		} catch (Exception e) {
			log.debug("添加水印和页码失败," + waterMarkPath, e);
			throw new RuntimeException("添加水印和页码失败");
		}

	}

	private static com.lowagie.text.pdf.BaseFont createFont(String fontPath) {
		try {
			return com.lowagie.text.pdf.BaseFont
					.createFont(fontPath, com.lowagie.text.pdf.BaseFont.IDENTITY_H, com.lowagie.text.pdf.BaseFont.NOT_EMBEDDED);
		} catch (Exception e) {
			log.debug("字符集读取失败," + fontPath, e);
			throw new RuntimeException("字符集读取失败");
		}
	}

	private static ByteArrayOutputStream htmlToPdf(String htmlContent, String fontPath) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ITextRenderer render = new ITextRenderer();
			ITextFontResolver fontResolver = render.getFontResolver();
			fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			render.setDocumentFromString(htmlContent);
			render.getSharedContext().setBaseURL(BASE_URL);
			render.layout();
			render.createPDF(output);
			return output;
		} catch (Exception e) {
			log.debug("html转换pdf失败!", e);
			throw new RuntimeException("html转换pdf失败");
		}

	}

	private static String freeMarkerRender(Map<String, Object> dataMap, String templateFileName,
			InputStream templateInputStream, String charSet) {
		try (Writer out = new StringWriter();
				InputStreamReader inputStreamReader = new InputStreamReader(templateInputStream);) {
			Configuration configuration = new Configuration();
			Template template = new Template(templateFileName, inputStreamReader, configuration);
			template.setEncoding(charSet);
			template.process(dataMap, out);
			out.flush();
			return out.toString();
		} catch (Exception e) {
			log.debug("HTML加载数据失败!", e);
			throw new RuntimeException("HTML加载数据失败");
		}
	}
}
