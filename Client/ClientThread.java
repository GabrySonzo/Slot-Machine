package Client;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientThread extends Thread{
    
    private Client client;
    private ObjectInputStream in;
    private Socket socket;
    private int slot;
    private HashMap<String, Object> serverResponse;

    public ClientThread(Client client, ObjectInputStream in, int slot, Socket socket){
        
        this.client = client;
        this.in = in;
        this.slot = slot;
        this.socket = socket;
        serverResponse = new HashMap<String, Object>();       

    }

    public void run(){
        
        while(true){

            try{
                synchronized(in){
                    serverResponse = (HashMap<String, Object>)in.readObject();
                }
                if(slot != Integer.parseInt((String)serverResponse.get("slot"))){
                    slot = Integer.parseInt((String)serverResponse.get("slot"));
                    //if(socket.getLocalPort() != Integer.parseInt(serverResponse.get("winner"))){
                        client.print("\nLa slot ha " + slot + " soldi" + "\n" + "Scrivi la tua puntata: ");
                    //}
                }
            }
            catch (Exception e) {
                break;
            }
        }
        this.interrupt();
    }

}
