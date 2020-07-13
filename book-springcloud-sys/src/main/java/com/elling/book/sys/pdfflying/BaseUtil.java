package com.elling.book.sys.pdfflying;

import java.io.File;
import java.io.FileInputStream;

public class BaseUtil {
	/**
	 *  @描述：文件转byte[]
	 *  @返回：byte[]         
	 *  @时间：2020年7月13日 下午12:33:58       
	 */

	public static byte[] file2byte(File file) {
			FileInputStream fileInputStream = null;
			byte[] bFile = null;
			try {
				bFile = new byte[(int) file.length()];
				fileInputStream = new FileInputStream(file);
				fileInputStream.read(bFile);
			} catch (Exception e) {
	//			logger.error("", e);
				e.printStackTrace();
			} finally {
				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch (Exception e) {
	//					logger.error("", e);
						e.printStackTrace();
					}
				}
			}
			return bFile;
		}
}
