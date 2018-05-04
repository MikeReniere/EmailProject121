import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;


public class EmailServer extends JFrame implements ActionListener,EmailConstants {

   //Mailboxes
   String sendTo = "";
   private ArrayList<Mailbox> boxes = new ArrayList<>();
   private Mailbox alex = new Mailbox("alex");
   private Mailbox alex2 = new Mailbox("dan");
   private Mailbox alex3 = new Mailbox("mike");
   private Mailbox alex4 = new Mailbox("hamis");
   private Mailbox guest = new Mailbox("guest");
   final String dir = System.getProperty("user.dir");


   //GUI components
   private JButton jbStart = new JButton("Start Server");
   private BufferedReader br = null;
   private PrintWriter pw = null;

   public static void main(String[] args){
      new EmailServer();
   }//end main

   /**
    * Email Server constructor
    */
   private EmailServer(){
      this.setSize(400,200);
      this.setLocation(200,400);
      this.setTitle("Email Server");
      this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
   
      JPanel jpNorth = new JPanel(new FlowLayout());
      jpNorth.add(jbStart);
      jbStart.setAlignmentX(Component.CENTER_ALIGNMENT);
   
      this.add(jpNorth,BorderLayout.NORTH);
      this.setVisible(true);
   
   
      jbStart.addActionListener(this);
      System.out.println("ActionLister added to jbStart");
      boxes.add(alex);
      boxes.add(alex2);
      boxes.add(alex3);
      boxes.add(alex4);
      boxes.add(guest);
   }//end constructor

   /**
    *@param ae takes the actionPerformed from the various buttons
    */
   @Override
   public void actionPerformed(ActionEvent ae) {
   
      switch (ae.getActionCommand()) {
         case "Start Server":
            System.out.println("I got to the start case of the start button server");
            jbStart.setText("Stop Server");
            Thread t = new Thread(
               ()-> {
                  try{
                     ServerSocket ss; //= null
                     System.out.println("Created server socket");
                     ss = new ServerSocket(PORT);
                  
                     Socket soc; // = null
                     while (true){
                        System.out.println("Entered infinite while; waiting for connections");
                        soc = ss.accept();
                        System.out.println("Connected to: " + soc.getInetAddress().getHostAddress());
                     
                     //Thread created
                        ThreadedServer ths = new ThreadedServer(soc);
                        System.out.println("Threaded Server created");
                        ths.start();
                        System.out.println("Threaded server started");
                     }
                  }
                  catch (IOException ioe){
                     System.out.println("IOException caught when creating a new server thread");
                     ioe.printStackTrace();
                  }
               });
         
            t.start();
      }
   }

   class ThreadedServer extends Thread {
   
      Socket client; //make null
   
      /**
       * @param inSoc takes socket to which clients can connect
       */
      ThreadedServer(Socket inSoc){
         this.client = inSoc;
         try{
            System.out.println("I am in the constructor of the Threaded Server");
            br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
         }
         catch (IOException ioe){
            System.out.println("IOException caught while creating br and pw");
            ioe.printStackTrace();
         }
      }//end ThreadedServer constructor
   
      /**
       * Run method of the ThreadedServer class
       */
      public void run(){
         System.out.println("Connected to " + client.getInetAddress().getHostAddress());
         String confirmed ="";
         try {
            pw.println("Login required");
            pw.flush();
            String name = br.readLine();
            System.out.println("The name received from the client is " + name);
            Account act = new Account();
            if(act.LogInUser(name)){
               System.out.println("User " + name + " logged in.");
               confirmed = name;
               pw.println("Ok");
               pw.flush();
               
               
               System.out.println("The name confirmed is " + confirmed);
               
            
               File f = new File(dir + File.separator +  confirmed + ".txt");
               System.out.println(f);
                  
               String cmd = br.readLine();
               if (cmd.equals("Send")){
                  if(confirmed.equals(alex.getName())){
                     System.out.println("I am in the if 'confirmed == alex.getName()' ");
                     smtp(confirmed, f);
                  
                  }
                  else if(confirmed.equals(alex2.getName())){
                     System.out.println("I am in the if 'confirmed == alex2.getName()' ");
                     smtp(confirmed, f);
                  }
                  else if(confirmed.equals(alex3.getName())){
                     System.out.println("I am in the if 'confirmed == alex3.getName()' ");
                     smtp(confirmed, f);
                  
                  }
                  else if(confirmed.equals(alex4.getName())){
                     System.out.println("I am in the if 'confirmed == alex4.getName()' ");
                     smtp(confirmed, f);     
                  }
                  else if(confirmed.equals(guest.getName())){
                     System.out.println("I am in the if 'confirmed == guest.getName()' ");
                     smtp(confirmed, f);
                  }
               }
               //SHOW INBOX
               if (cmd.equals("Show mail")){
                  System.out.println(f.getPath());
                  Desktop.getDesktop().open(f);
               }
               else{
                  JOptionPane.showMessageDialog(jbStart,"Oops, something went wrong. ");
               
               }
            }
            else{
               JOptionPane.showMessageDialog(jbStart,"Guests login with \"guest\"");
               
            }
         
         } catch (IOException ioe) {
            ioe.printStackTrace();
         }
         try{
            br.close();
            pw.close();
         }
         catch (IOException ioe){
            ioe.printStackTrace();
         }
      
      
      }
   }
   
