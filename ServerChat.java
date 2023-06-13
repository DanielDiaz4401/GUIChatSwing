import util.ConnectionTable;
import util.MyServerSocket;
import util.MySocket;

public class ServerChat {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    
    public static void main(String[] args){
        MyServerSocket ss = new MyServerSocket();
        ConnectionTable table = new ConnectionTable();
        System.out.println("Esperant clients...");

        while(true){
            MySocket sc = ss.accept();

            Thread handlerThread = new Thread(() -> {
                String nom_usuari=sc.read();                
                String usuari = table.addParticipant(nom_usuari, sc);
                System.out.println(ANSI_GREEN + usuari + " ha entrat" + ANSI_RESET);
                String content;
                while((content=sc.read())!=null){
                    table.broadcast(content, usuari);
                    System.out.println(nom_usuari +": "+ content);             
                }
                System.out.println(ANSI_RED + usuari + " ha marxat" + ANSI_RESET);
                table.removeParticipant(usuari);
                sc.close();
            });
            handlerThread.start();

        }
    }
}
