import java.util.Scanner;

public class GUI {

    private final Scanner scr;
    private final AlarmLogic alarm;

    public GUI(Scanner scr, AlarmLogic alarm) {
        this.scr = scr;
        this.alarm = alarm;
    }

    public void welcoming() {
        System.out.println("******************************");
        System.out.println("welcome to the timer app");
        System.out.println("******************************");
    }

    public int askingForChoice() {
        return InputUtil.readInt(scr, """

                for setting an alarm enter 1
                for changing the song for the alarm enter 2
                for displaying the time enter 3
                for stopping/cancelling the alarm enter 4
                for exiting the app enter 5
                """);
    }

    /** A new runnable + thread every time, so this can be used again and again. */
    public void displayTimeNow() {
        MyRunnable clock = new MyRunnable();
        Thread clockThread = new Thread(clock);
        System.out.println("Time is running. Press Enter to go back to the main menu:");
        clockThread.start();

        scr.nextLine();                 // any Enter press returns to the menu
        stopAndWait(clock, clockThread);
        System.out.println("\nReturning to main menu...");
    }

    /** Sets the alarm, shows the countdown, and lets the user go back to the menu. */
    public void timer() {
        int hours = InputUtil.readIntInRange(scr, "enter the hour:", 0, 10_000);
        int minutes = InputUtil.readIntInRange(scr, "enter the minute:", 0, 59);

        if (hours == 0 && minutes == 0) {
            System.out.println("the time must be more than 0.");
            return;
        }

        alarm.setAlarm(hours, minutes);
        System.out.println("Alarm set. Press Enter to go back to the menu (the alarm keeps running):");

        Countdown countdown = new Countdown(alarm);
        Thread countdownThread = new Thread(countdown);
        countdownThread.start();

        scr.nextLine();
        stopAndWait(countdown, countdownThread);
        System.out.println("\nReturning to main menu...");
    }

    public void stopAlarm() {
        alarm.cancelAlarm();
        System.out.println("alarm stopped.");
    }

    private void stopAndWait(Object runnable, Thread thread) {
        if (runnable instanceof MyRunnable r) {
            r.stopThread();
        } else if (runnable instanceof Countdown c) {
            c.stopThread();
        }
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /** Shows the time left, calculated from the real alarm time (never drifts). */
    private static class Countdown implements Runnable {
        private final AlarmLogic alarm;
        private volatile boolean running = true;

        Countdown(AlarmLogic alarm) {
            this.alarm = alarm;
        }

        void stopThread() {
            running = false;
        }

        @Override
        public void run() {
            while (running && alarm.isWaiting()) {
                long left = alarm.secondsLeft();
                long h = left / 3600;
                long m = (left % 3600) / 60;
                long s = left % 60;
                System.out.print("\r" + h + ":" + String.format("%02d", m) + ":" + String.format("%02d", s) + "   ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
