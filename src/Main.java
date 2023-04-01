import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = null;
        YearlyReport yearlyReport = null;

        while (true) {
            printMenu();

            int userInput = scanner.nextInt();
            if (userInput == 1) {
                monthlyReport = new MonthlyReport();
                for (int i = 1; i <= 3; i++) {
                    String path = "resources/m.20210" + i + ".csv";
                    String month = monthConverter(i);
                    monthlyReport.loadFile(month, path);
                }
            } else if (userInput == 2) {
                yearlyReport = new YearlyReport("year", "resources/y.2021.csv");
            } else if (userInput == 3) { // работает, даже если отчеты не загружены!!!
                CheckReports checkReports = new CheckReports(monthlyReport, yearlyReport);
                if (checkReports.isReportsLoaded()) {
                    if (checkReports.isSumsCorrect()) {
                        System.out.println("Сверка завершена.");
                    } else {
                        System.out.println("Сумма доходов или расходов в годовом отчёте и в отчётах по месяцам не совпадают. Проверьте исходные данные.");
                    }
                }
            } else if (userInput == 4) {
                monthlyReport.printFullMonthStat();
            } else if (userInput == 5) {
                yearlyReport.printYearStat("2021");
            } else if (userInput == 0) {
                System.out.println("Пока!");
                scanner.close();
                return;
            } else {
                System.out.println("Такой команды нет");
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
        // Вывод доступных команд
    }

    public static String monthConverter(int monthNumber) {
        String month = "";
        if (monthNumber == 1) {
            month = "Январь";
        } else if (monthNumber == 2) {
            month = "Февраль";
        } else if (monthNumber == 3) {
            month = "Март";
        } else {
            month = null;
        }
        return month;
    }
}

