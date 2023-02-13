package lab.lab4other.Auto;

import lab4.Config.DistributerConfig;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import lab4.Helper.XMLHelper;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class JExcelHelper {

    public static void main(String[] args) {
        handler();
    }

    public static void handler() {
        try (FileInputStream file = new FileInputStream("C:\\Users\\gamza\\IdeaProjects\\MAC\\src\\main\\resources\\Load.xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("Sheet");
            Cell cell = null;
            List<Double> listz = new ArrayList<>();
            List<Double> firstListz, secondListz, thirdListz;
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    if (Objects.requireNonNull(cell.getCellType()) == CellType.NUMERIC) {
                        listz.add(cell.getNumericCellValue());
                    }
                }
            }
            firstListz = listz.subList(0, 24);
            secondListz = listz.subList(24, 48);
            thirdListz = listz.subList(48, 72);
            Parameters firstParameters = new Parameters();
            Parameters secondParameters = new Parameters();
            Parameters thirdParameters = new Parameters();
            assert cell != null;
            firstParameters.setFirstPower(firstListz);
            secondParameters.setSecondPower(secondListz);
            thirdParameters.setThirdPower(thirdListz);
            firstParameters.setDistributer("firstDistributer");
            secondParameters.setDistributer("secondDistributer");
            thirdParameters.setDistributer("thirdDistributer");
            // create
            XMLHelper.marshalAny(firstParameters, "src/main/resources/residentialBuildingConsumer.xml");
            XMLHelper.marshalAny(secondParameters, "src/main/resources/colormetConsumer.xml");
            XMLHelper.marshalAny(thirdParameters, "src/main/resources/foodIndustryConsumer.xml");

            DistributerConfig distributerConfig = new DistributerConfig();
            distributerConfig.setProducersName("residentialBuildingConsumer");
            distributerConfig.setTopicName("topic1");
            XMLHelper.marshalAny(distributerConfig, "firstDistributer.xml");
            distributerConfig.setProducersName("secondDistributer");
            distributerConfig.setTopicName("topic2");
            XMLHelper.marshalAny(distributerConfig, "secondDistributer.xml");
            distributerConfig.setProducersName("thirdDistributer");
            distributerConfig.setTopicName("topic3");
            XMLHelper.marshalAny(distributerConfig, "thirdDistributer.xml");

            // parser
//            File xml = new File(args);
//            JAXBContext jaxbContext = JAXBContext.newInstance(Parameters.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            Parameters object = (Parameters) jaxbUnmarshaller.unmarshal(xml);
//
//            for (Double def : object.getFirstPower()){
//                List<Parameters> listParameters = new ArrayList<>();
//                listParameters.add(firstParameters);
////                def.setFile(firstListz);
//                System.out.println(def + " def");
//                System.out.println(listParameters + " list");
//            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
