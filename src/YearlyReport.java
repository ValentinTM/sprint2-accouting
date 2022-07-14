
import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    ArrayList<YearlyReportRecord> yearlyRecords = new ArrayList<>();
    YearlyReportRecord yearlyReportRecord;
    HashMap<Integer,YearlyReportRecord> recordExpense = new HashMap<>();
    HashMap<Integer, YearlyReportRecord> recordIncome = new HashMap<>();
    HashMap<Integer, Double> profit = new HashMap<>();

    public YearlyReport() {}

}
