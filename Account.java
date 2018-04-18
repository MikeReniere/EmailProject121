import java.io.*;

public class Account {

   String name = "";
   String password = "";
   String path = System.getProperty("user.dir") + File.separator + "Users.txt";

   BufferedReader userBr = null;
   BufferedWriter userBw = null;

   public boolean LogInUser(String chkName) {
      try {
         userBr = new BufferedReader(new FileReader(path));
      } catch (FileNotFoundException fnfe) {
         System.out.println("File not Found Exception");
      }
      name = chkName;
   
      boolean userFound = false;
   
      String u = "";
      try {
         while ((u = userBr.readLine()) != null) {
            System.out.println("The buffered Reader read " + u);
            if (u.equals(name)) {
               userFound = true;
            }//end if
         }//end while
      }catch (IOException ioe) {
         System.out.println();
      }
      return userFound;
   }

   public void MakeAccount(String newName) {
      if(!LogInUser(newName)){
         String fileName = newName;
         System.out.println(fileName);
         try {
            File f = new File(System.getProperty("user.dir") + File.separator + fileName + ".txt");
            BufferedWriter filebw = new BufferedWriter(new FileWriter(f));
            String s = "";
            filebw.write("");
            filebw.flush();
            filebw.close();
         
            System.out.println("The name of the file created is " + f.getName());
            System.out.println("The path of " + f.getName() + " " + f.getAbsolutePath());
            userBr = new BufferedReader(new FileReader(path));
         
            userBw = new BufferedWriter(new FileWriter(path, true));
         } catch (FileNotFoundException fnfe) {
            System.out.println("FileNotFoundException");
            fnfe.printStackTrace();
         } catch (IOException ioe) {
            System.out.println("IO Exception caught");
            ioe.printStackTrace();
         }
      
         String use = fileName;
         try {
            userBw.write(use);
            userBw.flush();
            userBw.newLine();
            userBw.flush();
         
         
         } catch (IOException ioe) {
            System.out.println("IO Exception caught ");
            ioe.printStackTrace();
         }
      }
   }

   public String setDefaultName() {
      return "user";
   }

}