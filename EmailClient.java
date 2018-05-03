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
import java.util.*;

public class EmailClient extends JFrame implements ActionListener,EmailConstants {
   String msg;
   static final String START = "--Begin encrypted message--\n";
   static final String END = "\n--End encrypted message--";
   private String values = "abcdefghijklmnopqrstuvwxyz ";
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
    JRadioButton jrbEncrypt = new JRadioButton("Encrypt Message");


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
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      JPanel inbox = new JPanel();
      getContentPane().add(inbox,BorderLayout.WEST);
      inbox.setLayout(new GridLayout(8, 0, 0, 0));
   
      JButton jbMsg;
      jbMsg = new JButton("Open Inbox");
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
      jbMsg.addActionListener(this);
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
            try{
               String p = br.readLine();
               if(p.equals("Login required")){
                  String name = JOptionPane.showInputDialog("Enter username: ");
               
                  System.out.println("The client received the name " + name + " from the login frame");
                  pw.println(name);
                  pw.flush();
               
                  String ok = br.readLine();
                  System.out.println(ok + " ok");
                  if(ok.equals("Ok")){
                     System.out.println("name valid");
                  }
               
               }
            }catch(Exception e){
            
            }
            break;
      
         case "Send":
            String message = textArea.getText().toLowerCase();
            String encrypt = Decipher(message, 3);
            System.out.println("Send case reached");
            if(jrbEncrypt.isSelected()){
            textArea.setText(START);
            textArea.append(encrypt);
            textArea.append(END);
            }
            try {
             pw.println("Send");
             pw.flush();
             if(br.readLine().substring(0,3).equals("220")){
                  
               pw.println("HELO");
               pw.flush();
               System.out.println("HELO");
               
               if(br.readLine().substring(0,3).equals("250")){
                  pw.println("MAIL FROM:<" +jtfFrom.getText() + ">");
                  pw.flush();
                  System.out.println("MAIL FROM");
                  if(br.readLine().substring(0,3).equals("250")){
                     pw.println("RCPT TO:<" +jtfTo.getText() + ">");
                     pw.flush();
                     System.out.println("RCPT TO");
                     if(br.readLine().substring(0,3).equals("250")){
                        pw.println("DATA");
                        pw.flush();
                        System.out.println("DATA");
                        String three = br.readLine();
                        System.out.println(three);
                           
                        if(three.substring(0,3).equals("354")){
                           System.out.println("got 354");
                           for(String s : textArea.getText().split("\\n")){
                              pw.println(s);
                              pw.flush();
                           }
                           if(br.readLine().substring(0,3).equals("250")){
                              pw.println("QUIT");
                              pw.flush();
                              System.out.println("QUIT");
                              if(br.readLine().substring(0,3).equals("221")){
                                 System.out.println("Victory");
                              }
                              
                           }
                           
                        }
                        
                     }
                  }
                  
               }
               
            }  
            } catch(IOException ioe){
               System.out.println(ioe);
            }
            
            break;
         case "Open Inbox":
            System.out.println("In the 'open' case");
            pw.println("Show mail");
            pw.flush();
            break;
      
      }
   }
   
    public String Decipher(String msg, int shift){
     
     
      char charEnc;
      int valEnc;
      int newEnc;
      String encrypted = "";
      msg = textArea.getText().toLowerCase();
      String [] string = msg.split(" ");
      
      for (int i = 0; i < msg.length(); i++) {
         charEnc = msg.charAt(i);
         if(Character.isLetter(charEnc)){
            valEnc = values.indexOf(charEnc);
         
            newEnc = (shift + valEnc) % values.length();
            encrypted += values.charAt(newEnc);
         }else{
            encrypted += (char)charEnc;
            
         }//end if else
      }//end for
      return encrypted;
   
   
   }//end decipher

}