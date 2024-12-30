package LibraryManagement;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class uyeislem2 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldId;      // Kullanıcı ID
    private JTextField textFieldKullaniciAdi;  // Kullanıcı Adı
    private JPasswordField passwordFieldSifre;  // Kullanıcı Şifresi

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    uyeislem2 frame = new uyeislem2();
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
    public uyeislem2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 783, 458);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("ÜYE SİLME İŞLEMİ");
        lblNewLabel.setBounds(316, 10, 120, 16);
        contentPane.add(lblNewLabel);
        
        JLabel lblId = new JLabel("Kullanıcı ID:");
        lblId.setBounds(216, 59, 100, 20);
        contentPane.add(lblId);
        
        textFieldId = new JTextField();
        textFieldId.setBounds(300, 60, 150, 20);
        contentPane.add(textFieldId);
        textFieldId.setColumns(10);
        
        JLabel lblKullaniciAdi = new JLabel("Kullanıcı Adı:");
        lblKullaniciAdi.setBounds(216, 165, 100, 20);
        contentPane.add(lblKullaniciAdi);
        
        textFieldKullaniciAdi = new JTextField();
        textFieldKullaniciAdi.setBounds(300, 166, 150, 20);
        contentPane.add(textFieldKullaniciAdi);
        textFieldKullaniciAdi.setColumns(10);
        
        JLabel lblSifre = new JLabel("Şifre:");
        lblSifre.setBounds(216, 264, 100, 20);
        contentPane.add(lblSifre);
        
        passwordFieldSifre = new JPasswordField();
        passwordFieldSifre.setBounds(300, 265, 150, 20);
        contentPane.add(passwordFieldSifre);
        
        JButton btnSil = new JButton("ÜYE SİL");
        btnSil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUser();  // Üye silme fonksiyonunu çağırıyoruz
            }
        });
        btnSil.setBounds(464, 315, 100, 30);
        contentPane.add(btnSil);
        
        JButton btnNewButton = new JButton("GERİ");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                yonticimenu yonticimenu = new yonticimenu();
                yonticimenu.setVisible(true);
                dispose();
            }
        });
        btnNewButton.setBounds(196, 315, 100, 30);
        contentPane.add(btnNewButton);
    }

    // Üye silme işlemi
    private void deleteUser() {
        String idkullanıcı = textFieldId.getText();           // Kullanıcı ID'si
        String kullanıcıadı = textFieldKullaniciAdi.getText(); // Kullanıcı Adı
        String sifre = new String(passwordFieldSifre.getPassword()); // Şifre

        // Alanların boş olup olmadığını kontrol ediyoruz
        if (idkullanıcı.isEmpty() || kullanıcıadı.isEmpty() || sifre.isEmpty()) {
            System.out.println("Lütfen tüm alanları doldurun.");
            return;
        }

        try (Connection conn = adminbaglanti.getConnection()) {  // adminbaglanti veritabanı bağlantısını sağlıyoruz
            // Kullanıcı ID, kullanıcı adı ve şifreyi kontrol etmek için sorgu yazıyoruz
            String sql = "SELECT * FROM kullanıcı WHERE idkullanıcı = ? AND kullanıcıadı = ? AND sifre = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(idkullanıcı)); // Kullanıcı ID'si
            pstmt.setString(2, kullanıcıadı);               // Kullanıcı Adı
            pstmt.setString(3, sifre);                      // Şifre

            ResultSet rs = pstmt.executeQuery();  // Sorguyu çalıştırıyoruz

            if (rs.next()) {
                // Kullanıcı bulundu, şimdi silme işlemi yapıyoruz
                String deleteSql = "DELETE FROM kullanıcı WHERE idkullanıcı = ?";
                PreparedStatement deletePstmt = conn.prepareStatement(deleteSql);
                deletePstmt.setInt(1, Integer.parseInt(idkullanıcı)); // Kullanıcıyı silmek için ID'yi alıyoruz
                int rowsAffected = deletePstmt.executeUpdate();  // Kullanıcıyı siliyoruz
                if (rowsAffected > 0) {
                    System.out.println("Üye başarıyla silindi.");
                } else {
                    System.out.println("Üye silinemedi.");
                }
                deletePstmt.close();
            } else {
                System.out.println("Bu bilgileri taşıyan bir kullanıcı bulunamadı.");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            System.out.println("Veritabanı hatası: " + ex.getMessage());
        }
    }
}