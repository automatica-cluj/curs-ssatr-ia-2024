package utcluj.aut;

import java.io.File;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OcrDemo {
    public static void main(String[] args) throws TesseractException {
        File image = new File("nr3.jpg");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("c:\\Program Files (x86)\\Tesseract-OCR\\tessdata\\");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_AUTO);
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result = tesseract.doOCR(image);
        System.out.println(result);
    }
}
