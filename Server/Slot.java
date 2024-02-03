package Server;

import java.net.Socket;

public class Slot {
    
    private String[] simbols;
    private int[] values;
    private int cash;

    public Slot(){
        simbols = new String[]{"ciliegia","banana","mela","arancia","uva","diamante","spugna","volpe","tasso","sette"};
        values = new int[]{2,4,5,8,10,20,50,80,100,1000};
        cash = 10;
    }

    public String[] spin(int bet, Socket s){
        String[] response = new String[6];
        response[4] = "true";
        //cash += bet;
        int[] r1 = new int[3];
        int[] r2 = new int[3];
        int[] r3 = new int[3];

        for(int i = 0; i < 3; i++){
            r2[i] = (int)(Math.random() * 9);
        }
        for(int i = 0; i < 3; i++){
            r3[i] = r2[i]+1;
            if(r3[i] > 9){
                r3[i] -= 10;
            }
        }
        for(int i = 0; i < 3; i++){
            r1[i] = r2[i]-1;
            if(r1[i] < 0){
                r1[i] += 10;
            }
        }
    
        if(r2[0] == r2[1] && r2[1] == r2[2]){
            bet *= values[r2[0]];
            response[1] = "Hai fatto una tripletta";
            response[2] = "x" + values[r2[0]]/2;
        }
        else if(r2[0] == r2[1] || r2[1] == r2[2]){
            bet *= values[r2[1]]/2;
            response[1] = "Hai fatto una doppia";
            response[2] = "x" + values[r2[1]]/2;
        }
        else if(r2[0] == r2[2]){
            bet *= values[r2[0]]/2;
            response[1] = "Hai fatto una doppia";
            response[2] = "x" + values[r2[0]]/2;
        }
        else{
            bet = 0;
            response[1] = "Hai perso";
            response[2] = "x0";
        }
        cash -= bet;
        if(cash <= 0){
            System.out.println("La slot ha finito i soldi, ha vinto l'utente" + s.getPort());
            response[4] = "perso";
            response[5] = s.getPort() + "";
        }

        response[0] = bet + "";
        response[3] = cash + "";
        return response;
    }

    public int getCash(){
        return cash;
    }

}
