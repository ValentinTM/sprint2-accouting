
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {
    HashMap<Integer, ArrayList<MonthlyReportRecord>> reports = new HashMap<>();;
    ArrayList<MonthlyReportRecord> monthlyRecords = new ArrayList<>();
    MonthlyReportRecord monthlyReportRecord;
    HashMap<Integer,Integer> recordExpense = new HashMap<>();
    HashMap<Integer,Integer> recordIncome = new HashMap<>();

    public MonthlyReport() {}

}
