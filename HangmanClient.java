package HangmanProject;
import java.net.*;
import java.io.*;

public class HangmanClient {
    public static void main(String args[]){
        try{
            Socket soc = new Socket("localhost", 9086);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Client side");
            String username, password;
            System.out.println("Enter the username");
            username = userInput.readLine();
            System.out.println("Enter the password");
            password = userInput.readLine();
            PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
            out.println(username+" "+password);
            BufferedReader response = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            while(true){
                String data = response.readLine();
                System.out.println(data);
                if(data.contains("Final Score"))
                    break;
                if(data.contains("Enter a character"))
                    out.println(userInput.readLine());
            }
            userInput.close();
            out.close();
            soc.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
