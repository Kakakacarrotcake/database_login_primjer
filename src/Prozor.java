import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.*;


public class Prozor implements KeyListener, ActionListener{

	private JButton button_login = new JButton("Login");
	private JButton button_signup = new JButton("Sign up");
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private int count = 0;
	private JLabel count_label = new JLabel("Broj je: " + count);
	private JTextField username_field = new JTextField();
	private JTextField password_field = new JTextField();
	private JLabel login_test = new JLabel();
	String username_string;
	String password_string;
	
	public void signup () {
		try (
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "hloptica");
			Statement stmt = conn.createStatement();
		) {
			String send = new String("select user_name from user where user_name = \""+ username_string +"\" ; ");
			ResultSet rset = stmt.executeQuery(send);
			
			if (rset.next()) {
				String user_baza = new String(rset.getString(1)); 
				login_test.setText("Korisnik "+user_baza+" vec postoji");
				
			} else {
				String send_2 = new String(" insert into user(user_name,password) values (\""+ username_string +"\",\""+password_string+"\"); ");
				int rset_2 = stmt.executeUpdate(send_2);
				if (rset_2 == 1) login_test.setText("Korisnik je uspjesno kreiran");		
			}
		}
		catch(SQLException ex) {
			 ex.printStackTrace();
		}
	}
	public void login () {
		try (
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "hloptica");
				Statement stmt = conn.createStatement();
			){
				String send = new String("select user_name from user where user_name = \""+ username_string +"\" ; ");
				ResultSet rset = stmt.executeQuery(send);
				
				if (rset.next()) {
					
					String send_3 =  "select password from user where user_name = \""+ username_string +"\" ; ";
					ResultSet rset_3 = stmt.executeQuery(send_3);
					rset_3.next();
					String sifra = new String(rset_3.getString(1));
					
					if (sifra.equals(password_string)) {
						login_test.setText("Korisnik je uspjesno logovan");
					} else {
						login_test.setText("Pogresna sifra");
					}
					
				} else {
					login_test.setText("Pogresan username");
				}
			}
			catch(java.sql.SQLIntegrityConstraintViolationException ex) {
		        login_test.setText("Korisnik vec postoji");
			}
			catch(SQLException ex) {
				 ex.printStackTrace();
			}
	}
	
	public Prozor() {
		frame.setBounds(0,0,500,300);
		frame.setTitle("Login");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3); //3 = exit on cllose
		
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel.setLayout(new GridLayout(0,1));
		
		button_login.setBounds(100, 200, 100, 20);
		button_signup.setBounds(300, 200, 100, 20);
		button_login.addActionListener(this);
		button_signup.addActionListener(this);
		
		JLabel username_label = new JLabel("Unesite ime");
		username_label .setBounds(100, 50, 100, 20);
		JLabel password_label = new JLabel("Unesite sifru");
		password_label .setBounds(100, 100, 100, 20);
		count_label.setBounds(200, 200, 50, 20);
		username_field.setBounds(200, 50, 200, 20);
		password_field.setBounds(200, 100, 200, 20);
		login_test.setBounds(200, 170, 200, 20);
		
		frame.add(login_test);
		frame.add(username_field);
		frame.add(password_field);
		//frame.add(count_label);
		frame.add(password_label);
		frame.add(username_label);
		frame.add(button_signup);
		frame.add(button_login);
		frame.add(panel);	
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) {
		if	(e.getSource() == button_login) {
			count++;
			count_label.setText("Broj je: " + count);
			username_string = username_field.getText();
			password_string = password_field.getText();
			login();
		}
		if (e.getSource() == button_signup) {
			count--;
			count_label.setText("Broj je: " + count);
			username_string = username_field.getText();
			password_string = password_field.getText();
			signup();
		}
	}
}















