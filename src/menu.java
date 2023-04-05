import javax.swing.*;

// Main class Menu; should run Menu Interface which connects to all the modules in our code
public class menu {

    public static void main(String[] args) {
        // Runs Swing for the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuInterface();
            }
        });

        
    }


}
