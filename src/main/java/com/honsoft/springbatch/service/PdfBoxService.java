package com.honsoft.springbatch.service;

import java.awt.Image;
import java.io.File;
import java.io.FileFilter;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PdfBoxService {
	private static Logger log = LoggerFactory.getLogger(PdfBoxService.class);

	public static File createPdf(String dirPath, String fileName) throws Exception {
		// Directory Check
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		if (!dir.isDirectory()) {
			throw new Exception("dirPath is not dir");
		}
		// File Check
		File pdfFile = new File(dirPath + File.separator + fileName + ".pdf");
		if (pdfFile.exists()) {
			pdfFile.delete();
		}
		PDDocument document = new PDDocument();
		document.save(pdfFile);
		document.close();
		System.out.println("PDF created");
		return pdfFile;
	}

	public static void images2Pdf(String sourceDir, String saveTargetDir, String savePdfFileName) throws Exception {
		log.debug(" -- Loading All Image Files --");
		File dir = new File(sourceDir);
		log.debug("\n\n -- Filtering Image Files --");
		File[] sourceFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().contains("jpg");
			}
		});
		log.debug("\n\n -- Create New PDF --");
		// Loading an existing document
		File pdfFile = createPdf(saveTargetDir, savePdfFileName);
		PDDocument doc = PDDocument.load(pdfFile);
		int totalCnt = 1;
		for (File curImgFile : sourceFiles) {
			// Read a Image Object
			Image curImg = ImageIO.read(curImgFile);
			float imgWidth = curImg.getWidth(null);
			float imgHeigth = curImg.getHeight(null);
			// Fit a PDF Page by Image Height Length
			PDImageXObject pdImage = PDImageXObject.createFromFileByContent(curImgFile, doc);
			PDRectangle newRect = new PDRectangle(0, 0, imgWidth, imgHeigth);
			PDPage newPage = new PDPage(newRect);
			doc.addPage(newPage);
			// Write a PDImageXObject to PDF
			PDPageContentStream contents = new PDPageContentStream(doc, newPage);
			contents.drawImage(pdImage, 0, 0, imgWidth, imgHeigth);
			contents.close();
			log.debug("Page " + (totalCnt++) + " was Drawed at PDF File.");
		}
		// Saving the document
		doc.save(pdfFile);
		// Closing the document
		doc.close();
	}

	public static void main(String[] args) throws Exception {
		String sourceDir, saveTargetDir, savePdfFileName;
		sourceDir = "C:\\Users\\changnamgo\\OneDrive\\??????\\???????????? ?????? ??????";
		saveTargetDir = "c:\\examples";
		savePdfFileName = "test";
		images2Pdf(sourceDir, saveTargetDir, savePdfFileName);
	}
}
