/**
 * @author Botiz Alexandru-Gabriel
 */
package app;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import codOferte.Oferta;
import codRezervare.Rezervare;
import codRezervare.RezervareUI;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import oracle.jdbc.OracleDriver;
import codOferte.*;
import java.awt.SystemColor;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
public class AppUi extends JFrame {

	/**
	 * @variable contentPane este frame-ul aplicatiei
	 * @variable listModel este modelul pentru lista cu oferte
	 * @variable listaOferte este lista cu oferte
	 * @variable listaModel este modelul pentru lista cu rezervari
	 * @variable listaRezervari este lista cu rezervari
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<Oferta> listModel; 
	private JList<Oferta> listaOferte;
	private DefaultListModel<Rezervare> listaModel; 
	private JList<Rezervare> listaRezervari;
	

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppUi frame = new AppUi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public AppUi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		 contentPane.setBackground(Color.decode("#a2d5f2"));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Oferte de Vacanta 2024");
		lblNewLabel.setBounds(266, 95, 249, 31);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		lblNewLabel.setForeground(Color.decode("#1a83be"));
		
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(339, 51, 1, 2);
		separator.setBackground(Color.decode("#3a9acb"));
		
		contentPane.add(separator);
		
		
		// Butoanele de sortare 
		
		/**
		 * Buton pentru sortare crescatoare
		 */
		
		JButton sortCresc = new JButton("");
		sortCresc.setIcon(new ImageIcon("C:\\Users\\Alex\\Downloads\\Crescator.png"));
		sortCresc.setForeground(UIManager.getColor("CheckBox.darkShadow"));
		sortCresc.setBackground(Color.decode("#7ec8e3")); 
        sortCresc.setForeground(Color.WHITE); 
		sortCresc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		sortCresc.addActionListener(new ActionListener() {
			
			/**
			 * Functie pentru sortarea listei cand se apasa butonul
			 */
				public void actionPerformed(ActionEvent e) {
                ArrayList<Oferta> sortedList = new ArrayList<>();
                for (int i = 0; i < listModel.size(); i++) {
                    sortedList.add(listModel.getElementAt(i));
                }
                Collections.sort(sortedList);
                updateListModel(sortedList);
            }
		});
		sortCresc.setBounds(20, 64, 87, 72);
		contentPane.add(sortCresc);
		
		/**
		 * Buton pentru sortare descrescatoare
		 */
		
		JButton sortDescresc = new JButton("");
		sortDescresc.setIcon(new ImageIcon("C:\\Users\\Alex\\Downloads\\sort-amount-down_optimized.png"));
		sortDescresc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		 sortDescresc.setBackground(Color.decode("#5ab1d8"));
	        sortDescresc.setForeground(Color.WHITE);
		sortDescresc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/**
				 * Functie pentru sortarea listei cand se apasa butonul
				 */
				
