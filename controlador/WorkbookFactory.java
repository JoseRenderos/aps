package controlador;

import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Workbook;

class WorkbookFactory {

    static Workbook create(InputStream fileContent) throws IOException {
        return org.apache.poi.ss.usermodel.WorkbookFactory.create(fileContent);
    }
}
