
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ReportManager reportManager = new ReportManager(2021);

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            switch (command){
                case 1: reportManager.readFileMonth(); break;
                case 2: reportManager.readFileYearly(); break;
                case 3: reportManager.compareReports(); break;
                case 4: reportManager.printStaticMonthReport(); break;
                case 5: reportManager.printStatisticOfYear(); break;
                case 0: System.out.println("Выход"); return;
                default: System.out.println("Извините, такой команды пока нет."); break;
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

