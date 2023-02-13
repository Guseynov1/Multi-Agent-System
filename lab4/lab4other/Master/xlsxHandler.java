package lab.lab4other.Master;

import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * Класс для импорта данных из xlsx-файлов
 */
@Data
public class xlsxHandler {

    /**
     * Метод, импортирующий график нагрузки потребителя из xlsx-файла в xml
     */
    public static void importLoad(String load) {
        File file = new File("C:\\Users\\gamza\\IdeaProjects\\MAC\\src\\main\\java\\lab\\lab4\\resources\\Load.xlsx");
        XSSFWorkbook workBook = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            // Finds the workbook instance for XLSX file
            workBook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return first sheet from the XLSX workbook
        assert workBook != null;
        XSSFSheet mySheet = workBook.getSheet("Sheet");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Meeting.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Meeting object = (Meeting) jaxbUnmarshaller.unmarshal(new File(load));;
            for (Definition def : object.getDefinitions()) {
                ArrayList<Double> list = new ArrayList<>();
                Iterator<Row> rowIterator = mySheet.iterator();
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
//                    System.out.println(cell.getStringCellValue());

                    if (Objects.equals(cell.getNumericCellValue(), def.getAgent())) {
                        int colin = cell.getColumnIndex();

                        // Traversing over each row of XLSX file
                        while (rowIterator.hasNext()) {
                            Row row1 = rowIterator.next();
                            Cell cell1 = row1.getCell(colin);
                            if (cell1.getCellType() == CellType.NUMERIC) list.add(cell1.getNumericCellValue());
                        }
                    }
                }
//                def.setFile(list);
            }
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(object, new File(load));

        } catch (JAXBException | ClassCastException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, импортирующий график объема производства электроэнергии ЭЭ из xlsx-файла в xml
     */
    public static void importPower(String power) {

        File file = new File("C:\\Users\\gamza\\IdeaProjects\\MAC\\src\\main\\java\\lab\\lab4\\resources\\Power.xlsx");
        XSSFWorkbook myWorkBook = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            myWorkBook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert myWorkBook != null;
        XSSFSheet mySheet = myWorkBook.getSheet("Sheet");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Meeting.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Meeting object = (Meeting) jaxbUnmarshaller.unmarshal(new File(power));;

            for (Definition def : object.getDefinitions()) {
                Iterator<Row> rowIterator = mySheet.iterator();
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getStringCellValue().equals(def.getAgent())) {
                        int colin = cell.getColumnIndex();

                        // Traversing over each row of XLSX file
                        while (rowIterator.hasNext()) {
                            Row row1 = rowIterator.next();
                            Cell cell1 = row1.getCell(colin);

                            if (cell1.getCellType() == CellType.NUMERIC) {
//                                def.getFile().add(cell1.getNumericCellValue());
                            }
                        }
                    }
                }
            }
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(object, new File(power));

        } catch (JAXBException | ClassCastException e) {
            e.printStackTrace();
        }
    }
}
