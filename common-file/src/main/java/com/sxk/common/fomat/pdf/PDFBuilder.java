package com.sxk.common.fomat.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

@Slf4j
public class PDFBuilder extends PdfPageEventHelper {

	/**
	 *  Ò³Ã¼ÐÅÏ¢
	 */
	public String header = "";

	public int presentFontSize = 12;

	public Rectangle pageSize = PageSize.A4;

	public PdfTemplate total;

	public BaseFont baseFont = null;

	public Font fontDetail = null;

	/**
	 *  Ë®Ó¡µØÖ·
	 */
	public String waterMarkPath;

	/**
	 *  ×ÖÌåµØÖ·
	 */
	public String fontPath;

	public PDFBuilder() {}

	public PDFBuilder(String fontPath, String waterMarkPath) {
		this.fontPath = fontPath;
		this.waterMarkPath = waterMarkPath;
	}

	public PDFBuilder(String yeMei, int presentFontSize, Rectangle pageSize) {
		this.header = yeMei;
		this.presentFontSize = presentFontSize;
		this.pageSize = pageSize;
	}

	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		total = writer.getDirectContent().createTemplate(50, 50);
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		this.addPage(writer, document);
		if (StringUtils.isNotBlank(waterMarkPath)) {
			this.addWatermark(writer);
		}
	}

	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		total.beginText();
		total.setFontAndSize(baseFont, presentFontSize);
		String foot2 = " " + (writer.getPageNumber()) + "Ò³";
		total.showText(foot2);
		total.endText();
		total.closePath();
	}

	private void addWatermark(PdfWriter writer) {
		try {
			Image image = Image.getInstance(waterMarkPath);
			PdfContentByte content = writer.getDirectContentUnder();
			content.beginText();
			image.setAbsolutePosition(125, 300);
			content.addImage(image);
			content.endText();
		} catch (IOException | DocumentException ex) {
			log.error("Ë®Ó¡Ìí¼ÓÊ§°Ü," + waterMarkPath, ex);
			throw new RuntimeException("Ë®Ó¡Ìí¼ÓÊ§°Ü");
		}
	}

	private void addPage(PdfWriter writer, Document document) {
		try {
			if (baseFont == null) {
				baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
			if (fontDetail == null) {
				fontDetail = new Font(baseFont, presentFontSize, Font.NORMAL);
			}
		} catch (Exception ex) {
			log.error("×Ö·û¼¯¶ÁÈ¡Ê§°Ü," + fontPath, ex);
			throw new RuntimeException("×Ö·û¼¯¶ÁÈ¡Ê§°Ü");
		}

		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(header, fontDetail),
				document.left(), document.top() + 20, 0);

		int pageNumber = writer.getPageNumber();
		String foot1 = "µÚ" + pageNumber + "Ò³ /¹²";
		Phrase footer = new Phrase(foot1, fontDetail);

		float len = baseFont.getWidthPoint(foot1, presentFontSize);

		PdfContentByte contentByte = writer.getDirectContent();

		ColumnText.showTextAligned(contentByte, Element.ALIGN_CENTER, footer,
				(document.rightMargin() + document.right() + document.leftMargin() - document.left() - len) / 2.0F
						+ 20F,
				document.bottom() - 20, 0);

		contentByte.addTemplate(total,
				(document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F + 20F,
				document.bottom() - 20);
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setPresentFontSize(int presentFontSize) {
		this.presentFontSize = presentFontSize;
	}
}
