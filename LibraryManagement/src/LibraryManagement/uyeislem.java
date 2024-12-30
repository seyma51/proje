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
import java.sql.SQLException;

public class uyeislem extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldId;         // Kullanıcı ID
    private JTextField textFieldKullaniciAdi;  // Kullanıcı Adı
    private JPasswordField passwordFieldSifre; // Şifre

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    uyeislem frame = new uyeislem();
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
    public uyeislem() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 450, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("ÜYE İŞLEMLERİ");
        lblNewLabel.setBounds(160, 28, 116, 13);
        contentPane.add(lblNewLabel);

        JLabel lblId = new JLabel("Kullanıcı ID:");
        lblId.setBounds(40, 80, 80, 13);
        contentPane.add(lblId);

        textFieldId = new JTextField();
        textFieldId.setBounds(140, 80, 150, 20);
        contentPane.add(textFieldId);
        textFieldId.setColumns(10);

        JLabel lblKullaniciAdi = new JLabel("Kullanıcı Adı:");
        lblKullaniciAdi.setBounds(40, 120, 80, 13);
        contentPane.add(lblKullaniciAdi);

        textFieldKullaniciAdi = new JTextField();
        textFieldKullaniciAdi.setBounds(140, 120, 150, 20);
        contentPane.add(textFieldKullaniciAdi);
        textFieldKullaniciAdi.setColumns(10);

        JLabel lblSifre = new JLabel("Şifre:");
        lblSifre.setBounds(40, 169, 80, 13);
        contentPane.add(lblSifre);

        passwordFieldSifre = new JPasswordField();
        passwordFieldSifre.setBounds(140, 166, 150, 20);
        contentPane.add(passwordFieldSifre);

        JButton btnEkle = new JButton("Üye Ekle");
        btnEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUser();  // Üye ekleme işlemi burada yapılacak
            }
        });
        btnEkle.setBounds(150, 209, 100, 21);
        contentPane.add(btnEkle);

        JButton btnGeri = new JButton("Geri");
        btnGeri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Geri gitme işlemi
                yonticimenu yonticimenu=new yonticimenu();
                yonticimenu.setVisible(true);
                dispose();
            }
        });
        btnGeri.setBounds(150, 240, 100, 21);
        contentPane.add(btnGeri);
    }

    // Üye eklemek için metod
    private void addUser() {
        String userId = textFieldId.getText();
        String username = textFieldKullaniciAdi.getText();
        
        String password = new String(passwordFieldSifre.getPassword());  // Şifreyi alıyoruz

        // Veritabanı işlemi yapılmadan önce boş alanları kontrol et
        if (userId.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("Lütfen tüm alanları doldurun.");
            return;
        }

        // Veritabanına bağlantı işlemi
        try (Connection conn = adminbaglanti.getConnection()) {
            String sql = "INSERT INTO kullanıcı (idkullanıcı, kullanıcıadı, sifre) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(userId));  // Kullanıcı ID'si
            pstmt.setString(2, username);  // Kullanıcı Adı
                 // Kullanıcı İsmi
            pstmt.setString(3, password);  // Şifre

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Üye başarıyla eklendi.");
            } else {
                System.out.println("Üye eklenemedi.");
            }
            pstmt.close();
        } catch (SQLException ex) {
            System.out.println("Veritabanı hatası: " + ex.getMessage());
        }
    }
}