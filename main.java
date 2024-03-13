import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    static String user = "Shivam@123";
    static String name = "Shivam";
    static int pass = 1234;
    static File file;
    static final int MAX_DESCRIPTION_LENGTH = 50;
    static final String FILENAME = "expenses.txt";
    static final int MAX_EXPENSES = 100;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        System.out.println("Hi " + name);
        System.out.println("Welcome back");
        System.out.println("Enter your password to login");
        System.out.print("PIN: ");
        int pin = scanner.nextInt();
        if (pass == pin) {
            while (true) {
                System.out.println("+-------------------------------------------------------------------------------------------+");
                System.out.println("|                                       EXPENSES TRACKER                                    |");
                System.out.println("+-------------------------------------------------------------------------------------------+");
                System.out.println("|   What do you want to do?                                                                 |");
                System.out.println("|   1. Add Expenses                                                                         |");
                System.out.println("|   2. View Expenses                                                                        |");
                System.out.println("|   3. Delete Expenses                                                                      |");
                System.out.println("|   4. Calculate Total Expenses                                                             |");
                System.out.println("|   5. Exit                                                                                 |");
                System.out.println("+-------------------------------------------------------------------------------------------+");
                System.out.print("Choose from 1-5: ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addExpenses();
                        break;
                    case 2:
                        viewExpenses();
                        break;
                    case 3:
                        deleteExpenses();
                        break;
                    case 4:
                        calculateTotal();
                        break;
                    case 5:
                        System.out.println("OK Bye!!");
                        return;
                    default:
                        System.out.println("Not Valid Option Selected. Try Again!");
                }
            }
        } else {
            System.out.println("Wrong username or password");
        }
    }

    static void addExpenses() {
        int count;
        int lineno = countLines();
        System.out.print("Enter total count of expenses to be added: ");
        count = scanner.nextInt();
        try {
            FileWriter writer = new FileWriter(FILENAME, true);
            for (int i = 0; i < count; i++) {
                System.out.print("Enter the amount(in Rs.): ");
                float amount = scanner.nextFloat();
                System.out.print("Enter Date in DD/MM/YYYY form: ");
                String date = scanner.next();
                System.out.print("Enter the category: ");
                String category = scanner.next();
                System.out.print("Enter Description: ");
                String description = scanner.next();
                writer.write(amount + " " + date + " " + category + " " + description + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void viewExpenses() {
        try {
            Scanner fileScanner = new Scanner(new File(FILENAME));
            int count = countLines();
            System.out.println("+--------------------------------------------------------------------------------------+");
            for (int i = 0; i < count; i++) {
                float amount = fileScanner.nextFloat();
                String date = fileScanner.next();
                String category = fileScanner.next();
                String description = fileScanner.next();
                System.out.println("| " + (i + 1) + ". | Description: " + description + " |");
                System.out.println("|    | Amount:      Rs." + amount + " |");
                System.out.println("|    | Category:    " + category + " |");
                System.out.println("|    | Date:        " + date + " |");
                System.out.println("+--------------------------------------------------------------------------------------+");
            }
            fileScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void deleteExpenses() {
        viewExpenses();
        System.out.print("Enter the expense number to delete: ");
        int expenseNumber = scanner.nextInt();

        try {
            Scanner fileScanner = new Scanner(new File(FILENAME));
            FileWriter tempWriter = new FileWriter("temp.txt");
            int currentExpenseNumber = 1;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (currentExpenseNumber != expenseNumber) {
                    tempWriter.write(line + "\n");
                }
                currentExpenseNumber++;
            }
            fileScanner.close();
            tempWriter.close();

            file = new File(FILENAME);
            if (!file.delete()) {
                System.out.println("Error deleting file");
                return;
            }

            File tempFile = new File("temp.txt");
            if (!tempFile.renameTo(file)) {
                System.out.println("Error renaming file");
            }

            System.out.println("Expense deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void calculateTotal() {
        try {
            Scanner fileScanner = new Scanner(new File(FILENAME));
            float totalExpense = 0;
            while (fileScanner.hasNext()) {
                float amount = fileScanner.nextFloat();
                fileScanner.next(); // Skip date
                fileScanner.next(); // Skip category
                fileScanner.next(); // Skip description
                totalExpense += amount;
            }
            fileScanner.close();
            System.out.printf("Total Expenses: Rs.%.2f\n", totalExpense);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int countLines() {
        int lineCount = 0;
        try {
            Scanner fileScanner = new Scanner(new File(FILENAME));
            while (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
                lineCount++;
            }
            fileScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }
}
