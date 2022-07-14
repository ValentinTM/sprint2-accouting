import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    int numberYearly;

    ArrayList<YearlyReportRecord> yearlyRecords = new ArrayList<>();
    YearlyReportRecord yearlyReportRecord;
    HashMap<Integer,YearlyReportRecord> recordExpense = new HashMap<>();
    HashMap<Integer, YearlyReportRecord> recordIncome = new HashMap<>();
    HashMap<Integer, Double> profit = new HashMap<>();

    public YearlyReport(int year) {
        this.numberYearly = year;

    }

    public void readFileYearly(){
        String data = readFileContentsOrNull("resources/y." + numberYearly + ".csv"); //"resources/y.2021.csv"
        String[] lines = data.split(System.lineSeparator());
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(",");
            int month = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            boolean isExpense = Boolean.parseBoolean(parts[2]);
            yearlyReportRecord = new YearlyReportRecord(month, amount, isExpense);
            yearlyRecords.add(yearlyReportRecord);

        }
        System.out.println("Отсчет за "+ numberYearly +" прочитан и записан!");
        addInHashMapExIn();
    }


    public void addInHashMapExIn(){

        for (YearlyReportRecord yearlyRecord : yearlyRecords) {
            if(yearlyRecord.isExpense){
                int index;
                index= yearlyRecord.month;
                recordExpense.put(index, yearlyRecord);
            }else{
                int index;
                index= yearlyRecord.month;
                recordIncome.put(index, yearlyRecord);
            }
        }


    }

    public  void  averageExpensesYear(){
        double sumExpense = 0;
        int monthCount=0;
        double averageValuesExpense =0;
        for (int i = 1; i < recordExpense.size()+1; i++) {
            monthCount++;
            sumExpense += recordExpense.get(i).amount;

        }
        averageValuesExpense = sumExpense/monthCount;
        System.out.println("Средний расход за все месяцы в году: " + averageValuesExpense + " руб!");

    }

    public  void  averageIncomeYear(){
        double sumIncome = 0;
        int monthCount=0;
        double averageValuesIncome =0;
        for (int i = 1; i < recordIncome.size()+1; i++) {
            monthCount++;
            sumIncome += recordIncome.get(i).amount;

        }
        averageValuesIncome = sumIncome/monthCount;
        System.out.println("Средний доход за все месяцы в году: " + averageValuesIncome + " руб!");

    }



    public void profit(){

        System.out.println("Рассматриваемый год "+numberYearly);

        for (int i = 1; i < 4; i++) {
            double proFit = recordIncome.get(i).amount-recordExpense.get(i).amount;
            profit.put(i, proFit);
        }

        for (int i = 1; i < profit.size()+1; ++i) {
            System.out.println("Прибыль " + profit.get(i) + " руб," + " за "+ i + " месяц!");
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

}
