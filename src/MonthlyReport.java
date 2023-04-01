import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {

    public ArrayList<MonthlyReportSaved> monthDataInClasses = new ArrayList<>();


    public void loadFile(String month, String path) {
        String content = readFileContentsOrNull(path);
        String[] lines = content.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(",");

            String itemName = parts[0];
            boolean isExpense = Boolean.parseBoolean(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            int sumOfOne = Integer.parseInt(parts[3]);

            monthDataInClasses.add(new MonthlyReportSaved(itemName, isExpense, quantity, sumOfOne, month));
        }
    }

    public String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return null;
        }
    }

    public ArrayList<String> createListOfMonths() { // создает список месяцев
        ArrayList<String> months = new ArrayList<>();
        for (MonthlyReportSaved monthlyReportSaved : monthDataInClasses) {
            String month = monthlyReportSaved.month;
            if (months.contains(month)) {
                continue;
            } else {
                months.add(month);
            }
        }
        return months;
    }

    public int countMonthExpenses(String month) {
        int monthExpenses = 0;
        for (MonthlyReportSaved monthlyReportSaved : monthDataInClasses) {
            if (monthlyReportSaved.month == month && monthlyReportSaved.isExpense) {
                monthExpenses += monthlyReportSaved.quantity * monthlyReportSaved.sumOfOne;
            }
        }
        return monthExpenses;
    }

    public int countMonthEarnings(String month) {
        int monthEarnings = 0;
        for (MonthlyReportSaved monthlyReportSaved : monthDataInClasses) {
            if (monthlyReportSaved.month == month && !(monthlyReportSaved.isExpense)) {
                monthEarnings += monthlyReportSaved.quantity * monthlyReportSaved.sumOfOne;
            }
        }
        return monthEarnings;
    }

    public void printMonthStat () {
        for(String month : createListOfMonths()) {
            System.out.println("Доходы за " + month + " составили: " + countMonthEarnings(month));
            System.out.println("Расходы за " + month + " составили: " + countMonthExpenses(month));
        }
    }

    public void printFullMonthStat() {
        for(String month : createListOfMonths()) {
            System.out.println("Статистика за " + month);
            getTopEarning(month);
            getTopExpense(month);
        }
    }

    public void getTopEarning(String month) {

        HashMap<String, HashMap<String, Integer>> earningToItemInMonth = new HashMap<>();
        for (MonthlyReportSaved monthlyReportSaved : monthDataInClasses) {
            if (monthlyReportSaved.isExpense) {
                continue;
            }
            if (monthlyReportSaved.month.equals(month)) {
                if (!earningToItemInMonth.containsKey(month)) {
                    earningToItemInMonth.put(month, new HashMap<>());
                } else {
                    HashMap<String, Integer> earningToItem = earningToItemInMonth.get(month);
                    earningToItem.put(
                            monthlyReportSaved.itemName,
                            earningToItem.getOrDefault(monthlyReportSaved.itemName, 0) +
                                    monthlyReportSaved.quantity * monthlyReportSaved.sumOfOne
                    );
                }
            }
        }
        String topItemName = "";
        int topItemValue = 0;
        for (String curMonth : earningToItemInMonth.keySet()) {
            HashMap<String, Integer> curMonthHashMap = earningToItemInMonth.get(curMonth);
            for (String item : curMonthHashMap.keySet()) {
                if (curMonthHashMap.get(item) > topItemValue) {
                    topItemValue = curMonthHashMap.get(item);
                    topItemName = item;
                }
            }
        }
        System.out.println(
                "Самый прибыльный товар в этом месяце: " + topItemName +
                ". Прибыль по нему составила: " + topItemValue
        );
    }

    public void getTopExpense(String month) {
        HashMap<String, HashMap<String, Integer>> expenseToItemInMonth = new HashMap<>();
        for (MonthlyReportSaved monthlyReportSaved : monthDataInClasses) {
            if (!monthlyReportSaved.isExpense) {
                continue;
            }
            if (monthlyReportSaved.month.equals(month)) {
                if (!expenseToItemInMonth.containsKey(month)) {
                    expenseToItemInMonth.put(month, new HashMap<>());
                } else {
                    HashMap<String, Integer> expenseToItem = expenseToItemInMonth.get(month);
                    expenseToItem.put(
                            monthlyReportSaved.itemName,
                            expenseToItem.getOrDefault(monthlyReportSaved.itemName, 0) +
                                    monthlyReportSaved.quantity * monthlyReportSaved.sumOfOne
                    );
                }
            }
        }
        String topExpenseName = "";
        int topExpenseValue = 0;
        for (String curMonth : expenseToItemInMonth.keySet()) {
            HashMap<String, Integer> curMonthHashMap = expenseToItemInMonth.get(curMonth);
            for (String item : curMonthHashMap.keySet()) {
                if (curMonthHashMap.get(item) > topExpenseValue) {
                    topExpenseValue = curMonthHashMap.get(item);
                    topExpenseName = item;
                }
            }
        }
        System.out.println(
            "Самая большая трата в этом месяце: " + topExpenseName +
            ". На неё потрачено: " + topExpenseValue + " рублей."
        );
    }
}