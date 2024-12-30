package LibraryManagement;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class kitapcikar extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField_1;  // Kitap ID'si
    private JTextField textField_2;  // Kitap Adı
    private JTextField textField_3;  // Sayfa Sayısı
    private JTextField textField_4;  // Kitap Türü
    private JTextField textField_5;  // Yazar Adı

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    kitapcikar frame = new kitapcikar();
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
    public kitapcikar() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("ÖDÜNÇ ALMA ");
        lblNewLabel.setBounds(142, 21, 114, 13);
        contentPane.add(lblNewLabel);
        
        JLabel lblKitapId = new JLabel("Kitap ID:");
        lblKitapId.setBounds(40, 78, 70, 13);
        contentPane.add(lblKitapId);
        
        textField_1 = new JTextField();
        textField_1.setBounds(142, 78, 96, 19);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        
        JLabel lblKitapAdi = new JLabel("Kitap Adı:");
        lblKitapAdi.setBounds(40, 120, 70, 13);
        contentPane.add(lblKitapAdi);
        
        textField_2 = new JTextField();
        textField_2.setBounds(142, 120, 96, 19);
        contentPane.add(textField_2);
        textField_2.setColumns(10);
        
        JLabel lblSayfaSayisi = new JLabel("Sayfa Sayısı:");
        lblSayfaSayisi.setBounds(40, 160, 70, 13);
        contentPane.add(lblSayfaSayisi);
        
        textField_3 = new JTextField();
        textField_3.setBounds(142, 160, 96, 19);
        contentPane.add(textField_3);
        textField_3.setColumns(10);
        
        JLabel lblKitapTuru = new JLabel("Kitap Türü:");
        lblKitapTuru.setBounds(40, 200, 70, 13);
        contentPane.add(lblKitapTuru);
        
        textField_4 = new JTextField();
        textField_4.setBounds(142, 200, 96, 19);
        contentPane.add(textField_4);
        textField_4.setColumns(10);
        
        JLabel lblYazarAdi = new JLabel("Yazar Adı:");
        lblYazarAdi.setBounds(40, 240, 70, 13);
        contentPane.add(lblYazarAdi);
        
        textField_5 = new JTextField();
        textField_5.setBounds(142, 240, 96, 19);
        contentPane.add(textField_5);
        textField_5.setColumns(10);
        
        JButton btnSil = new JButton("ÖDÜNÇ AL");
        btnSil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteKitap();  // Kitap silme işlemi burada yapılacak
            }
        });
        btnSil.setBounds(297,125, 102, 21);
        contentPane.add(btnSil);
        
        // Geri Butonu
        JButton btnGeri = new JButton("GERİ");
        btnGeri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Kitap işlemleri sayfasına geri dön
                kitapislem kitapIslem = new kitapislem();
                kitapIslem.setVisible(true);
                dispose();  // Şu anki pencereyi kapat
            }
        });
        btnGeri.setBounds(297, 179, 102, 21);
        contentPane.add(btnGeri);
    }

    // Kitap silmek için metod
    private void deleteKitap() {
        String kitapId = textField_1.getText();  // Kullanıcıdan alınan kitap ID'si

        // Eğer ID girilmemişse, hata mesajı ver
        if (kitapId.isEmpty()) {
            System.out.println("Lütfen silmek istediğiniz kitabın ID'sini girin.");
            return;
        }

        // Veritabanına bağlantı işlemi
        try (Connection conn = adminbaglanti.getConnection()) {
            String sql = "DELETE FROM books WHERE idbooks = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(kitapId));  // Silinecek kitap ID'si

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Kitap ödünç alındı.");
            } else {
                System.out.println("Bu ID'ye sahip bir kitap bulunamadı.");
            }
            pstmt.close();
        } catch (SQLException ex) {
            System.out.println("Veritabanı hatası: " + ex.getMessage());
        }
    }
}