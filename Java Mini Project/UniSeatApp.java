import view.UniSeatView;
import controller.UniSeatController;
import model.UniSeatModel;
import view.LoginPanel;
import javax.swing.*;

public class UniSeatApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame loginFrame = new JFrame("Login");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            LoginPanel loginPanel = new LoginPanel(e -> {
                loginFrame.dispose(); // Close login window
                try {
                    UniSeatModel model = new UniSeatModel();
                    UniSeatView view = new UniSeatView();
                    UniSeatController controller = new UniSeatController(model, view);
                    controller.showApplication();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Error starting application: " + ex.getMessage(),
                        "Startup Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            });

            loginFrame.setContentPane(loginPanel);
            loginFrame.setSize(1200, 800);
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
        });
    }
}


// import model.UniSeatModel;
// import view.UniSeatView;
// import controller.UniSeatController;
// import javax.swing.*;

// public class UniSeatApp {
//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             try {
//                 UniSeatModel model = new UniSeatModel();
//                 UniSeatView view = new UniSeatView();
//                 UniSeatController controller = new UniSeatController(model, view);
//                 controller.showApplication();
//             } catch (Exception e) {
//                 e.printStackTrace();
//                 JOptionPane.showMessageDialog(null, 
//                     "Error starting application: " + e.getMessage(),
//                     "Startup Error", 
//                     JOptionPane.ERROR_MESSAGE);
//             }
//         });
//     }
// }