
public class ReportManager {

    MonthlyReport monthlyReport;
    YearlyReport yearlyReport;


    public ReportManager(int report) {
        monthlyReport = new MonthlyReport();
        yearlyReport = new YearlyReport(report);

    }

    
    public void compareReports(){

        monthlyReport.allExpenses();
        monthlyReport.allIncomes();

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
    }
}
