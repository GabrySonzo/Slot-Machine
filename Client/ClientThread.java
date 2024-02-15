package Client;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;

public class ClientThread extends Thread{
    
    private Client client;
    private ObjectInputStream in;
    private DataOutputStream out;
    private int slot;
    private String[] serverResponse;

    public ClientThread(Client client, ObjectInputStream in, DataOutputStream out, int slot){
        
        this.client = client;
        this.in = in;
        this.out = out;
        this.slot = slot;        

    }

    public void run(){
        
        while(true){
            try{
                synchronized(in){
                    serverResponse = (String[])in.readObject();
                }
                if(slot != Integer.parseInt(serverResponse[3])){
                    slot = Integer.parseInt(serverResponse[3]);
                    client.print("La slot ha " + slot + " soldi" + "\n" + "Scrivi la tua puntata: ");
                }
            }
            catch (Exception e) {
                System.out.println("IOException: " + e);
            }
        }

    }


}
