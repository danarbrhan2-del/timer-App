import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.sound.sampled.*;

public class Song {

    // Only PCM .wav files work (Java can't read mp3 by default).
    
    private static final String[] SONG_FILES = {
            "songs/alarm1.wav",
            "songs/alarm2.wav"
    };

    // Labels now match the files above.
    private static final String[] SONG_NAMES = {
            "FAST alarm ringtone",
            "alarm2"
    };

    private final Scanner scr;          // shared scanner, not a new one
    private String filePath = SONG_FILES[0];
    private Clip clip;

    public Song(Scanner scr) {
        this.scr = scr;
    }

    public void changingTheSong() {
        System.out.println("choose the alarm song:");
        for (int i = 0; i < SONG_NAMES.length; i++) {
            System.out.println("enter " + (i + 1) + " for " + SONG_NAMES[i]);
        }
        int chose = InputUtil.readIntInRange(scr, "your choice:", 1, SONG_FILES.length);
        filePath = SONG_FILES[chose - 1];
        System.out.println("song changed to: " + SONG_NAMES[chose - 1]);
    }

    /** Starts the alarm sound and loops it until stop() is called. */
    public synchronized void start() {
        stop(); // never play two clips at once

        File file = resolveSongFile();
        if (!file.exists()) {
            System.out.println("song file not found: " + file.getAbsolutePath());
            return;
        }

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(file)) {
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("unsupported audio file (use PCM .wav): " + e.getMessage());
            clip = null;
        } catch (LineUnavailableException | IOException e) {
            System.out.println("could not play the song: " + e.getMessage());
            clip = null;
        }
    }

    private File resolveSongFile() {
        Path relativePath = Paths.get(filePath);
        Path workingDirectory = Paths.get("").toAbsolutePath();
        Path classLocation;
        try {
            classLocation = Paths.get(Song.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            System.out.println("could not resolve song file location: " + e.getMessage());
            return workingDirectory.resolve(relativePath).toFile();
        }

        Path[] locations = {workingDirectory, classLocation};
        for (Path location : locations) {
            Path directory = Files.isDirectory(location) ? location : location.getParent();
            while (directory != null) {
                Path candidate = directory.resolve(relativePath).normalize();
                if (Files.isRegularFile(candidate)) {
                    return candidate.toFile();
                }
                directory = directory.getParent();
            }
        }

        return workingDirectory.resolve(relativePath).toFile();
    }

    /** Stops and releases the clip so resources are not leaked. */
    public synchronized void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }

    public synchronized boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
