public class MonthlyReportSaved { // в объекте только одна строка отчета за месяц
    public String itemName;
    public boolean isExpense;
    public int quantity;
    public int sumOfOne;
    public String month;

    public MonthlyReportSaved(String itemName, boolean isExpense, int quantity, int sumOfOne, String month) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
        this.month = month;
    }
}
