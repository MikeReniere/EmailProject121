
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
import java.sql.SQLOutput;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EmailClient extends JFrame implements ActionListener,EmailConstants {

    //Server-Client variables
   private BufferedReader br;
   private PrintWriter pw;
   private Socket s = null;
    //Standard variables
   private String host;
   //Declaration of JFrame variables
   private JTextField jtfTo;
   private JTextField jtfFrom;
   private JTextArea textArea = new JTextArea();

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

      JButton jbMsg;
      jbMsg = new JButton("Message");
      JButton jbConnect;
      jbConnect = new JButton("Connect");
      inbox.add(jbConnect);
      inbox.add(jbMsg);
   	
      JPanel msgView = new JPanel();
      getContentPane().add(msgView,BorderLayout.CENTER);
      msgView.setLayout(new GridLayout(0, 1, 0, 0));
      msgView.setBorder(new EmptyBorder(10, 10, 10, 10));
   	
      JPanel msgViewTop = new JPanel();
      msgView.add(msgViewTop);
      msgViewTop.setLayout(new GridLayout(0, 2, 0, 0));
   	
      JLabel jlTo = new JLabel("To: ");
      jlTo.setHorizontalAlignment(SwingConstants.RIGHT);
      msgViewTop.add(jlTo);
   	
      jtfTo = new JTextField(10);
      msgViewTop.add(jtfTo);
      jtfTo.setColumns(10);
   	
      JLabel jlFrom = new JLabel("From: ");
      jlFrom.setHorizontalAlignment(SwingConstants.RIGHT);
      msgViewTop.add(jlFrom);
   	
      jtfFrom = new JTextField();
      msgViewTop.add(jtfFrom);
      jtfFrom.setColumns(10);
   	
      JLabel jlAddress = new JLabel("Address: ");
      jlAddress.setHorizontalAlignment(SwingConstants.RIGHT);
      msgViewTop.add(jlAddress);

       JTextField jtfServer = new JTextField();
      msgViewTop.add(jtfServer);
      jtfServer.setColumns(10);
   	
      JRadioButton jrbEncrypt = new JRadioButton("Encrypt Message");
      msgViewTop.add(jrbEncrypt);
   	
      JLabel label = new JLabel("");
      msgViewTop.add(label);
   	
      JLabel jlCompose = new JLabel("Compose: ");
      msgViewTop.add(jlCompose);
   	
      JButton jbSend = new JButton("Send");
      msgViewTop.add(jbSend);
   	
      JPanel msgViewBot = new JPanel();
      msgView.add(msgViewBot);
      msgViewBot.setLayout(new GridLayout(0, 1, 0, 0));

      msgViewBot.add(textArea);
   	
      this.setVisible(true);
   
      jbConnect.addActionListener(this);
      jbSend.addActionListener(this);
   
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


            String name = JOptionPane.showInputDialog("Enter username: ");

            System.out.println("The client received the name " + name + " from the login frame");
            pw.println(name);
            pw.flush();
            break;

         case "Send":
             System.out.println("Send case reached");
             pw.println(jtfFrom.getText());
             pw.flush();
             System.out.println(jtfFrom.getText());

             pw.println(jtfTo.getText());
             pw.flush();
             System.out.println(jtfTo.getText());

             pw.println(textArea.getText());
             pw.flush();
             System.out.println(textArea.getText());

            break;
          case "Messages":
              System.out.println("In the 'messages' case");
              pw.println("Show mail");
              break;

      }
   }
}
