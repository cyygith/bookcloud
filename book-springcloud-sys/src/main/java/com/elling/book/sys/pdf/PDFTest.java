package com.elling.book.sys.pdf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class PDFTest {
	
	
	private static final String DEST = "target/HelloWorld3.pdf";
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
	
	
	
	/**
     * freemarker渲染html
     */
    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = freemarkerCfg.getTemplate(htmlTmp);
            template.setEncoding("UTF-8");
            // 合并数据模型与模板
            template.process(data, out); //将合并后的数据和模板写入到流中，这里使用的字符流
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
	
	
	
	public static void createPdf(String content,String dest) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        // step 3
        document.open();
        // step 4
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new ByteArrayInputStream(content.getBytes()), null, Charset.forName("UTF-8"), fontImp);
        // step 5
        document.close();
 
    }
	
	
	public static void main(String[] args) throws IOException, DocumentException {
        Map<String,Object> data = new HashMap();
        data.put("name","西歪歪");
        String content = PDFTest.freeMarkerRender(data,HTML);
        PDFTest.createPdf(content,DEST);
        
        System.out.println("操作成功~~~");
    }
}
