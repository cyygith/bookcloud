package com.elling.book.sys.pdfflying;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.util.StringUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.net.FileRetrieve;
import com.itextpdf.tool.xml.net.ReadingProcessor;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.itextpdf.tool.xml.pipeline.html.ImageProvider;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Generator {
	/**
	 *
	 * @描述：生成html
	 *
	 * @返回：String
	 *
	 * @时间：2020年7月13日 下午12:33:58
	 */
	public static String htmlGenerate(String template, Map<String, Object> variables) throws Exception {
		Configuration config = FreemarkerConfiguration.getConfiguation();
		Template tp = config.getTemplate(template);
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		tp.process(variables, writer);
		String htmlStr = stringWriter.toString();
		writer.flush();
		writer.close();
		return htmlStr;
	}

	public static void pdfGenerate(String htmlStr, OutputStream out) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		org.w3c.dom.Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes()));
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(doc, null);
		renderer.layout();
		renderer.createPDF(out);
		out.close();
	}

	/**
	 *
	 * @描述：生成pdf
	 *
	 * @返回：void
	 *
	 *
	 * @时间：2020年7月13日 下午12:33:58
	 */
	public static void pdfGeneratePlus(String htmlTemplate, Map<String, Object> dataMap, String targetPdf,
			Rectangle pageSize, String header, boolean isFooter, File watermark) throws Exception {
		/**
		 * 根据freemarker模板生成html
		 */
		String htmlStr = htmlGenerate(htmlTemplate, dataMap);
		final String charsetName = "UTF-8";
		Document document = new Document(pageSize);

		OutputStream out = new FileOutputStream(targetPdf);
		/**
		 * 设置边距
		 */
		// document.setMargins(30, 30, 30, 30);
		PdfWriter writer = PdfWriter.getInstance(document, out);
		/**
		 * 添加页码
		 */
		PDFBuilder builder = new PDFBuilder(header, 10, pageSize, watermark, isFooter);
		writer.setPageEvent(builder);

		document.open();

		/**
		 * html内容解析
		 */
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(new CssAppliersImpl(new XMLWorkerFontProvider() {
			@Override
			public Font getFont(String fontname, String encoding, float size, final int style) {
				if (fontname == null) {
					/**
					 * 操作系统需要有该字体, 没有则需要安装; 当然也可以将字体放到项目中， 再从项目中读取
					 */
					fontname = "STSong-Light";
					encoding = "UniGB-UCS2-H";
//					fontname = "SIMHEI";
//					encoding = "UTF-8";
//					以下这种方式是使用已有的字体，上面那种方式使用的是jar包里面的字体，在windows上面没问题，
//					-》在linux下如果没有安装字体的话就会报错，找不到对应的字体信息
//					fontname = "/pdfTemplate/SIMHEI.TTF,1";//（字体的存放位置，这个,1不能去掉）
//					encoding = BaseFont.IDENTITY_H;//字体的编码
				}
				Font font = null;
				try {
					font = new Font(BaseFont.createFont(fontname, encoding, BaseFont.NOT_EMBEDDED), size, style);
				} catch (Exception e) {
//					log.error("", e);
					e.printStackTrace();
				}
				return font;
			}
		})) {
			@Override
			public HtmlPipelineContext clone() throws CloneNotSupportedException {
				HtmlPipelineContext context = super.clone();
				ImageProvider imageProvider = this.getImageProvider();
				context.setImageProvider(imageProvider);
				return context;
			}
		};

		/**
		 * 图片解析
		 */
		htmlContext.setImageProvider(new AbstractImageProvider() {

			String rootPath = "C:\\Users\\Administrator\\Desktop\\刘亦菲\\";

			@Override
			public String getImageRootPath() {
				return rootPath;
			}

			@Override
			public Image retrieve(String src) {
				if (StringUtils.isEmpty(src)) {
					return null;
				}
				try {
					Image image = Image.getInstance(new File(rootPath, src).toURI().toString());
					/**
					 * 图片显示位置
					 */
					image.setAbsolutePosition(400, 400);
					if (image != null) {
						store(src, image);
						return image;
					}
				} catch (Exception e) {
//					log.error("", e);
					e.printStackTrace();
				}
				return super.retrieve(src);
			}
		});
		htmlContext.setAcceptUnknown(true).autoBookmark(true).setTagFactory(Tags.getHtmlTagProcessorFactory());

		/**
		 * css解析
		 */
		CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
		cssResolver.setFileRetrieve(new FileRetrieve() {
			@Override
			public void processFromStream(InputStream in, ReadingProcessor processor) throws IOException {
				try (InputStreamReader reader = new InputStreamReader(in, charsetName)) {
					int i = -1;
					while (-1 != (i = reader.read())) {
						processor.process(i);
					}
				} catch (Throwable e) {
				}
			}

			/**
			 * 解析href
			 */
			@Override
			public void processFromHref(String href, ReadingProcessor processor) throws IOException {
				InputStream is = new ByteArrayInputStream(href.getBytes());
				try {
					InputStreamReader reader = new InputStreamReader(is, charsetName);
					int i = -1;
					while (-1 != (i = reader.read())) {
						processor.process(i);
					}
				} catch (Exception e) {
//					log.error("", e);
					e.printStackTrace();
				}

			}
		});

		HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, new PdfWriterPipeline(document, writer));
		Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
		XMLWorker worker = null;
		worker = new XMLWorker(pipeline, true);
		XMLParser parser = new XMLParser(true, worker, Charset.forName(charsetName));
		try (InputStream inputStream = new ByteArrayInputStream(htmlStr.getBytes())) {
			parser.parse(inputStream, Charset.forName(charsetName));
		}
		document.close();
	}
}
