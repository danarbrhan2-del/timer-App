import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class song {
    String filePath = "D:\\programing\\programing\\timerApp\\songs\\Zakaria Abdulla - Rebwar  Lyrics.wav";
    Scanner scr = new Scanner(System.in);

    public void changingTheSong() {



        String[] songs = {"D:\\programing\\programing\\timerApp\\songs\\Zakaria Abdulla - Rebwar  Lyrics.wav",
                "D:\\programing\\programing\\timerApp\\songs\\Hasan Zirak - Har Tom Dawe To - with Lyrics - HD  حەسەن زیرەک - هەر تۆم دەوێ تۆ.wav"
        };
        int chose = 0;
        do {
            System.out.println("enter 1 for Zakaria Abdulla - Rebwar song\nenter 2 for Hasan Zirak - Har Tom Dawe To song");
            chose = scr.nextInt();


            if (chose == 1) {
                filePath = songs[chose - 1];
            } else if (chose == 2) {
                filePath = songs[chose - 1];

            } else {
                System.out.println("wrong ,you must enter 1 or 2");
            }
        } while (chose != 1 && chose != 2);
    }

    public void start() {

        File file = new File(filePath);
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);) {


            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
