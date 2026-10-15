import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scr = new Scanner(System.in);          
        Song song = new Song(scr);                     
        AlarmLogic alarm = new AlarmLogic(song);
        GUI gui = new GUI(scr, alarm);

        boolean isRunning = true;
        gui.welcoming();

        do {
            int choice = gui.askingForChoice();
            switch (choice) {
                case 1 -> gui.timer();
                case 2 -> song.changingTheSong();
                case 3 -> gui.displayTimeNow();
                case 4 -> gui.stopAlarm();
                case 5 -> isRunning = false;
                default -> System.out.println("wrong input!");
            }
        } while (isRunning);

        alarm.shutdown();   // cancel timer and silence sound
        scr.close();
        System.out.println("bye!");
        System.exit(0);
    }
}
