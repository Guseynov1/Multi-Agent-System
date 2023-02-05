package lab.lab3.container;

import lab.lab3.behaviour.MasterAgentBehavior;
import lab.lab3.behaviour.ReceivingAndProcessingMSG;
import lab.lab3.behaviour.EndNode;
import jade.core.Agent;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

public class Main extends Agent {

    @SneakyThrows
    @Override
    protected void setup() {
        Config config = new Config();
        FileInputStream inputStream = new FileInputStream("C:\\Users\\gamza\\IdeaProjects\\MAC\\src\\main\\resources\\mtrx.xlsx");
        Workbook workbook = new XSSFWorkbook(inputStream);
        for (Sheet sheet : workbook) {
            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();
            for (int index = firstRow; index <= lastRow; index++) {
                Map<Integer, Double> intermediate = new HashMap<>();
                List<NodeAgent> nodes = new ArrayList<>();
                Row row = sheet.getRow(index);
                for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    intermediate.put(cellIndex, cell.getNumericCellValue());
                    if (intermediate.get(cellIndex) != 0) nodes.add(new NodeAgent("Smith" + cellIndex, intermediate.get(cellIndex)));
                }
                config.setName("Smith" + index);
                config.setNodes(nodes);
                xmlHandler.marshalAny(Config.class, config, "Smith" + index + ".xml");
            }
        }
        final int[] nodes = { 3, 10 };
        if (getLocalName().equals("Smith" + nodes[0]))
            addBehaviour(new MasterAgentBehavior(new ArrayList<>(), "Smith" + nodes[1], 0));
        addBehaviour(new ReceivingAndProcessingMSG());
        addBehaviour(new EndNode());
    }
}

