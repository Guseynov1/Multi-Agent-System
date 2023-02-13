//package lab.lab4new.Auto;
//
//import jakarta.xml.bind.JAXBContext;
//import jakarta.xml.bind.JAXBException;
//import jakarta.xml.bind.Unmarshaller;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import pr4.serialization.XmlHelper;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Objects;
//
//public class JExcelHelper {
//
//    public static void main(String[] args) {
//        handler("C:\\Users\\gamza\\IdeaProjects\\MAC\\src\\main\\resources\\firstConsumer.xml");
//    }
//
//    public static void handler(String args) {
//        try (FileInputStream file = new FileInputStream("C:\\Users\\gamza\\IdeaProjects\\MAC\\src\\main\\resources\\Load.xlsx")) {
//            XSSFWorkbook workbook = new XSSFWorkbook(file);
//            XSSFSheet sheet = workbook.getSheet("Sheet");
//            Cell cell = null;
//            List<Double> listz = new ArrayList<>();
//            List<Double> firstListz, secondListz, thirdListz;
//            for (Row row : sheet) {
//                Iterator<Cell> cellIterator = row.cellIterator();
//                while (cellIterator.hasNext()) {
//                    cell = cellIterator.next();
//                    if (Objects.requireNonNull(cell.getCellType()) == CellType.NUMERIC) {
//                        System.out.println(cell.getNumericCellValue());
//                        listz.add(cell.getNumericCellValue());
//                    }
//                }
//            }
//            firstListz = listz.subList(0, 24);
//            secondListz = listz.subList(24, 48);
//            thirdListz = listz.subList(48, 72);
//            Parameters firstParameters = new Parameters();
//            Parameters secondParameters = new Parameters();
//            Parameters thirdParameters = new Parameters();
//            assert cell != null;
//            firstParameters.setPower(firstListz);
////            secondParameters.setSecondPower(secondListz);
////            thirdParameters.setThirdPower(thirdListz);
//            // create
//            XmlHelper.marshalAny(firstParameters, "src/main/resources/firstConsumer.xml");
//            XmlHelper.marshalAny(secondParameters, "src/main/resources/secondConsumer.xml");
//            XmlHelper.marshalAny(thirdParameters, "src/main/resources/thirdConsumer.xml");
//
//            // parser
//            File xml = new File(args);
//            JAXBContext jaxbContext = JAXBContext.newInstance(Parameters.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            Parameters object = (Parameters) jaxbUnmarshaller.unmarshal(xml);
//
//            for (Double def : object.getPower()){
//                List<Parameters> listParameters = new ArrayList<>();
//                listParameters.add(firstParameters);
////                def.setFile(firstListz);
//                System.out.println(def + " def");
//                System.out.println(listParameters + " list");
//            }
//        }
//
//        catch (IOException | JAXBException e) {
//            e.printStackTrace();
//        }
//    }
//}
