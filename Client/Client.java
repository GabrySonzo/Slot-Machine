package Client;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Client { 

    private Socket socket; 
    private DataOutputStream out; 
    private ObjectInputStream in;
    private BufferedReader stdIn;
    private int cash;
    private int slot;
    private HashMap<String, Object> serverResponse;
    private ClientThread clientThread;

    public Client(){
        
        try{
            socket = new Socket("localhost", 7777); 
            out = new DataOutputStream(socket.getOutputStream()); 
            in = new ObjectInputStream(socket.getInputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            serverResponse = new HashMap<String, Object>();
            cash = 1000;
            synchronized(in){
                serverResponse = (HashMap<String, Object>)in.readObject();
            }
            slot = Integer.parseInt((String)serverResponse.get("slot"));
            clientThread = new ClientThread(this, in, slot, socket);
            clientThread.start();
        }
        catch (Exception e) {
            System.out.println("IOException: " + e);
        }
    
    }
    public void start()throws IOException, ClassNotFoundException{ 

        System.out.print("Per disconnettersi dal Server scrivere: QUIT\n"); 

        while (true)
        {
            if(cash == 0){
                System.out.println("Hai finito i soldi");
                break;
            }
            System.out.println("Hai " + cash + " soldi");
            System.out.println("La slot ha " + slot + " soldi");
            System.out.print("Scrivi la tua puntata: ");
            String userInput = stdIn.readLine();
            if (userInput.equals("QUIT")){
                break; 
            }else if((!userInput.matches("[0-9]+") || userInput.equals(""))){
                System.out.println("Inserire un numero");
            }else if(Integer.parseInt(userInput) > cash){
                System.out.println("Non hai abbastanza soldi");
            }
            else{
                cash -= Integer.parseInt(userInput);
                out.writeBytes(userInput + '\n');

                synchronized(in){
                    serverResponse = (HashMap<String, Object>)in.readObject();
                }
                if(serverResponse.get("win").equals("true")){
                    System.out.println("La slot ha finito i soldi, ha vinto l'utente " + serverResponse.get("winner"));
                    break;
                }
                String[][] results = (String[][])serverResponse.get("results");
                for(int i=0; i<3; i++){
                    System.out.println(results[i][0] + " " + results[i][1] + " " + results[i][2]);
                }
                System.out.println(serverResponse.get("message") + "(" + serverResponse.get("multiplier") + ")\n");
                cash += Integer.parseInt((String)serverResponse.get("bet"));
                slot = Integer.parseInt((String)serverResponse.get("slot"));
            }
        } 

        out.writeBytes("QUIT\n");
        out.close(); 
        in.close(); 
        
        socket.close();
        
    } 

    public void print(String message){
        System.out.println(message);
    }

    public static void main (String[] args) throws Exception { 
        Client tcpClient = new Client(); 
        tcpClient.start(); 
    } 
} 