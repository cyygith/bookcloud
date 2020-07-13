package com.elling.book.sys.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import freemarker.template.Configuration;

public class PDFTemplateUtilFlying {
	
	private static final String DEST = "target/HelloWorld5.pdf";
	private static final String FONT = "pdfTemplate/SIMHEI.TTF"; 
	private static final String HTML = "/house.ftl";
	
	private static Configuration freemarkerCfg = null;
	  
    static {
        freemarkerCfg =new Configuration();
        //freemarker的模板目录
        try {
        	String htmlPath = System.getProperty("user.dir");
            String path = "/src/main/resources/pdfTemplate";
            String fullPath = htmlPath + path;
            freemarkerCfg.setDirectoryForTemplateLoading(new File(fullPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
    public static void main(String[] args) throws IOException,FileNotFoundException, DocumentException {
        System.out.println("导出开始....");
        String htmlPath = System.getProperty("user.dir");
        String path = "/src/main/resources/pdfTemplate";
        String fullPath = htmlPath + path + HTML;
        System.out.println("html地址："+fullPath);
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        // step 3
        document.open();
        // step 4
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
       
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,new FileInputStream(fullPath), null, Charset.forName("UTF-8"), fontImp);
        // step 5
        document.close();
        
        
        System.out.println("操作成功！");
    }
    
    //文件流导出
//    public static void main(String[] args) throws FileNotFoundException, DocumentException {
//        System.out.println("导出开始....");
//    	Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
//        document.open();
//        Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//        document.add(new Paragraph("hello world,我是城管！abc123333",f1));
//        document.close();
//        writer.close();
//        System.out.println("操作成功！");
//    }
	
}
