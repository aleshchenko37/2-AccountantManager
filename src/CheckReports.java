public class CheckReports {
    public MonthlyReport monthlyReport;
    public YearlyReport yearlyReport;


    public CheckReports(MonthlyReport monthlyReport, YearlyReport yearlyReport) {
        this.monthlyReport = monthlyReport;
        this.yearlyReport = yearlyReport;
    }


    public boolean isSumsCorrect() {
        boolean check = true;
        monthlyReport.printMonthStat();
        System.out.println("Идет сверка отчетов...");
        for (String month : monthlyReport.createListOfMonths()) {
            if (monthlyReport.countMonthExpenses(month) == yearlyReport.getMonthExpense(month)) {
                continue;
            } else {
                System.out.println("Данные за " + month + " не совпадают. Проверьте корректность исходных данных.");
                check = false;
            }
            if (monthlyReport.countMonthEarnings(month) == yearlyReport.getMonthEarnings(month)) {
                continue;
            } else {
                System.out.println("Данные за " + month + " не совпадают. Проверьте корректность исходных данных.");
                check = false;
            }
        }
        return check;
    }

    public boolean isReportsLoaded() {
        boolean checker = true;
        if (monthlyReport == null) {
            System.out.println("Данные по отчётам за месяц не считаны.");
            checker = false;
        }
        if (yearlyReport == null) {
            System.out.println("Данные по годовому отчёту не считаны.");
            checker = false;
        }
        return checker;
    }
}
