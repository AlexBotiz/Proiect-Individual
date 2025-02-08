/**
 * @author Botiz Alecandru-Gabriel
 */
package codRezervare;

import codOferte.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import app.AppUi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RezervareUI extends JFrame {
	/**
	 * @variable dataField este casuta cu text pentru data rezervarii
	 * @variable persoaneField este casuta cu text pentru numarul de persoane care fac rezervarea
	 */
    private static final long serialVersionUID = 1L;
    private JTextField dataField;
    private JTextField persoaneField;
    private AppUi parentFrame;

    public RezervareUI(AppUi parentFrame, Oferta oferta) {
        this.parentFrame = parentFrame;

        setTitle("Detalii Rezervare");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 250, 350, 200);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblData = new JLabel("Data (zz/ll/aaaa):");
        lblData.setBounds(20, 20, 120, 25);
        contentPane.add(lblData);

        dataField = new JTextField();
        dataField.setBounds(150, 20, 150, 25);
        contentPane.add(dataField);

        JLabel lblPersoane = new JLabel("Numar persoane:");
        lblPersoane.setBounds(20, 60, 120, 25);
        contentPane.add(lblPersoane);

        persoaneField = new JTextField();
        persoaneField.setBounds(150, 60, 150, 25);
        contentPane.add(persoaneField);

        JButton btnConfirma = new JButton("Confirma");
        btnConfirma.setBounds(100, 110, 120, 30);
        btnConfirma.setBackground(Color.decode("#7ec8e3"));
        btnConfirma.setForeground(Color.WHITE);
        btnConfirma.setFocusPainted(false);
        
        
        btnConfirma.addActionListener(new ActionListener() {
            /**
             * Functie care verifica daca casutele cu text s-au completat
             */
            public void actionPerformed(ActionEvent e) {
                String dataInceperii = dataField.getText();
                String persoane = persoaneField.getText();

                if (dataInceperii.isEmpty() || persoane.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Toate campurile sunt obligatorii!", "Eroare", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                   /**
                    * Formatarea datei
                    */
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    /**
                     * Parsarea datei
                     */
                    LocalDate startDate = LocalDate.parse(dataInceperii, formatter);
                    LocalDate endDate = startDate.plusDays(oferta.length);  
                    String dataFinalizarii = endDate.format(formatter);
                  
                    int persoaneInt = Integer.parseInt(persoane);
                    Rezervare rezervare = new Rezervare(dataInceperii, dataFinalizarii, oferta.location, persoaneInt, oferta.id, oferta.name);
                   
                    parentFrame.adaugaRezervare(rezervare);
                   
                    adaugaRezervareInDB(rezervare);

                    JOptionPane.showMessageDialog(null, "Rezervare confirmata!\nOferta #" + oferta.id + " - " + oferta.name +
                    		"\nData inceput: " + dataInceperii +
                            "\nSe incheie pe: " + dataFinalizarii + 
                            "\nNumar persoane: " + persoane);
                    dispose();
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Formatul datei este invalid. Folositi zz/ll/aaaa.", "Eroare", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Numarul de persoane trebuie să fie un numar valid.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
            /**
             * Functie care adauga rezervarile in baza de date
             * @param rezervare rezervarea care s-a facut in aplicatie
             */
            private void adaugaRezervareInDB(Rezervare rezervare) {
                String url = "jdbc:mysql://localhost:3306/jdbcdemo"; 
                String username = "root"; 
                String password = ""; 

                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    String sql = "INSERT INTO rezervari (data_inceput, data_finalizare, locatie, persoane, oferta_id, oferta_name) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setString(1, rezervare.dataInceperii);
                        preparedStatement.setString(2, rezervare.dataFinalizarii);
                        preparedStatement.setString(3, rezervare.locatia);
                        preparedStatement.setInt(4, rezervare.persoane);
                        preparedStatement.setInt(5, rezervare.oID);
                        preparedStatement.setString(6, rezervare.oName);

                        preparedStatement.executeUpdate();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Eroare la adaugarea rezervarii în baza de date: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }

        });


        contentPane.add(btnConfirma);
    }
}