				ArrayList<Oferta> descrescSortedList = new ArrayList<>();
                for (int i = 0; i < listModel.size() ; i++) {
                    descrescSortedList.add(listModel.getElementAt(i));
                }
                descrescSortedList.sort((a, b) -> a.compareToD(b));
                updateListModel(descrescSortedList);
			}
		});
		sortDescresc.setBounds(628, 64, 87, 72);
		contentPane.add(sortDescresc);
		
		/**
		 * Initializarea Listei cu Oferte
		 */
		
		listModel = new DefaultListModel<>();
		this.listaOferte = new JList<>(listModel);
        
        
        listaOferte.setBounds(20, 136, 695, 301);
        listaOferte.setBackground(Color.decode("#a2d5f2"));
        listaOferte.setBorder(BorderFactory.createLineBorder(Color.decode("#1a83be"), 2));
        contentPane.add(listaOferte);
        listaOferte.setVisible(true);
        
        /**
         * Initializarea Listei cu Rezervari
         */
        
        listaModel = new DefaultListModel<>();
        this.listaRezervari = new JList<>(listaModel);
        listaRezervari.setBorder(new LineBorder(new Color(0, 0, 0)));
        listaRezervari.setBackground(new Color(163, 213, 242));
        
        
        listaRezervari.setBounds(773, 137, 243, 223);
        listaOferte.setBackground(Color.decode("#a2d5f2")); 
        listaOferte.setBorder(BorderFactory.createLineBorder(Color.decode("#1a83be"), 2));
        contentPane.add(listaRezervari);
        
        /**
         * Label 
         */
        
        JLabel lblNewLabel_3 = new JLabel("Oferte Rezervate");
        lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 26));
        lblNewLabel_3.setBounds(799, 80, 182, 60);
        lblNewLabel_3.setForeground(Color.decode("#1a83be"));
        contentPane.add(lblNewLabel_3);
        
        JButton btnNewButton = new JButton("Agentii");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new AgentiiUI().setVisible(true);
        	}
        });
        btnNewButton.setBackground(new Color(163, 213, 242));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
        btnNewButton.setBounds(799, 400, 182, 37);
        contentPane.add(btnNewButton);
        

        /**
         * Functie pentru a deschide o noua fereastra cu detalii suplimentare despre oferta
         */
        
        listaOferte.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {                 	
                    int i = listaOferte.locationToIndex(e.getPoint());
                    if (i != -1) {
                        Oferta selectedOffer = listModel.getElementAt(i);
                        new RezervareUI(AppUi.this , selectedOffer).setVisible(true);
                    }
                }
            }
        });

        /**
         * Apelul functiei adaugaOferte 
         */
        adaugaOferte();
        /**
         * Apelul functiei adaugaRezervari 
         */
        adaugaRezervari();      
	}
	

	    /**
	     * Functie pentru a adauga ofertele din baza de date
	     */
	    private void adaugaOferte() {
	        
	        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
	        String username = "root"; 
	        String password = "";      

	        try {
	           
	            Connection connection = DriverManager.getConnection(url, username, password);
	            Statement statement = connection.createStatement();
	           
	            ResultSet resultSet = statement.executeQuery("SELECT * FROM offerv4");	
	           
	            while (resultSet.next()) {
	            	int id = resultSet.getInt(1);
	            	String name = resultSet.getString(2);
	                String location = resultSet.getString(3);
	                int pret = resultSet.getInt(4);
	                int length = resultSet.getInt(5);
	                int a_id = resultSet.getInt(6);
	                Oferta oferta = new Oferta(id, name, location, pret, length , a_id);
	                listModel.addElement(oferta);	               
	            }

	            
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    /**
	     * Functie pentru a adauga rezervarile din baza de date
	     */
	    
	    private void adaugaRezervari() {
	        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
	        String username = "root"; 
	        String password = "";      

	        try {
	            Connection connection = DriverManager.getConnection(url, username, password);
	            Statement statement = connection.createStatement();
	          
	            ResultSet resultSet = statement.executeQuery("SELECT * FROM rezervari");

	            while (resultSet.next()) {
	                String dataInceput = resultSet.getString("data_inceput");
	                String dataFinalizare = resultSet.getString("data_finalizare");
	                String locatie = resultSet.getString("locatie");
	                int persoane = resultSet.getInt("persoane");
	                int ofertaId = resultSet.getInt("oferta_id");
	                String ofertaName = resultSet.getString("oferta_name");
	               
	                Rezervare rezervare = new Rezervare(dataInceput, dataFinalizare, locatie, persoane, ofertaId, ofertaName);
	                listaModel.addElement(rezervare);
	            }

	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        	     
	        listaRezervari.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getClickCount() == 2) { 
	                    int i = listaRezervari.locationToIndex(e.getPoint());
	                    if (i != -1) {
	                        Rezervare rezervareSelectata = listaModel.getElementAt(i);
	                        
	                        int confirmare = JOptionPane.showConfirmDialog(
	                            AppUi.this,
	                            "Vrei sa stergi aceasta rezervare?\n" + rezervareSelectata.toString(),
	                            "Confirmare stergere",
	                            JOptionPane.YES_NO_OPTION
	                        );

	                        if (confirmare == JOptionPane.YES_OPTION) {   
	                            if (stergeRezervareDinBazaDeDate(rezervareSelectata)) {	                                
	                                listaModel.remove(i);
	                                JOptionPane.showMessageDialog(
	                                    AppUi.this,
	                                    "Rezervarea a fost stearsa cu succes!",
	                                    "Succes",
	                                    JOptionPane.INFORMATION_MESSAGE
	                                );
	                            } else {
	                                JOptionPane.showMessageDialog(
	                                    AppUi.this,
	                                    "A aparut o problemă la stergerea rezervarii.",
	                                    "Eroare",
	                                    JOptionPane.ERROR_MESSAGE
	                                );
	                            }
	                        }
	                    }
	                }
	            }
	        });

	    }

        
        /**
         * Functie pentru sortarea listei
         */
        private void updateListModel(ArrayList<Oferta> sortedList) {
            listModel.clear();
            for (Oferta oferta : sortedList) {
                listModel.addElement(oferta);
            }
        }
        
        /**
         * Functie pentru adaugarea rezervarilor in lista
         */
        public void adaugaRezervare(Rezervare rezervare) {
            listaModel.addElement(rezervare);
        }

        /**
         * Functia care sterge rezervarea din baza de date
         * @param url este aadresa bazei de date
         * @param username este username-ul bazei de date
         * @param password este parola de la baza de date
         * @param rezervare Rezervarea care trebuie stearsa
         * @return true daca rezervarea a fost stearsa cu succes, altfel false
         */
        private boolean stergeRezervareDinBazaDeDate(Rezervare rezervare) {
            String url = "jdbc:mysql://localhost:3306/jdbcdemo";
            String username = "root"; 
            String password = ""; 

            String query = "DELETE FROM rezervari WHERE oferta_id = ? AND data_inceput = ?";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, rezervare.oID);
                statement.setString(2, rezervare.dataInceperii);

                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
}

