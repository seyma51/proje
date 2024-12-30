package LibraryManagement;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;  // Kaydırma eklemek için import ekledik
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class kitapislem extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea textArea;  // Burada textArea sınıfın parçası olmalı

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    kitapislem frame = new kitapislem();
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
    public kitapislem() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 783, 458);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("KİTAP İŞLEMLERİ");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(270, 31, 209, 22);
        contentPane.add(lblNewLabel);
        
        JButton btnNewButton = new JButton("İADE ET");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kitapekle kitapekle = new kitapekle();
                kitapekle.setVisible(true);
            }
        });
        btnNewButton.setBounds(417, 325, 130, 21);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("ÖDÜNÇ AL");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kitapcikar kitapcikar = new kitapcikar();
                kitapcikar.setVisible(true);
            }
        });
        btnNewButton_1.setBounds(244, 325, 130, 21);
        contentPane.add(btnNewButton_1);
        
        // Kitapları listelemek için JTextArea
        textArea = new JTextArea();
        textArea.setEditable(false);  // Kullanıcı yazmasın diye sadece okunabilir yapıyoruz
        
        // JScrollPane ekleniyor
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(40, 80, 698, 230);  // Scroll Pane'in boyutlarını ayarlıyoruz
        contentPane.add(scrollPane);
        
        JButton btnNewButton_2 = new JButton("KİTAP LİSTELE");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listKitaplar();  // Kitapları listeleme metodunu çağır
            }
        });
        btnNewButton_2.setBounds(40, 325, 130, 21);
        contentPane.add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("GERİ");
        btnNewButton_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		girismenu girismenu=new girismenu();
        		girismenu.setVisible(true);
        		dispose();
        		
        	}
        });
        btnNewButton_3.setBounds(608, 325, 130, 21);
        contentPane.add(btnNewButton_3);
    }

    // Kitapları veritabanından çeken ve JTextArea'ya yazdıran metot
    private void listKitaplar() {
        // Veritabanı bağlantısı
        try (Connection conn = adminbaglanti.getConnection()) {
            // SQL sorgusu ile kitapları seç
            String sql = "SELECT idbooks, kitapadi, sayfasayisi, kitapturu, yazaradi FROM books";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            // Sonuçları JTextArea'ya yazdır
            textArea.setText(""); // Önceki metinleri temizle
            while (rs.next()) {
                int id = rs.getInt("idbooks");
                String kitapAdi = rs.getString("kitapadi");
                int sayfaSayisi = rs.getInt("sayfasayisi");
                String kitapTuru = rs.getString("kitapturu");
                String yazarAdi = rs.getString("yazaradi");

                // Verileri satır satır yazdır
                textArea.append("ID: " + id + ", Kitap Adı: " + kitapAdi + ", Sayfa Sayısı: " + sayfaSayisi + 
                                ", Kitap Türü: " + kitapTuru + ", Yazar Adı: " + yazarAdi + "\n");
            }

            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}