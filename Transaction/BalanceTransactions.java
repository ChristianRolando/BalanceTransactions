import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *  Title:           BalanceTransactions
 *  Semester:        COP3337  Summer 2023
 *  @author          Christian Rolando
 *
 *  I affirm that this program is entirely my own work
 *  and none of it is the work of any other person.
 *
 *  Description of program, and explanation of programming
 *  concept(s) being applied in the program: Reads a ledger file and compares cash in/cash out. Concepts: exceptions, file I/O.
 */


public class BalanceTransactions {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Ask user for input of startingCash and endingCash
        System.out.print("Please enter your starting cash: ");
        double startingCash = scanner.nextDouble();
        System.out.print("Please enter your ending cash: ");
        double endingCash = scanner.nextDouble();
        //Ask user to specify file name
        System.out.print("Enter the name of the text file, please: ");
        String fileName = scanner.next();
        //Create new file Object for reading. Check if file exists or not...
        File file = new File(fileName);
        while (!file.exists()) {
            System.out.println("That file does not exist! Please try again or type 'exit' to end the program: ");
            System.out.print("Enter the name of the text file again: ");
            fileName = scanner.next();
            if (fileName.equalsIgnoreCase("exit")) {
                return;
            }
            file = new File(fileName);
        }
        //Read our file, differentiate text from double types..save the info we need.
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            double totalCash = 0.0;
            String line;
            while ((line = br.readLine()) != null) {
                String[] items = line.split(" ");
                if (items.length != 3) {
                    System.out.println("Invalid file. Ending program. Goodbye.");
                    return;
                }
                double amount = Double.parseDouble(items[1]);
                String type = items[2];
                //Making sure that case sensitivity does NOT matter.
                if (type.equalsIgnoreCase("P")) {
                    totalCash += amount;
                } else if (type.equalsIgnoreCase("R")) {
                    totalCash -= amount;
                } else {
                    System.out.println("Invalid transaction. Ending program.");
                    return;
                }
            }
            //Checks to know whether our cash corresponds to amount or not.
            if (totalCash == endingCash - startingCash) {
                System.out.println("The actual amount of cash at the end of the day matches your expected value.");
            } else {
                System.out.println("The actual amount of cash at the end of the day does not match your expected value.");
            }
            //Our exceptions if File is not found or file can't be read (not txt file).
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Ending program.");
        } catch (IOException e) {
            System.out.println("Error reading the file. Ending program.");
        }
    }
}
