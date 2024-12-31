package LibraryManagement;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class girismenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() { 
            public void run() {
                try {
                    girismenu frame = new girismenu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    

    public girismenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 783, 458);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("HOŞGELDİNİZ"); 
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(323, 75, 150, 25);
        contentPane.add(lblNewLabel);

        JButton btnKullaniciIslem = new JButton("KULLANICI İŞLEM");
        btnKullaniciIslem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Kullanıcı İşlem Ekranına Geçiş
                kullanicigirisekrani2 kullanicigirisekrani = new kullanicigirisekrani2();
                kullanicigirisekrani.setVisible(true);
                dispose(); // Mevcut pencereyi kapat
            }
        });
        btnKullaniciIslem.setBounds(300, 160, 150, 21);
        contentPane.add(btnKullaniciIslem);
        
        JButton btnYoneticiIslem = new JButton("YÖNETİCİ İŞLEM");
        btnYoneticiIslem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Yönetici İşlem Ekranına Geçiş
                kullanıcıgirisekrani kullanicigirisekrani = new kullanıcıgirisekrani();
                kullanicigirisekrani.setVisible(true);
                dispose(); // Mevcut pencereyi kapat
            }
        });
        btnYoneticiIslem.setBounds(300, 245, 150, 21);
        contentPane.add(btnYoneticiIslem);

        JButton btnNewButton = new JButton("ÇIKIŞ");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Uygulamayı kapat
                System.exit(0);
            }
        });
        btnNewButton.setBounds(300, 330, 150, 21);
        contentPane.add(btnNewButton);
    }

}