package banking;
//4000000706334416  5564
//4000002581051891  5047

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static String currentCardNum;
    private static String currentCardPin;
    private static int userInput = 100;
    private static MyDB myDB;
    public static void main(String[] args) {

        myDB = new MyDB(args[1]);

        while(userInput != 0) {
            mainMenu();
        }
        System.out.println("\nBye!");
    }

    private static void mainMenu() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
        userInput = Integer.parseInt(scanner.nextLine());
        mainSelect(userInput);
    }

    private static void mainSelect(int nextInt) {
        switch (nextInt){
            case 1: createAccount();
            break;
            case 2: login();
            break;
        }
    }


    private static void createAccount() {
        String cardNumber =  generateCardNumber();
        String fullPin = generatePin();

        myDB.save(cardNumber, fullPin);
        System.out.println("\nYour card has been created\n" +
                "Your card number:\n" + cardNumber + "\n" +
                "Your card PIN:\n" + fullPin + "\n");

        mainMenu();
    }

    private static String generateCardNumber() {
        Random random = new Random();
        int firstHalf = random.nextInt(100000);
        String  firstHalfStr = fillByZeros(firstHalf, 5);
        int secondHalf = random.nextInt(10000);
        String secondHalfStr =  fillByZeros(secondHalf, 4);
        int checkSum = calculateCheckSum("400000" + firstHalfStr + secondHalfStr);
        return "400000" + firstHalfStr + secondHalfStr + checkSum;
    }

    public static int calculateCheckSum(String str) {
        AtomicInteger count = new AtomicInteger(1);
        int checkSum = (10 - (Arrays.stream(str.split(""))
                .mapToInt(Integer::parseInt)
                .map(i -> {
                    if(count.get() % 2 != 0) {
                        i = i * 2;
                        if (i > 9) {
                            i -= 9;
                        }
                    }
                    count.incrementAndGet();
                    // System.out.print("+++" + i + "+++");
                    return i;
                })
        .sum())%10) % 10;
        return checkSum;
    }

    private static String generatePin() {
        Random random = new Random();
        int pin = random.nextInt(10000);
        return fillByZeros(pin, 4);
    }

    public static String fillByZeros(int number, int length) {
        String filledStr = number + "";
        while ( filledStr.length() < length){
            filledStr = "0" + filledStr;
        }
        return filledStr;
    }

    private static void login() {
        System.out.println("\nEnter your card number:");//4000008287136639
        String inputCardNum = scanner.nextLine();
        System.out.println("Enter your PIN:");//0906
        String inputPin = scanner.nextLine();
        if (myDB.get(inputCardNum, inputPin)) {
            currentCardNum = inputCardNum;
            System.out.println("\nYou have successfully logged in!\n");
            loggedMenu();
        } else {
            System.out.println("Wrong card number or PIN!\n");
            mainMenu();
        }
    }

    private static void loggedMenu() {
        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");
        userInput = Integer.parseInt(scanner.nextLine());
        loggedSelect(userInput);
    }

    private static void loggedSelect(int nextInt) {
        switch (nextInt){
            case 1: showBalance();
                break;
            case 2: addIncome();
                break;
            case 3: doTransfer();
                break;
            case 4: closeAccount();
                break;
            case 5: logout();
                break;
        }
    }

    //reads the balance of the account from
    // the database and outputs it into the console.
    private static void showBalance() {
        int balance = myDB.getBalance(currentCardNum);
        System.out.println("\nBalance: " + balance + "\n");
        loggedMenu();
    }

    private static void addIncome() {
        System.out.println("\nEnter income:");
        int income = scanner.nextInt();
        scanner.nextLine();
        myDB.addIncome(income, currentCardNum);
        System.out.println("Income was added!\n");
        loggedMenu();
    }

    private static void doTransfer() {
        System.out.println("\nTransfer\n" +
                "Enter card number:");
        String transferCard = scanner.nextLine();
        if ((!transferCard.equals(currentCardNum) && check(transferCard) && transfer(transferCard))) {
            System.out.println("Success!\n");
        } else if (transferCard.equals(currentCardNum)) {
            System.out.println("You can't transfer money to the same account!");
        }
        loggedMenu();
    }

    private static boolean transfer(String transferCard) {
        System.out.println("Enter how much money you want to transfer:");
        int transferSum = scanner.nextInt();
        scanner.nextLine();
        if (myDB.getBalance(currentCardNum) < transferSum) {
            System.out.println("Not enough money!\n");
            return false;
        } else {
            myDB.transfer(currentCardNum, transferCard, transferSum);
            return true;
        }
    }

    private static boolean check(String transferCard) {
        boolean cardExists = false;
        if (calculateCheckSum(transferCard) == 0) {
            if (myDB.cardExists(transferCard)) {
                cardExists = true;

            } else {
                System.out.println("Such a card does not exist.\n");
            }
        } else {
            System.out.println("Probably you made a mistake in the card number. Please try again!\n");
        }
        return cardExists;
    }


    private static void closeAccount() {
        myDB.deleteCard(currentCardNum);
        System.out.println("\nThe account has been closed!\n");
        mainMenu();
    }

    private static void logout() {
        currentCardNum = "";
        System.out.println("You have successfully logged out!\n");
        mainMenu();
    }

}
