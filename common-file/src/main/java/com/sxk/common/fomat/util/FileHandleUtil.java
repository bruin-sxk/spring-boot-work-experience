package com.sxk.common.fomat.util;

public class FileHandleUtil {

	public static String getResourceFilePath(String fileName) {
		return FileHandleUtil.class.getResource(fileName).getPath();
	}
}
