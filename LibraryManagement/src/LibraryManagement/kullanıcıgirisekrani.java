package LibraryManagement;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class kullanıcıgirisekrani extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldId;
    private JTextField textFieldUsername;
    private JTextField textFieldPassword;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    kullanıcıgirisekrani frame = new kullanıcıgirisekrani();
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
    public kullanıcıgirisekrani() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 783, 458);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textFieldId = new JTextField();
        textFieldId.setBounds(340, 93, 96, 19);
        contentPane.add(textFieldId);
        textFieldId.setColumns(10);

        textFieldUsername = new JTextField();
        textFieldUsername.setBounds(340, 177, 96, 19);
        contentPane.add(textFieldUsername);
        textFieldUsername.setColumns(10);

        textFieldPassword = new JTextField();
        textFieldPassword.setBounds(340, 256, 96, 19);
        contentPane.add(textFieldPassword);
        textFieldPassword.setColumns(10);

        JLabel lblId = new JLabel("ID");
        lblId.setBounds(260, 96, 70, 13);
        contentPane.add(lblId);

        JLabel lblUsername = new JLabel("Kullanıcı Adı");
        lblUsername.setBounds(260, 180, 70, 13);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel(" Şifre");
        lblPassword.setBounds(260, 262, 70, 13);
        contentPane.add(lblPassword);

        JButton btnLogin = new JButton("Giriş");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login(); // Giriş kontrolü
            }
        });
        btnLogin.setBounds(340, 335, 96, 19);
        contentPane.add(btnLogin);
        
        JLabel lblNewLabel = new JLabel("YÖNETİCİ GİRİŞ EKRANI");
        lblNewLabel.setBounds(282, 30, 150, 21);
        contentPane.add(lblNewLabel);
    }

    private void login() {
        String ID = textFieldId.getText();
        String kullanıcı_ad = textFieldUsername.getText();
        String sifre = textFieldPassword.getText();

        // Boş alan kontrolü
        if (ID.isEmpty() || kullanıcı_ad.isEmpty() || sifre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun.", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = adminbaglanti.getConnection()) {
            // SQL sorgusu
            String sql = "SELECT * FROM admin WHERE ID = ? AND kullanıcı_ad = ? AND sifre = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(ID));
            pstmt.setString(2, kullanıcı_ad);
            pstmt.setString(3, sifre);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Başarılı giriş
                JOptionPane.showMessageDialog(this, "Giriş başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

                // yonticimenu ekranına geçiş
                yonticimenu yoneticiMenu = new yonticimenu();
                yoneticiMenu.setVisible(true);
                dispose(); // Mevcut pencereyi kapat
            } else {
                // Hatalı giriş
                JOptionPane.showMessageDialog(this, "Giriş başarısız! Bilgilerinizi kontrol edin.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID bir sayı olmalıdır.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}