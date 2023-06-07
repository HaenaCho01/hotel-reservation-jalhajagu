package util;

import java.util.Scanner;

public class ConsoleUtil {
    private Scanner scanner;

    public ConsoleUtil(){
        this.scanner = new Scanner(System.in);
    }

    public String getValueOf(String label){
        System.out.print(label + " : ");
        String inputStr = scanner.nextLine();
        inputStr = inputStr.trim();
        return inputStr;
    }

    public void printLine() {
        System.out.println("-----------------------------------");
    }
}
