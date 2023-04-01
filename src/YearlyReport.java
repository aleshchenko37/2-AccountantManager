import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    public ArrayList<YearReportSaved> yearReportFinal = new ArrayList<>(); // хранит month, amount, isExpense

    public YearlyReport(String year, String path) {
        String content = readFileContents(path);
        String[] lineContents = content.split("\r?\n"); // для Windows
        for (int i = 1; i < lineContents.length; i++) {
            String line = lineContents[i];
            String[] parts = line.split(",");
            String month = parts[0];
            month = monthConverter(month); // 01 --> Январь
            int amount = Integer.parseInt(parts[1]); // т.к. в массиве parts содержатся значения только формата String
            boolean isExpense = Boolean.parseBoolean(parts[2]); // т.к. в массиве parts содержатся значения только формата String

            YearReportSaved yearReportSaved = new YearReportSaved(month, amount, isExpense, year);
            yearReportFinal.add(yearReportSaved);
        }
    }
    public String readFileContents(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return null;
        }
    }

    public int getMonthExpense(String month) {
        int monthExpense = 0;
        for (YearReportSaved yearReportSaved : yearReportFinal) {
            if (yearReportSaved.isExpense && yearReportSaved.month.equals(month)) {
                monthExpense = yearReportSaved.amount;
            }
        }
        return monthExpense;
    }

    public int getMonthEarnings(String month) {
        int monthExpense = 0;
        for (YearReportSaved yearReportSaved : yearReportFinal) {
            if (!yearReportSaved.isExpense && yearReportSaved.month.equals(month)) {
                monthExpense = yearReportSaved.amount;
            }
        }
        return monthExpense;
    }

    public String monthConverter(String month) {
        if (month.equals("01")) {
            month = "Январь";
        } else if (month.equals("02")) {
            month = "Февраль";
        } else if (month.equals("03")) {
            month = "Март";
        } else {
            return null;
        }
        return month;
    }

    public void printYearStat(String year) {
        int profit;
        HashMap<String, Integer> expenses = new HashMap<>();
        HashMap<String, Integer> benefits = new HashMap<>();
        for (YearReportSaved yearReportSaved : yearReportFinal) {
            if (yearReportSaved.isExpense) {
                expenses.put(
                        yearReportSaved.month,
                        expenses.getOrDefault(yearReportSaved.month, 0) +
                        yearReportSaved.amount
                );
                // на случай, если несколько значений по одному месяцу
            } else {
                benefits.put(
                        yearReportSaved.month,
                        benefits.getOrDefault(yearReportSaved.month, 0) +
                        yearReportSaved.amount
                );
            }
        }
        System.out.println("Вы просматриваете отчет за: " + year + " год.");
        for (String month : benefits.keySet()) {
            profit = benefits.get(month) - expenses.get(month);
            System.out.println("Прибыль за " + month + " составила " + profit + " рублей.");
        }

        int sumExpenses = 0;
        int averageExpense = 0;
        for (Integer exp : expenses.values()) {
            sumExpenses += exp;
        }
        averageExpense = sumExpenses / expenses.values().size();
        System.out.println("Среднемесячная сумма расходов составила " + averageExpense + " рублей.");

        int sumBenefits = 0;
        int averageBenefit = 0;
        for (Integer exp : benefits.values()) {
            sumBenefits += exp;
        }
        averageBenefit = sumBenefits / benefits.values().size();
        System.out.println("Среднемесячная сумма доходов составила " + averageBenefit + " рублей.");
    }

}