   public void smtp(String confirmed, File f) {
      String newAddress="";
      String newTo ="";
      String newFrom = "";
      String newMsg = ""; 
      try {
         Message m = new Message();
         pw.println("220");
         pw.flush();
         if(br.readLine().substring(0,4).equals("HELO")){
            System.out.println("got to HELO");
            pw.println("250");
            pw.flush();
            String from = br.readLine();
            if(from.substring(0,9).equals("MAIL FROM")){
               m.setFrom(from.substring(from.indexOf('<'), from.length() -1));
               newFrom = (from.substring(from.indexOf('<'), from.length() -1));
               pw.println("250");
               pw.flush();
               String to = br.readLine();
               if(to.substring(0,7).equals("RCPT TO")){
                  m.setTo(to.substring(to.indexOf('<'), to.length() -1));
                  if(to.indexOf('@') != -1){
                     newAddress = to.substring(to.indexOf('@')); 
                     newTo = to.substring(0, to.indexOf('@'));
                  }
                     
                  pw.println("250");
                  pw.flush();
                  System.out.println("sent here");
                  if(br.readLine().substring(0,4).equals("DATA")){
                     pw.println("354");
                     pw.flush();
                     ArrayList<String> msg = new ArrayList<String>();
                     boolean loop = true;
                     while(loop){
                        String line = br.readLine();
                        if(!line.equals("."))
                           msg.add(line);
                        else {
                           loop = false;
                           String message = "";
                           for(String s : msg){
                              message+=s;
                           }
                           m.setMessage(message);
                           System.out.println("Message: " + m.getMessage());
                           pw.println("250");
                           pw.flush();
                           if(br.readLine().substring(0,4).equals("QUIT")){
                              pw.println("221");
                              pw.flush();
                           }
                        }
                     }
                                                  
                  }
                     
               }
            }
               
         }
      
         
            
         BufferedWriter bwFile = new BufferedWriter(new FileWriter(f,true));
            
         bwFile.write("From:\n" + m.getFrom() + "\n");
         bwFile.flush();
         bwFile.newLine();
            
         bwFile.write("To:\n" + m.getTo()+ "\n");
         bwFile.flush();
         bwFile.newLine();
         sendTo = m.getTo();   
         bwFile.write("Message:\n" + m.getMessage()+ "\n\n");
         bwFile.flush();
         bwFile.newLine();
            
         // alex.add(m);
            
         for(Mailbox mb: boxes){
            if(mb.getName().equals(sendTo)){
               mb.add(m);
            }
         }   
         for(Mailbox mb: boxes){
            System.out.println("Name of the mailbox "+ mb.getName()+"\n");
            for( Message ms : mb ){
               if(ms != null) {
                  System.out.println("Message inside mailbox" + ms.getMessage());
                  System.out.println("From: " + ms.getFrom());
                  System.out.println("Sent to: " + ms.getTo());
               }
            }
               
         }
      } catch(IOException ioe){
         System.out.println(ioe);
      }
      
      //BEGIN FORWARDING
      if(!newAddress.equals("")){
         try{
            Socket s = new Socket(newAddress,PORT);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(new PrintWriter(s.getOutputStream()));
               
            String p = br.readLine();
            if(p.equals("Login required")){
               pw.println("guest");
               pw.flush();
               
               String ok = br.readLine();
               System.out.println(ok + " ok");
               if(ok.equals("Ok")){
                  System.out.println("name valid");
               }
               
            }
         
               
            try {
               pw.println("Send");
               pw.flush();
               if(br.readLine().substring(0,3).equals("220")){
                  
                  pw.println("HELO");
                  pw.flush();
                  System.out.println("HELO");
               
                  if(br.readLine().substring(0,3).equals("250")){
                     pw.println("MAIL FROM:<" + newFrom + ">");
                     pw.flush();
                     System.out.println("MAIL FROM");
                     if(br.readLine().substring(0,3).equals("250")){
                        pw.println("RCPT TO:<" + newTo + ">");
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
                              for(String st : newMsg.split("\\n")){
                                 pw.println(st);
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
           
      
      } //end forwarding
   }
}
//login
//send request
//client requests message download
//send messages from server to server