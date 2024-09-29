package utcluj.isp.curs3.appocr;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * This class is used to demonstrate the use of the Tesseract OCR engine.
 * The Tesseract engine is used to recognize text from images.
 * The Tesseract engine is available at https://github.com/UB-Mannheim/tesseract/wiki
 */
public class Demo {
    public static void main(String[] args) throws TesseractException {
        File image = new File("c:\\Work\\_UTCN\\exemple-isp-2023\\exemple-curs4-oopdetails\\nr5.jpg");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("c:\\Program Files\\Tesseract-OCR\\tessdata\\");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_AUTO);
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result = tesseract.doOCR(image);
        System.out.println(result);
    }
}
