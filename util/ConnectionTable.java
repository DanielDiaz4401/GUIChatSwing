package util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionTable {
    Map<String, MySocket> clients_map;
    Lock l;

    public ConnectionTable() {
        clients_map = new HashMap<>();
        l = new ReentrantLock();
    }

    public String addParticipant(String nickname_original, MySocket sc) {
        String nickname = null;
        l.lock();
        try { 
            //Si el nom d'usuari ja existeix, hi afegeix 3 números ranodm al final
            if(clients_map.containsKey(nickname_original)){
                do {
                    nickname = nickname_original + "_"
                            + (int) (Math.random() * 10)
                            + (int) (Math.random() * 10)
                            + (int) (Math.random() * 10);
                } while (clients_map.containsKey(nickname));            
            }
            else{
                nickname = nickname_original;
            }  
            clients_map.put(nickname, sc);                      
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            l.unlock();
        }
        return nickname;
    }

    public void broadcast(String content, String nick) { //
        l.lock();
        try {
            for (String nickname : clients_map.keySet()) {
                if (!nickname.equals(nick)) {
                    MySocket sc = clients_map.get(nickname);
                    sc.write(nick + ": " + content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }

    public void removeParticipant(String meu_nickname) {
        l.lock();
        try {
            clients_map.remove(meu_nickname);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }
}
