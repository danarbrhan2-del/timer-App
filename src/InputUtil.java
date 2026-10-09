import java.util.Scanner;

/**
 * Safe console input. Everything reads whole lines from ONE shared Scanner,
 * so letters or empty input never crash the program.
 */
public class InputUtil {

    public static int readInt(Scanner scr, String prompt) {
        while (true) {
            System.out.println(prompt);
            String line = scr.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("wrong input, please enter a whole number.");
            }
        }
    }

    public static int readIntInRange(Scanner scr, String prompt, int min, int max) {
        while (true) {
            int value = readInt(scr, prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("the number must be between " + min + " and " + max + ".");
        }
    }
}
