import java.io.*;

public class Account {

    String name = "";
    String password = "";
    String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "Users.txt";

    BufferedReader userBr = null;
    BufferedWriter userBw = null;

    public String LogInUser(String chkName) {
        try {
            System.out.println("Path being opened " + path);
            userBr = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not Found Exception");
        }
        name = chkName;

        boolean userFound = false;

        String u = "";
        while (u != null) {
            try {
                u = userBr.readLine();
                System.out.println("The buffered Reader read " + u);
            } catch (IOException ioe) {
                System.out.println();
            }
            if ( (u != null) && u.equals(name)) {
                userFound = true;
                break;
            }//end if
        }//end while
        return name;
    }

    public void MakeAccount(String newName) {
        String fileName = newName;
        System.out.println(fileName);
        try {
            File f = new File(System.getProperty("user.dir") + fileName + ".txt");
            System.out.println("The path is "+f.getAbsoluteFile());
            BufferedWriter filebw = new BufferedWriter(new FileWriter(f));
            //BufferedReader filebr = new BufferedReader(new FileReader(f));
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

    public String setDefaultName() {
        return "user";
    }

}