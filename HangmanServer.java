package HangmanProject;
import java.net.*;
import java.io.*;

public class HangmanServer {
    public static void main(String args[]) {
        try {
            ServerSocket ss = new ServerSocket(9086);
            Socket soc = ss.accept();
            BufferedReader userIn = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            System.out.println("Game server");
            String userCred = userIn.readLine();
            PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
            if (isValidUser(userCred.trim())) {
                out.println("Welcome "+userCred.split(" ")[0].toUpperCase()+"!. Game starts.");
                Hangman.start(userIn, out);
                userIn.close();
                out.close();
                soc.close();
            }
            else{
                String msg = "Invalid Credentials. Connection terminated.";
                out.println(msg);
                userIn.close();
                soc.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static boolean isValidUser(String userDetails) throws Exception{
        BufferedReader fileObj = new BufferedReader(new InputStreamReader(new FileInputStream("src\\HangmanProject\\users")));
        String data;
        while((data = fileObj.readLine()) != null) {
            if (userDetails.equals(data.trim())) {
                fileObj.close();
                return true;
            }
        }
        fileObj.close();
        return false;
    }
}
