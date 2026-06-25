import java.time.format.DateTimeFormatter;

public class main {
    public static void main(String []args){

        //1.well we need to make a task time so it rigs whe the time is up
        //2.we need to make it play muosec at the end

        GUI gui = new GUI();
        myRunble displingTime = new myRunble();
        Thread displingTimeThread = new Thread(displingTime);
        song song = new song();
        alarmLogic alarm = new alarmLogic();

        boolean isRuning = true;
        gui.Welcoming();

       do {
           int choss =
           gui.askingForChoss();
           switch (choss) {
               case 1 ->  gui.timer();
               case 2 -> song.changingTheSong();
               case 3 -> gui.displayTimeNow();
               case 4 ->  isRuning = false;
               default -> System.out.println("wrong input!");

           }
       }while (isRuning);
    }
}
