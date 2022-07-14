
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ReportManager reportManager = new ReportManager(2021);


        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                reportManager.monthlyReport.readFileMonth();
            } else if (command == 2) {
                reportManager.yearlyReport.readFileYearly();
            } else if (command == 3) {
                try {
                    reportManager.compareReports();
                } catch (NullPointerException exception) {
                    System.out.println("Невозможно сравнить файлы с отчётами. Возможно, файлы еще не считаны! Используя меню считайте файлы еще раз!");
                }
            } else if (command == 4) {
                reportManager.monthlyReport.printStaticMonthReport();
            } else if (command == 5) {
                try {
                    reportManager.yearlyReport.profit();
                    reportManager.yearlyReport.averageExpensesYear();
                    reportManager.yearlyReport.averageIncomeYear();
                } catch (NullPointerException exception) {
                    System.out.println("Невозможно прочитать файл с годовым отчётом. " +
                            "Возможно, файл не еще не прочитан и не сохранен! Попробуйте считайте файл еще раз!.");
                }
            } else if (command == 0) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выход");
    }
}

