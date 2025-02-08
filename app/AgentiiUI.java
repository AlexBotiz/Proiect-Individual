/**
 * @author Botiz Alexandru-Gabriel
 */
package app;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * @variable agentiiList Lista cu agentii
 * @variable listModel Modelul pentru lista cu agentii
 */
public class AgentiiUI extends JFrame {

    private JList<String> agentiiList;
    private DefaultListModel<String> listModel;

    public AgentiiUI() {
        setTitle("Agențiile de Turism");
        setBounds(450, 250, 400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
       
        listModel = new DefaultListModel<>();
        
        agentiiList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(agentiiList);
        scrollPane.setBackground(Color.decode("#a2d5f2"));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#1a83be"), 2));
        add(scrollPane, BorderLayout.CENTER);
       
        loadAgentii();

        setVisible(true);
    }

    /**
     * Functie care citeste agentiile din baza de date si le afiseaza in aplicatie
     */
    private void loadAgentii() {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "";

        String query = "SELECT Agentie_ID, Nume_Agentie FROM Agentii";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("Agentie_ID");
                String numeAgentie = resultSet.getString("Nume_Agentie");
                listModel.addElement(id + "."+ " - " + numeAgentie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
