import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {

    HashMap<Integer, ArrayList<MonthlyReportRecord>> reports = new HashMap<>();;
    ArrayList<MonthlyReportRecord> monthlyRecords = new ArrayList<>();
    MonthlyReportRecord monthlyReportRecord;
    HashMap<Integer,Integer> recordExpense = new HashMap<>();
    HashMap<Integer,Integer> recordIncome = new HashMap<>();

    private static final String DIRECTORY = "resources";

    public MonthlyReport() {


    }

    public void readFileMonth(){
        int numberMonth = 0;
        for (int i = 1; i < 4; i++) {
            monthlyRecords = new ArrayList<>();
            String path = DIRECTORY  + "//m.20210" + i + ".csv";
            String data = readFileContentsOrNull(path);
            String[] lines = data.split(System.lineSeparator());

            for (int j = 1; j < lines.length; j++){
                String line = lines[j];
                String[] parts = line.split(",");
                String item_name = parts[0];
                boolean is_expense = Boolean.parseBoolean(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                int sum_of_one = Integer.parseInt(parts[3]);
                monthlyReportRecord = new MonthlyReportRecord(item_name, is_expense, quantity, sum_of_one);
                monthlyRecords.add(monthlyReportRecord);

            }

            numberMonth = i;
            System.out.println("Отсчет за " + numberMonth + " месяц прочитан и записан!");
            reports.put(numberMonth, monthlyRecords);


        }
    }

    protected String readFileContentsOrNull(String path){
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public void allExpenses(){
        int maxExpense;
        for (Integer key :reports.keySet()) {
            maxExpense = 0;
            for (MonthlyReportRecord values: reports.get(key)) {
                if (values.is_expense) {
                    maxExpense += values.quantity*values.sum_of_one;
                }
            }
            recordExpense.put(key, maxExpense);
        }
    }

    public void allIncomes(){

        int maxIncomes;
        for (Integer key :reports.keySet()) {
            maxIncomes = 0;
            for (MonthlyReportRecord values: reports.get(key)) {
                if (!values.is_expense) {
                    maxIncomes += values.quantity*values.sum_of_one;
                }
            }
            recordIncome.put(key, maxIncomes);
        }
    }

    public void printStaticMonthReport(){
        double maxIncome = 0;
        double maxExpense = 0;
        double maxExProduct;
        String nameProductEx = null;
        String nameProductIn = null;

        for (Integer key :reports.keySet()) {
            System.out.println("Месяц "+key);
            for (MonthlyReportRecord values: reports.get(key)) {
                    maxExProduct = values.sum_of_one * values.quantity;
                    if (values.is_expense && maxExProduct > maxExpense) {
                        maxExpense = maxExProduct;
                        nameProductEx = values.item_name;
                    } else if(!values.is_expense && maxExProduct > maxIncome){
                        maxIncome = maxExProduct;
                        nameProductIn = values.item_name;
                    }
            }
            System.out.println("Самый прибыльный товар: " + nameProductIn + ". Сумма: " + maxIncome+" руб!");
            System.out.println("Самая большая трата: " + nameProductEx + ". Сумма: " + maxExpense+ " руб!");
            maxIncome = 0;
            maxExpense = 0;
            nameProductEx = null;
            nameProductIn = null;

        }

    }

}
