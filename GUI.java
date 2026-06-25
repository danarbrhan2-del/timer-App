import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GUI {

    myRunble displingTime = new myRunble();
    Thread displingTimeThread = new Thread(displingTime);




    Scanner scr = new Scanner(System.in);
    alarmLogic alarm = new alarmLogic();



    public void Welcoming(){
        System.out.println("******************************");
        System.out.println("welcome to the timer app");
        System.out.println("******************************");
    }

    public int askingForChoss(){
        System.out.println("""
                for setting a alarm enter 1
                for changing the song for the alarm enter 2
                for displing the time enter 3
                for exsiting the app enter 4
                """);
        return scr.nextInt();
    }

    public int settengTheAlarmMinut(int timeToRing){
        System.out.println("enter the minut:");
        return scr.nextInt();
    }

    public void displayTimeNow() {
        System.out.println("Time is running. Enter 1 to go back to main menu:");
        displingTimeThread.start();
        int chose = scr.nextInt();
        if (chose == 1){
            displingTime.stopThread();
        }
        System.out.println("\nReturning to main menu...");
    }

    public void timer(){
        System.out.println("enter the hour:");
        int hours = scr.nextInt();
        System.out.println("enter the minut:");
        int minutes = scr.nextInt();

        // Convert to total seconds
        int totalSeconds = (hours * 3600) + (minutes * 60);
        alarm.timer(hours, minutes);

        myRunble displayTimeLeft = new myRunble() {
            @Override
            public void run() {
                for (int timeLeft = totalSeconds; timeLeft >= 0; timeLeft--) {
                    int h = timeLeft / 3600;
                    int m = (timeLeft % 3600) / 60;
                    int s = timeLeft % 60;

                    System.out.print("\r" + h + ":" + String.format("%02d", m) + ":" + String.format("%02d", s));

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Thread displingTimeLeftThred = new Thread(displayTimeLeft);
        displingTimeLeftThred.start();
        try {
            displingTimeLeftThred.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
