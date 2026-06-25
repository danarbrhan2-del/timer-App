
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class alarmLogic {
    song song = new song();
    Timer time = new Timer();
    int userHour ;
    int userMinut ;


    public void timer(int userHour, int userMinut) {
        this.userHour =userHour;
        this.userMinut= userMinut;

        int delay = (userHour * 1000 * 60 *60) +(userMinut *1000 * 60);
        int timeTheSongPlays = 100000;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                song.start();
            }
        };
        time.schedule(task,delay,timeTheSongPlays);

    }
}