import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduledMain {

    public static void main(String[] args) {
        // Schedule the execution at a specific time
        scheduleExecution(14, 9); // 12:00 PM (noon)
    }

    public static void scheduleExecution(int hour, int minute) {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Check if the scheduled time is in the past, if so, schedule for the next day
        if (calendar.getTime().before(new Date())) {
            calendar.add(Calendar.DATE, 1); // move to the next day
        }

        // Create a Timer object
        Timer timer = new Timer();

        // Schedule the task to run at the specified time
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Execute the main class
                Main.main(new String[]{});
            }
        }, calendar.getTime());
    }
}
