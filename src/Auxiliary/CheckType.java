package Auxiliary;

import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;

public class CheckType {
    public static Date getDate(String message, String errorMessage) {
        Scanner sc = new Scanner(System.in);
        String input;
        Date output = null;
        boolean validValue = false;

        while (!validValue) {
            System.out.println(message);
            input = sc.nextLine().replaceAll("/", "-");
            try {
                output = StringToDate.parseDate(input);
                validValue = !validValue;
            } catch (ParseException e) {
                System.out.println(errorMessage);
            }
        }
        return output;
    }

    public static int getInt(String message, String errorMessage) {
        Scanner sc = new Scanner(System.in);
        String input;
        int output = 0;
        boolean validValue = false;

        while (!validValue) {
            System.out.println(message);
            input = sc.nextLine();
            try {
                output = Integer.parseInt(input);
                validValue = !validValue;
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
        return output;
    }

    public static float getFloat(String message, String errorMessage) {
        Scanner sc = new Scanner(System.in);
        String input;
        float output = 0;
        boolean validValue = false;

        while (!validValue) {
            System.out.println(message);
            input = sc.nextLine().replaceAll(",", ".");
            try {
                output = Float.parseFloat(input);
                validValue = !validValue;
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
        return output;
    }

    public static String getFileName(String message, String errorMessage) {
        Scanner sc = new Scanner(System.in);
        String fileName = null;

        while (fileName == null) {
            System.out.println(message);
            fileName = sc.nextLine();

            if (fileName.contains(".db") == false) {
                System.out.println(errorMessage);
                fileName = null;
            }
        }
        if (fileName.contains(FinalVariables.PATH)) {
            return fileName;
        } else {
            return FinalVariables.PATH + fileName;
        }
    }
}
