package utcluj.aut;
import java.util.Timer;
import java.util.TimerTask;

class TrafficLightControlSystem {

    private enum LightState { RED, YELLOW, GREEN }
    private LightState currentLight;
    private Timer timer;
    private boolean pedestrianRequest;

    public TrafficLightControlSystem() {
        this.currentLight = LightState.RED;
        this.timer = new Timer();
        this.pedestrianRequest = false;
    }

    // Method to simulate pedestrian pressing the crossing button
    public void pedestrianPressButton() {
        System.out.println("Pedestrian request: Button pressed!");
        pedestrianRequest = true;
    }

    // Start the traffic light system
    public void startSystem() {
        timer.schedule(new LightChangeTask(), 0, 5000); // Change lights every 5 seconds
    }

    // Task that changes the traffic light based on the current state
    private class LightChangeTask extends TimerTask {
        @Override
        public void run() {
            // Prioritize pedestrian crossing if requested
            if (pedestrianRequest) {
                handlePedestrianCrossing();
                return;
            }

            // Normal traffic light state change
            switch (currentLight) {
                case RED:
                    System.out.println("Light is RED. Waiting for 5 seconds...");
                    currentLight = LightState.GREEN;
                    break;
                case GREEN:
                    System.out.println("Light is GREEN. Waiting for 5 seconds...");
                    currentLight = LightState.YELLOW;
                    break;
                case YELLOW:
                    System.out.println("Light is YELLOW. Waiting for 2 seconds...");
                    currentLight = LightState.RED;
                    break;
            }
        }

        // Handle pedestrian crossing (high priority task)
        private void handlePedestrianCrossing() {
            System.out.println("Pedestrian crossing request: Changing to RED for pedestrian...");
            currentLight = LightState.RED;
            System.out.println("Pedestrians crossing... Lights remain RED for 10 seconds.");

            // Keep light red for pedestrian crossing (simulate pedestrian crossing delay)
            try {
                Thread.sleep(10000); // 10 seconds for pedestrian crossing
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Pedestrian crossing completed. Resuming normal light cycle.");
            pedestrianRequest = false;  // Reset pedestrian request
        }
    }

    public static void main(String[] args) {
        TrafficLightControlSystem system = new TrafficLightControlSystem();
        system.startSystem();

        // Simulate pedestrian pressing the button after 7 seconds
        Timer pedestrianTimer = new Timer();
        pedestrianTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                system.pedestrianPressButton();
            }
        }, 7000); // Pedestrian presses button after 7 seconds
    }
}
