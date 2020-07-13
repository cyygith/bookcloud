package com.elling.book.sys.pdfflying;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.PageSize;

public class PdfTest {
	public static void main(String[] args) throws Exception {
		System.out.println("开始打印~~~");
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String outputFile = "D:\\TESTPDF3.pdf";// 生成后的路径
			Map<String, Object> dataMap = new HashMap<String, Object>();

			List<String> titleList = Arrays.asList("属性1", "属性2", "属性3", "属性4", "属性5", "属性6", "属性7");
			dataMap.put("titleList", titleList);

			List<List<String>> dataList = new ArrayList<List<String>>();
			for (int i = 0; i < 100; i++) {
				dataList.add(Arrays.asList("数据1_" + i, "数据2_" + i, "数据3_数据3_数据3_数据3_数据3_数据3_数据3_数据3_数据3_" + i,
						"数据4_" + i, "数据5_" + i, "数据6_" + i, "数据7_" + i));
			}
			dataMap.put("dataList", dataList);

			// File water = new File("C:\\Users\\zhongjy\\Desktop\\test123\\water.png");
			Generator.pdfGeneratePlus("house.ftl", dataMap, outputFile, PageSize.A4, "", true, null);
			mp.put("code", "200");
			mp.put("url", outputFile);
			System.out.println("打印成功~~~");
		} catch (Exception ex) {
			ex.printStackTrace();
			mp.put("code", "500");
		}

	}
}
