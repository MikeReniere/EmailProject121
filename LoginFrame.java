
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JTextField;
import javax.swing.JButton;

public class LoginFrame extends JFrame implements ActionListener, EmailConstants {

    //Declaration of JFrame variables
	private JPanel contentPane;
	private JTextField jtfUsername;
	private JTextField jtfHost;
	private String host;

   private String currentUser = "";

	public static void main(String[] args) {
		new LoginFrame();
	}

	public LoginFrame() {
		this.setTitle("Login");
		this.setSize(450,300);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 450, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsername.setBounds(66, 17, 82, 30);
		contentPane.add(lblUsername);
		
		jtfUsername = new JTextField();
		jtfUsername.setBounds(160, 16, 208, 20);
		contentPane.add(jtfUsername);
		jtfUsername.setColumns(10);
		
		JButton jbLogin = new JButton("Login");
		jbLogin.setBounds(181, 70, 89, 23);
		contentPane.add(jbLogin);
		
		JLabel lblNewUser = new JLabel("New User? ");
		lblNewUser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewUser.setBounds(191, 98, 74, 18);
		contentPane.add(lblNewUser);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setBounds(85, 130, 138, 23);
		contentPane.add(btnCreateAccount);
		
		JButton btnLoginAsGuest = new JButton("Login as Guest");
		btnLoginAsGuest.setBounds(233, 130, 154, 23);
		contentPane.add(btnLoginAsGuest);
		
		this.setResizable(false);
		this.setVisible(true);

		jbLogin.addActionListener(this);
		btnCreateAccount.addActionListener(this);
		btnLoginAsGuest.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

	    switch (ae.getActionCommand()){
            case "Login":
                String userName1 = jtfUsername.getText();
                Account a = new Account();
                System.out.println("The username entered is " + userName1);
                if(a.LogInUser(userName1)){
                  System.out.println("User " + userName1 + " logged in.");
                  currentUser = userName1;
                  }
                break;
            case "Create Account":
                String userName2 = jtfUsername.getText();
                Account b = new Account();
                System.out.println("The username is " + userName2);
                b.MakeAccount(userName2);
                if(b.LogInUser(userName2)){
                  System.out.println("User " + userName2 + " logged in.");
                  currentUser = userName2;
                  }
                break;
            case "Login as Guest":
               currentUser = new Account().setDefaultName();


        }

	}

   public String getCurrentUser() {
      return currentUser;
   }
}
