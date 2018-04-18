import java.awt.*;
import javax.swing.*;
import java.awt.Font;
import javax.swing.JTextField;

public class CreateAccount extends JFrame {
	//Declaration of JFrame Variables 
	private JPanel contentPane;
	private JTextField textField;
	
	/*main*/
	public static void main(String[] args) {

		new CreateAccount();
	}
	/*constructor (creates GUI)*/
	public CreateAccount() {
		this.setTitle("Create an Account");
		//this.setSize(450,224);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 451, 153);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setBounds(312, 90, 123, 23);
		contentPane.add(btnCreateAccount);
		
		JLabel lblUsername = new JLabel("Pick a username: ");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsername.setBounds(44, 49, 117, 14);
		contentPane.add(lblUsername);
		
		textField = new JTextField();
		textField.setBounds(171, 47, 188, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		this.setResizable(false);
		this.setVisible(true);
	}
}
