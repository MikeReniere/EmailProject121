import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;


public class EmailServer extends JFrame implements ActionListener,EmailConstants {

    //Mailboxes
    private ArrayList<Mailbox> boxes = new ArrayList<>();
    private Mailbox alex = new Mailbox("alex");
    private Mailbox alex2 = new Mailbox("alex2");
    private Mailbox alex3 = new Mailbox("alex3");
    private Mailbox alex4 = new Mailbox("alex4");
    private Mailbox guest = new Mailbox("guest");


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
                Thread t = new Thread(()-> {
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
            try {
                String name = br.readLine();
                System.out.println("The name received from the client is " + name);
                Account act = new Account();
                String confirmed = act.LogInUser(name);

                System.out.println("The name confirmed is " + confirmed);
                System.out.println("if the name of the mailbox is " + alex.getName());

                if(confirmed.equals(alex.getName())){
                    System.out.println("I am in the if 'confirmed == alex.getName()' ");

                    Message m = new Message();
                    String from = br.readLine();
                    m.setFrom(from);
                    System.out.println("From: " + m.getFrom());

                    String to = br.readLine();
                    m.setTo(to);
                    System.out.println("To: " + m.getTo());

                    String message = br.readLine();
                    m.setMessage(message);
                    System.out.println("Message: " + m.getMessage());

                    File f = new File("C:\\Users\\alexl\\Desktop\\Java 2\\Final Project V2\\src\\alex.txt");
                    BufferedWriter bwFile = new BufferedWriter(new FileWriter(f,true));

                    bwFile.write("From: " + m.getFrom());
                    bwFile.flush();
                    bwFile.newLine();

                    bwFile.write("To: " + m.getTo());
                    bwFile.flush();
                    bwFile.newLine();

                    bwFile.write("Message\n: " + m.getMessage());
                    bwFile.flush();
                    bwFile.newLine();


                    alex.add(m);

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

                }
                else if(confirmed.equals(alex2.getName())){
                    System.out.println("I am in the if 'confirmed == alex2.getName()' ");

                    Message m = new Message();
                    String from = br.readLine();
                    m.setFrom(from);
                    System.out.println("From: " + m.getFrom());

                    String to = br.readLine();
                    m.setTo(to);
                    System.out.println("To: " + m.getTo());

                    String message = br.readLine();
                    m.setMessage(message);
                    System.out.println("Message: " + m.getMessage());

                    File f = new File("C:\\Users\\alexl\\Desktop\\Java 2\\Final Project V2\\src\\alex2.txt");
                    BufferedWriter bwFile = new BufferedWriter(new FileWriter(f,true));

                    alex.add(m);

                    for(Mailbox mb: boxes){
                        System.out.println("Name of the mailbox "+ mb.getName()+"\n");
                        for( Message ms : mb ){
                            if(ms != null)
                                System.out.println("Message inside mailbox" + ms.getMessage());
                        }
                    }

                    String cmd = br.readLine();
                }
                else if(confirmed.equals(alex3.getName())){
                    System.out.println("I am in the if 'confirmed == alex3.getName()' ");

                    Message m = new Message();
                    String from = br.readLine();
                    System.out.println("From: " + from);
                    m.setFrom(from);

                    String to = br.readLine();
                    System.out.println("To: " + to);
                    m.setTo(to);

                    String message = br.readLine();
                    System.out.println("Message: " + message);
                    m.setMessage(message);

                    File f = new File("C:\\Users\\alexl\\Desktop\\Java 2\\Final Project V2\\src\\alex3.txt");
                    BufferedWriter bwFile = new BufferedWriter(new FileWriter(f,true));

                    alex.add(m);

                    for(Mailbox mb: boxes){
                        System.out.println("Name of the mailbox "+ mb.getName()+"\n");
                        for( Message ms : mb ){
                            if(ms != null)
                                System.out.println("Message inside mailbox" + ms.getMessage());
                        }

                    }

                }
                else if(confirmed.equals(alex4.getName())){
                    System.out.println("I am in the if 'confirmed == alex4.getName()' ");

                    Message m = new Message();
                    String from = br.readLine();
                    System.out.println("From: " + from);
                    m.setFrom(from);

                    String to = br.readLine();
                    System.out.println("To: " + to);
                    m.setTo(to);

                    String message = br.readLine();
                    System.out.println("Message: " + message);
                    m.setMessage(message);

                    File f = new File("C:\\Users\\alexl\\Desktop\\Java 2\\Final Project V2\\src\\alex4.txt");
                    BufferedWriter bwFile = new BufferedWriter(new FileWriter(f,true));

                    alex.add(m);

                    for(Mailbox mb: boxes){
                        System.out.println("Name of the mailbox "+ mb.getName()+"\n");
                        for( Message ms : mb ){
                            if(ms != null)
                                System.out.println("Message inside mailbox" + ms.getMessage());
                        }

                    }

                }
                else if(confirmed.equals(guest.getName())){
                    System.out.println("I am in the if 'confirmed == guest.getName()' ");

                    Message m = new Message();
                    String from = br.readLine();
                    System.out.println("From: " + from);
                    m.setFrom(from);

                    String to = br.readLine();
                    System.out.println("To: " + to);
                    m.setTo(to);

                    String message = br.readLine();
                    System.out.println("Message: " + message);
                    m.setMessage(message);

                    File f = new File("C:\\Users\\alexl\\Desktop\\Java 2\\Final Project V2\\src\\guest.txt");
                    BufferedWriter bwFile = new BufferedWriter(new FileWriter(f,true));

                    alex.add(m);

                    for(Mailbox mb: boxes){
                        System.out.println("Name of the mailbox "+ mb.getName()+"\n");
                        for( Message ms : mb ){
                            if(ms != null)
                                System.out.println("Message inside mailbox" + ms.getMessage());
                        }

                    }
                }
                else{
                        JOptionPane.showMessageDialog(jbStart,"Something went wrong!");
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
}
//login
//send request
//client requests message download
//send messages from server to server 