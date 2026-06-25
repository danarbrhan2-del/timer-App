import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class myRunble implements Runnable {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private volatile boolean running = true;
    @Override
    public void run() {
        while (running) {
            LocalTime time = LocalTime.now();
            System.out.print("\r" + time.format(formatter) );

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopThread() {
        running = false;
    }
}
