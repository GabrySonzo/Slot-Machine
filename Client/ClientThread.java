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
    private HashMap<String, String> serverResponse;

    public ClientThread(Client client, ObjectInputStream in, int slot, Socket socket){
        
        this.client = client;
        this.in = in;
        this.slot = slot;
        this.socket = socket;
        serverResponse = new HashMap<String, String>();       

    }

    public void run(){
        
        while(true){

            try{
                synchronized(in){
                    serverResponse = (HashMap<String, String>)in.readObject();
                }
                if(slot != Integer.parseInt(serverResponse.get("slot"))){
                    slot = Integer.parseInt(serverResponse.get("slot"));
                    //if(socket.getLocalPort() != Integer.parseInt(serverResponse.get("winner"))){
                        client.print("\nLa slot ha " + slot + " soldi" + "\n" + "Scr5vi la tua puntata: ");
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
