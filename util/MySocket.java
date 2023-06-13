package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket{
    Socket sc;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    BufferedReader ky_br;
    PrintWriter pw;
    String my_nickname;
    
    
    public MySocket(String nickname){ 
        try{
            this.sc = new Socket("127.0.0.1", 8080);            
            this.ky_br   = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            this.pw = new PrintWriter(sc.getOutputStream(), true);
            this.my_nickname  = nickname;
            write(my_nickname);
    
        }catch(Exception e){
            //e.printStackTrace();
            System.out.println("Error creating MySocket");
        }
    }

    public MySocket(Socket s){
        this.sc = s;
        try{
            this.ky_br   = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            this.pw = new PrintWriter(sc.getOutputStream(), true);
        }catch(Exception e){
            //e.printStackTrace();
        }
    }
        
    
    public String read(){
        try{
            return ky_br.readLine();
        }catch(Exception e){
            //e.printStackTrace();
            return null; 
        }
    }   
    
    public void write(String message){
        pw.println(message);
    }

    public void close(){
        try{
            pw.close();
            ky_br.close();
            sc.close();
        }catch(Exception e){
            //e.printStackTrace();
        }
    }

    public String getNickname(){
        return my_nickname;
    }
    public void setNickname(String nickname){
        this.my_nickname=nickname;
    }
}
