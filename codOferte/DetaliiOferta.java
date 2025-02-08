/**
 * @author Botiz Alexandru-Gabriel
 */
package codOferte;
import javax.swing.*;
import codRezervare.*;
import javax.swing.border.EmptyBorder;
import app.AppUi;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class DetaliiOferta extends JFrame {

    private static final long serialVersionUID = 1L;

    public DetaliiOferta(Oferta oferta) {
    	
        setTitle("Detalii Oferta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(400, 200, 400, 300); 
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(Color.decode("#a2d5f2"));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        /**
         * Butonul pentru rezervare
         */
        
        JButton rezervare = new JButton("Rezervare");
		rezervare.setFont(new Font("Times New Roman", Font.PLAIN, 20));          
		rezervare.setBounds(20, 100, 350, 30);  
		rezervare.setBackground(Color.decode("#7ec8e3")); 
        rezervare.setForeground(Color.WHITE);	
        rezervare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RezervareUI rezervareFrame = new RezervareUI((AppUi) getOwner(), oferta);
                rezervareFrame.setVisible(true);
            }
        });
		contentPane.add(rezervare);
		
        /**
         * Informatii aditionale pentru oferta selectata
         */
		
        JLabel lblLocation = new JLabel("Locatie: " + oferta.location);
        lblLocation.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblLocation.setForeground(Color.decode("#1a83be"));
        lblLocation.setBounds(20, 20, 500, 30);
        contentPane.add(lblLocation);

        JLabel lblPret = new JLabel("Pret: " + oferta.pret + " lei/noapte");
        lblPret.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblPret.setBounds(20, 60, 350, 30);
        lblPret.setForeground(Color.decode("#1a83be"));
        contentPane.add(lblPret);
        

    }
}
