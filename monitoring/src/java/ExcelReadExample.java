
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hendro Steven
 */
public class ExcelReadExample {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {

        String filename = "D:/X3 Solution/Monitoring/kbli - gabung.xls";


        List sheetData = new ArrayList();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                List data = new ArrayList();
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    data.add(cell);
                }
                sheetData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        showExelData(sheetData);
    }

    private static void showExelData(List sheetData) {
        //
        // Iterates the data and print it out to the console.
        //
        for (int i = 0; i < sheetData.size(); i++) {
            List list = (List) sheetData.get(i);
            HSSFCell cell = (HSSFCell) list.get(0);
            System.out.print(cell.getRichStringCellValue().getString());
            cell = (HSSFCell) list.get(1);
            System.out.print(cell.getRichStringCellValue().getString());
//            for (int j = 0; j < list.size(); j++) {
//                HSSFCell cell = (HSSFCell) list.get(j);
//                System.out.print(cell.getRichStringCellValue().getString());
////                if (j < list.size() - 1) {
////                    System.out.print(", ");
////                }
//            }
            System.out.println("");
        }
    }
}

