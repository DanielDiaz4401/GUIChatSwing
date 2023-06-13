package util;

import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {
    ServerSocket ss;
    
    public MyServerSocket(){
        try{
            ss = new ServerSocket(8080);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public MySocket accept(){
        try{
            Socket sc = ss.accept();            
            return new MySocket(sc);                        
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
