
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EmailClient extends JFrame implements ActionListener,EmailConstants {

    //Server-Client variables
   BufferedReader br;
   PrintWriter pw;
   Socket s = null;
    //Standard variables
   String host;
   //Declaration of JFrame variables
   private JTextField jtfTo;
   private JTextField jtfFrom;
   private JTextField jtfServer;
   private JButton btnMessage;
   private JButton jbConnect;

   //Other variables 
   String user = "";
   
   /*Main */
   public static void main(String[] args) {
      new EmailClient();
   }


   /*Constructor (Creates GUI)*/
   public EmailClient() {
      getContentPane().setLayout(new BorderLayout(0, 0));
      this.setSize(950, 600);
      this.setTitle("Email Client");
      this.setLocationRelativeTo(null);
   	
      JPanel inbox = new JPanel();
      getContentPane().add(inbox,BorderLayout.WEST);
      inbox.setLayout(new GridLayout(8, 0, 0, 0));
   
      btnMessage = new JButton("Message");
      jbConnect = new JButton("Connect");
      inbox.add(jbConnect);
      inbox.add(btnMessage);
   	
      JPanel msgView = new JPanel();
      getContentPane().add(msgView,BorderLayout.CENTER);
      msgView.setLayout(new GridLayout(0, 1, 0, 0));
      msgView.setBorder(new EmptyBorder(10, 10, 10, 10));
   	
      JPanel msgViewTop = new JPanel();
      msgView.add(msgViewTop);
      msgViewTop.setLayout(new GridLayout(0, 2, 0, 0));
   	
      JLabel lblNewLabel = new JLabel("To: ");
      lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      msgViewTop.add(lblNewLabel);
   	
      jtfTo = new JTextField(10);
      msgViewTop.add(jtfTo);
      jtfTo.setColumns(10);
   	
      JLabel lblNewLabel_1 = new JLabel("From: ");
      lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
      msgViewTop.add(lblNewLabel_1);
   	
      jtfFrom = new JTextField();
      msgViewTop.add(jtfFrom);
      jtfFrom.setColumns(10);
   	
      JLabel lblNewLabel_2 = new JLabel("Address: ");
      lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
      msgViewTop.add(lblNewLabel_2);
   	
      jtfServer = new JTextField();
      msgViewTop.add(jtfServer);
      jtfServer.setColumns(10);
   	
      JRadioButton rdbtnEncryptMessage = new JRadioButton("Encrypt Message");
      msgViewTop.add(rdbtnEncryptMessage);
   	
      JLabel label = new JLabel("");
      msgViewTop.add(label);
   	
      JLabel lblCompose = new JLabel("Compose: ");
      msgViewTop.add(lblCompose);
   	
      JButton btnSend = new JButton("Send ");
      msgViewTop.add(btnSend);
   	
      JPanel msgViewBot = new JPanel();
      msgView.add(msgViewBot);
      msgViewBot.setLayout(new GridLayout(0, 1, 0, 0));
   	
      JTextArea textArea = new JTextArea();
      msgViewBot.add(textArea);
   	
      this.setVisible(true);
   
      jbConnect.addActionListener(this);
   
   }

   @Override
   public void actionPerformed(ActionEvent ae) {
   
      switch (ae.getActionCommand()){
         case "Connect":
            System.out.println("I got in the connect case of the client");
            host = JOptionPane.showInputDialog(this,"Enter the IP address of the server",null);
            
            try{
               s = new Socket(host,PORT);
               br = new BufferedReader(new InputStreamReader(s.getInputStream()));
               pw = new PrintWriter(new PrintWriter(s.getOutputStream()));
            }
            catch(UnknownHostException uhe){
               System.out.println("Unknown Host Exception caught");
               uhe.printStackTrace();
            }
            catch (IOException ioe){
               System.out.println("IO exception caught");
               ioe.printStackTrace();
            }
            catch (Exception e){
               System.out.println("Some other exception caught!");
               e.printStackTrace();
            }
            LoginFrame lf = new LoginFrame();
            
               new Thread() {
                  public void run(){
                     while (lf.getCurrentUser().equals("")){
                        System.out.print("");
                     }
                  
                     user = lf.getCurrentUser();
                     System.out.println("Client current user: " + user);
                     lf.setVisible(false);
                     pw.println(user);
                     pw.flush();
                  }
               }.start();
            
            
            break;
         case "Message":
            if(s != null){
               MessageHandler(br, pw);
            }     
            break;
      }
   }
   
   public void MessageHandler(BufferedReader br, PrintWriter pw) {
      try{
         String hostName = InetAddress.getLocalHost().getHostName();
         System.out.println("hostName = " + hostName);
         
         send("HELO " + hostName);
         send("MAIL FROM: " + "");
         send("RCPT TO: " + "");
         send("DATA");
         send("test1");
         send("test2");
         send(".");
         send("QUIT");
      }
      catch(IOException e)
      { e.printStackTrace();
      }
   
   }
   
   public void send(String s){
      if (s != null){
         pw.println(s);
         pw.flush();
      }
   }
}
