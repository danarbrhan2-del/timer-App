import java.util.Timer;
import java.util.TimerTask;

public class AlarmLogic {

    private final Song song;            // the SAME song object the menu changes
    private Timer timer;
    private long ringAtMillis;

    public AlarmLogic(Song song) {
        this.song = song;
    }

    /** Sets a single alarm. Any previous alarm is cancelled first. */
    public synchronized void setAlarm(int hours, int minutes) {
        cancelAlarm();

        long delay = hours * 3_600_000L + minutes * 60_000L;   // long: no overflow
        ringAtMillis = System.currentTimeMillis() + delay;

        timer = new Timer("alarm-timer", true);                 // daemon: won't block exit
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\n*** ALARM! *** (choose 4 in the menu to stop it)");
                song.start();
            }
        }, delay);                                              // fires once, no repeat
    }

    /** Cancels a pending alarm and silences a ringing one. */
    public synchronized void cancelAlarm() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        ringAtMillis = 0;
        song.stop();
    }

    public synchronized boolean isWaiting() {
        return timer != null && System.currentTimeMillis() < ringAtMillis;
    }

    public synchronized long secondsLeft() {
        return Math.max(0, (ringAtMillis - System.currentTimeMillis() + 999) / 1000);
    }

    public void shutdown() {
        cancelAlarm();
    }
}
