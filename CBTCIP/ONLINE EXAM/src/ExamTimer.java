import java.util.Timer;
import java.util.TimerTask;

public class ExamTimer {
    private Timer timer;
    private int secondsRemaining;

    public ExamTimer(int minutes) {
        this.secondsRemaining = minutes * 60;
        this.timer = new Timer();
    }

    public void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (secondsRemaining > 0) {
                    secondsRemaining--;
                    // Update UI with remaining time if needed
                    System.out.println("Time remaining: " + secondsRemaining + " seconds");
                } else {
                    // Auto-submit answers when time is up
                    submitAnswers();
                    cancelTimer(); // Stop the timer
                }
            }
        }, 0, 1000); // Schedule task to run every second
    }

    private void submitAnswers() {
        // Implement logic to submit answers automatically
        System.out.println("Time's up! Answers submitted.");
    }

    public void cancelTimer() {
        timer.cancel(); // Stop the timer
    }

    public static void main(String[] args) {
        ExamTimer examTimer = new ExamTimer(1); // 10 minutes timer
        examTimer.startTimer();

        // Example: Cancel timer after 5 seconds (for testing)
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                examTimer.cancelTimer();
                System.out.println("Timer cancelled.");
            }
        }, 5000);
    }
}


