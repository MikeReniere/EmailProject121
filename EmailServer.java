import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;


public class EmailServer extends JFrame implements ActionListener,EmailConstants {

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
   EmailServer(){
      this.setSize(400,200);
      this.setLocation(200,400);
      this.setTitle("Email Server");
      this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
   
      JPanel jpNorth = new JPanel(new FlowLayout());
      jpNorth.add(jbStart);
      jbStart.setAlignmentX(Component.CENTER_ALIGNMENT);
   
      this.add(jpNorth,BorderLayout.NORTH);
      this.setVisible(true);
   
      jbStart.addActionListener(this);
      System.out.println("ActionLister added to jbStart");
   }//end constructor

   @Override
    public void actionPerformed(ActionEvent ae) {
   
      switch (ae.getActionCommand()) {
         case "Start Server":
            System.out.println("I got to the start case of the start button server");
            jbStart.setText("Stop Server");
            Thread t = new Thread(
               ()-> {
                  try{
                     ServerSocket ss = null;
                     System.out.println("Created server socket");
                     ss = new ServerSocket(PORT);
                  
                     Socket soc = null;
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
   
      Socket client = null;
   
   
      public ThreadedServer(Socket inSoc){
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
   
      public void run(){
         try {
            System.out.println("Connected to " + client.getInetAddress().getHostAddress());
            pw.println("Login required");
            pw.flush();
            String login = br.readLine();
            System.out.println("Current user: " + login);
         } catch(IOException ioe) {
            System.out.println(ioe);
         }
      }
   }
}
//login
//send request
//client requests message download
//send messages from server to server