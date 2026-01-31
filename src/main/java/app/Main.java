package app;

import app.view.AppFrame;
import app.database.DatabaseConnection;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DatabaseConnection.getConnection();

                AppFrame app = new AppFrame();
                app.setVisible(true);

                app.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        DatabaseConnection.closeConnection();
                    }
                });

            } catch (Exception e) {
                System.err.println("Erro ao iniciar aplicação: " + e.getMessage());
                e.printStackTrace();
                DatabaseConnection.closeConnection();
            }
        });
    }
}