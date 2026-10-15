import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Prints the current time every second until stopThread() is called. */
public class MyRunnable implements Runnable {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.print("\r" + LocalTime.now().format(formatter));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;   // stopThread() interrupts the sleep so we stop immediately
            }
        }
    }

    public void stopThread() {
        running = false;
    }
}
