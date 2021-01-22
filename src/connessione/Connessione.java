package connessione;

import utilities.ErrorCode;
import utilities.Log;
import utilities.StatusCode;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connessione {
    private boolean connected = false;
    private PrintWriter writer;
    private BufferedReader reader;
    private final int destinationPort = 50000;
    private Socket socket;
    private Log log;

    public Connessione(String ip){
        log = new Log();
        try{
            socket = new Socket(ip,destinationPort);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = true;
        } catch (UnknownHostException e) {
            System.err.println("Server selezionato inesistente");
            log.append("Errore! Indirizzo ip del server non valido!");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("INPUT OUTPUT ERROR!");
            log.append("Errore nella comunicazione con il server " + ip );
        }
    }

    public void send(String msg){
        writer.println(msg);
    }

    public String recv() {
        try {
            return reader.readLine();
        }
        catch(IOException e ){
            System.err.println("Errore nella ricezione dal server");
            log.append("Errore nella ricezaione dal server");
            e.printStackTrace();
        }
        return null;
    }

    public boolean isConnected(){
        return connected;
    }

    public int getPorta(){
        return destinationPort;
    }

    public int handShake(String nome){
        String messaggio = recv();

        if(messaggio.equals("" + StatusCode.STATUS_READY)){
            send(nome);
            send("" + StatusCode.STATUS_READY);
            log.append("Handshake completato con il server: "+ socket.getRemoteSocketAddress().toString());
        }
        else if(messaggio.equals("" + ErrorCode.ERROR_MAX_CLIENT)){
            log.append("Errore! Il server e' pieno");
            return 0;
        }
        else{
            log.append("Errore! il protocollo di comunicazione e' diverso");
            return -1;
        }
        return 1;
    }

}
