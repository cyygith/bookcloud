package com.elling.book.sys.pdfflying;

import java.io.File;

import freemarker.template.Configuration;

public class FreemarkerConfiguration {
	private static Configuration config = null;
	static {
		config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		config.setClassForTemplateLoading(FreemarkerConfiguration.class, "/pdfTemplate/");
//		config.setDirectoryForTemplateLoading(new File(fullPath));
	}

	public static Configuration getConfiguation() {
		return config;
	}

}
