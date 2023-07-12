package com.phuclq.student.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.multipart.MultipartFile;

public class SplitDocs {

  public static void main(String base64, String name) {

    FileInputStream in = null;
    HWPFDocument doc = null;

    XWPFDocument us = null;
    XWPFDocument japan = null;
    FileOutputStream outUs = null;
    FileOutputStream outJapan = null;

    try {
      java.io.File file = convertMultiPartToFile(uploadFile(base64,name));
      in = new FileInputStream(file);
      doc = new HWPFDocument(in);

      us = new XWPFDocument();
      japan = new XWPFDocument();

      Range range = doc.getRange();

      for (int parIndex = 0; parIndex < range.numParagraphs(); parIndex++) {
        Paragraph paragraph = range.getParagraph(parIndex);

        String text = paragraph.text();
        System.out.println("***Paragraph" + parIndex + ": " + text);

        if ((parIndex >= 11) && (parIndex <= 20)) {
          createParagraphInAnotherDocument(us, text);
        } else if ((parIndex >= 21) && (parIndex <= 30)) {
          createParagraphInAnotherDocument(japan, text);
        }
      }

      outUs = new FileOutputStream("us.docx");
      outJapan = new FileOutputStream("japan.docx");
      us.write(outUs);
      japan.write(outJapan);

      in.close();
      outUs.close();
      outJapan.close();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static void createParagraphInAnotherDocument(XWPFDocument document, String text) {
    XWPFParagraph newPar = document.createParagraph();
    newPar.createRun().setText(text, 0);
  }

  private static java.io.File convertMultiPartToFile(MultipartFile file) throws IOException {
    java.io.File convFile = new java.io.File(file.getOriginalFilename());
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }
  public static MultipartFile uploadFile(String base64, String fileName) {
    final String[] base64Array = base64.split(",");
    String dataUir, data;
    if (base64Array.length > 1) {
      dataUir = base64Array[0];
      data = base64Array[1];
    } else {
      //Build according to the specific file you represent
      dataUir = "data:image/jpg;base64";
      data = base64Array[0];
    }

    return new Base64ToMultipartFile(dataUir+","+data, dataUir,fileName);
  }
}
