package stocktrack;

import reception.ReceptionistDashboard;
import homepage.Homepage;
import login.Login;

/**
 *
 * @author Joy De Castro
 */

public class StockTrack {
    public static void main(String[] args) {
        Login loginFrame = new Login();
        loginFrame.setVisible(true);

        // Wait until Login frame is closed
        while (loginFrame.isDisplayable()) {
            try {
                Thread.sleep(100); // Small delay
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // check login status and role bago bumukas
        if (loginFrame.isLoginSuccessful()) {
            String role = loginFrame.getRole();

            // Based on the role, navigate to the correct screen
            java.awt.EventQueue.invokeLater(() -> {
                if (null == role) {
                    System.out.println("Invalid role. Exiting program.");
                } else switch (role) {
                    case "manager" -> new Homepage().setVisible(true); // Open the PanelBorder for Admin      

                    case "receptionist" -> new ReceptionistDashboard(); // Open Reception for Receptionist
                    default -> System.out.println("Invalid role. Exiting program.");
                }
            });
        } else {
            System.out.println("Login failed. Exiting program.");
        }
    }
}