package in.derros.pi.cmd; 

 
import java.io.*;
import java.nio.*;

public class ExecuteCmd {

public ExecuteCmd(String command) {
            try {
                Runtime rt = Runtime.getRuntime();
                //Process pr = rt.exec("cmd /c dir");
                Process pr = rt.exec(command);
 
                BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
 
                String line = input.readLine();
               
                return line;
            } catch(Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }
}

}
