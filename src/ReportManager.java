import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ReportManager {

    MonthlyReport monthlyReport;
    YearlyReport yearlyReport;
    int numberYearly;

    private static final String DIRECTORY = "resources";
    public ReportManager(int numberYear) {
        this.numberYearly = numberYear;
        monthlyReport = new MonthlyReport();
        yearlyReport = new YearlyReport();

    }

    protected String readFileContentsOrNull(String path){
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public void readFileMonth(){
        int numberMonth;
        for (int i = 1; i < 4; i++) {
            monthlyReport.monthlyRecords = new ArrayList<>();
            String path = DIRECTORY  + "//m.20210" + i + ".csv";
            String data = readFileContentsOrNull(path);
            if (data!=null){
                String[] lines = data.split(System.lineSeparator());
                for (int j = 1; j < lines.length; j++){
                    String line = lines[j];
                    String[] parts = line.split(",");
                    String item_name = parts[0];
                    boolean is_expense = Boolean.parseBoolean(parts[1]);
                    int quantity = Integer.parseInt(parts[2]);
                    int sum_of_one = Integer.parseInt(parts[3]);
                    monthlyReport.monthlyReportRecord = new MonthlyReportRecord(item_name, is_expense, quantity, sum_of_one);
                    monthlyReport.monthlyRecords.add(monthlyReport.monthlyReportRecord);
                }
            }
            numberMonth = i;
            System.out.println("Отсчет за " + numberMonth + " месяц прочитан и записан!");
            monthlyReport.reports.put(numberMonth, monthlyReport.monthlyRecords);
        }
        allExpenses();
        allIncomes();
    }
    public void allExpenses(){
        int maxExpense;
        for (Integer key : monthlyReport.reports.keySet()) {
            maxExpense = 0;
            for (MonthlyReportRecord values: monthlyReport.reports.get(key)) {
                if (values.is_expense) {
                    maxExpense += values.quantity*values.sum_of_one;
                }
            }
            monthlyReport.recordExpense.put(key, maxExpense);
        }
    }
    public void allIncomes(){
        int maxIncomes;
        for (Integer key : monthlyReport.reports.keySet()) {
            maxIncomes = 0;
            for (MonthlyReportRecord values: monthlyReport.reports.get(key)) {
                if (!values.is_expense) {
                    maxIncomes += values.quantity*values.sum_of_one;
                }
            }
            monthlyReport.recordIncome.put(key, maxIncomes);
        }
    }
    public void printStaticMonthReport(){
        double maxIncome = 0;
        double maxExpense = 0;
        double maxExProduct;
        String nameProductEx = null;
        String nameProductIn = null;
        try {
            for (Integer key :monthlyReport.reports.keySet()) {
                System.out.println("Месяц "+key);
                for (MonthlyReportRecord values: monthlyReport.reports.get(key)) {
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
        } catch (NullPointerException exception) {
            System.out.println("Невозможно вывести статистику по месячному отчету! " +
                    "Возможно, файл не еще не прочитан и не сохранен! Попробуйте считайте файл еще раз!.");
        }

    }

    public void readFileYearly(){

        String data = readFileContentsOrNull(DIRECTORY+ "/y." + numberYearly + ".csv"); //"resources/y.2021.csv"
        if(data!=null){
            String[] lines = data.split(System.lineSeparator());
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                String[] parts = line.split(",");
                int month = Integer.parseInt(parts[0]);
                int amount = Integer.parseInt(parts[1]);
                boolean isExpense = Boolean.parseBoolean(parts[2]);
                yearlyReport.yearlyReportRecord = new YearlyReportRecord(month, amount, isExpense);
                yearlyReport.yearlyRecords.add(yearlyReport.yearlyReportRecord);
            }
        }
        System.out.println("Отсчет за "+ numberYearly +" прочитан и записан!");
        addInHashMapExIn();
    }
    public void addInHashMapExIn(){
        for (YearlyReportRecord yearlyRecord : yearlyReport.yearlyRecords) {
            if(yearlyRecord.isExpense){
                int index;
                index= yearlyRecord.month;
                yearlyReport.recordExpense.put(index, yearlyRecord);
            }else{
                int index;
                index= yearlyRecord.month;
                yearlyReport.recordIncome.put(index, yearlyRecord);
            }
        }
    }
    public void printStatisticOfYear(){
        try {
            profitOfYear();
            averageExpensesYear();
            averageIncomeYear();
        } catch (NullPointerException exception) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. " +
                    "Возможно, файл не еще не прочитан и не сохранен! Попробуйте считайте файл еще раз!.");
        }
    }
    public void profitOfYear(){
        System.out.println("Рассматриваемый год "+ numberYearly);
        for (int i = 1; i < 4; i++) {
            double proFit = yearlyReport.recordIncome.get(i).amount-yearlyReport.recordExpense.get(i).amount;
            yearlyReport.profit.put(i, proFit);
        }
        for (int i = 1; i < yearlyReport.profit.size()+1; ++i) {
            System.out.println("Прибыль " + yearlyReport.profit.get(i) + " руб," + " за "+ i + " месяц!");
        }
    }
    public void averageExpensesYear(){
        double sumExpense = 0;
        int monthCount=0;
        double averageValuesExpense;
        for (int i = 1; i < yearlyReport.recordExpense.size()+1; i++) {
            monthCount++;
            sumExpense += yearlyReport.recordExpense.get(i).amount;
        }
        averageValuesExpense = sumExpense/monthCount;
        System.out.println("Средний расход за все месяцы в году: " + averageValuesExpense + " руб!");
    }
    public void averageIncomeYear(){
        double sumIncome = 0;
        int monthCount=0;
        double averageValuesIncome;
        for (int i = 1; i < yearlyReport.recordIncome.size()+1; i++) {
            monthCount++;
            sumIncome += yearlyReport.recordIncome.get(i).amount;
        }
        averageValuesIncome = sumIncome/monthCount;
        System.out.println("Средний доход за все месяцы в году: " + averageValuesIncome + " руб!");
    }
    
    public void compareReports(){
        try {
            for (int i = 1; i < 4; i++) {
                if (monthlyReport.recordExpense.get(i) == yearlyReport.recordExpense.get(i).amount) {
                    System.out.println("Успешное завершение операции!");
                } else {
                    System.out.println("В месяце " + i +" обнаружено несоответствие в расходах!");
                }
                if(monthlyReport.recordIncome.get(i) == yearlyReport.recordIncome.get(i).amount){
                    System.out.println("Успешное завершение операции!");
                } else {
                    System.out.println("В месяце " + i +" обнаружено несоответствие в доходах!");
                }
            }
        } catch (NullPointerException exception) {
            System.out.println("Невозможно сравнить файлы с отчётами. Возможно, файлы еще не считаны! " +
                    "Используя меню считайте файлы еще раз!");
        }
    }
}
