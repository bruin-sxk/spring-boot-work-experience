package com.sxk.common.fomat.util;

import com.sxk.common.fomat.pdf.Html2Pdf;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class PdfExportUtil {


	/**
	 * 通过HTML模板生成PDF(不支持高级CSS样式)
	 * <p>
	 * html模板注意事项：
	 * <p>
	 * 1.charset属性设置，windows下使用GBK，linux使用UTF-8，否则会出现乱码
	 * <p>
	 * 2.font-family属性设置，必须为font-family:SimSun;注意大小写，不能是SIMSUN或simsun，否则生成文件中文为空白
	 *
	 * @param data
	 * @param templateRoot
	 * @param htmlTemplateName
	 * @param fontPath
	 * @param charSet
	 * @param waterMarkPath
	 * @return
	 */
	public static OutputStream exportPdfByHtml(Map<String, Object> data, String templateRoot, String htmlTemplateName,
			String fontPath, String charSet, String waterMarkPath) {
		Assert.hasText(templateRoot, "模板根目录不能为空");
		Assert.hasText(htmlTemplateName, "模板名字不能为空");
		Assert.hasText(fontPath, "字体地址不能为空");
		File file = new File(fontPath);
		Assert.isTrue(file.exists(), "模板文件不存在");
		Assert.hasText(charSet,"字符集编码不能为空");
		return Html2Pdf.createPdf(data, templateRoot, htmlTemplateName, fontPath, charSet, waterMarkPath);
	}


	/**
	 * 通过HTML模板生成PDF(支持高级CSS样式，如：position)
	 * <p>
	 * html模板注意事项：
	 * <p>
	 * 1.charset属性设置，windows下使用GBK，linux使用UTF-8，否则会出现乱码
	 * <p>
	 * 2.font-family属性设置，必须为font-family:SimSun;注意大小写，不能是SIMSUN或simsun，否则生成文件中文为空白
	 * <p>
	 * 3.img标签src使用file协议，如：src="file:图片路径"/
	 *
	 * @param dataMap
	 * @param templateFileName
	 * @param templateInputStream
	 * @param fontPath
	 * @param charSet
	 * @param waterMarkPath
	 * @return
	 */
	public static OutputStream createPdfByHtml(Map<String, Object> dataMap, String templateFileName,
			InputStream templateInputStream, String fontPath, String charSet, String waterMarkPath) {

		File file = new File(fontPath);

		return Html2Pdf.createPdfByInputStream(dataMap, templateFileName, templateInputStream, fontPath, charSet,
				waterMarkPath);

	}

}
