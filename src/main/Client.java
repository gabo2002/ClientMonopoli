package main;

import connessione.Connessione;
import letturaXml.LeggiXml;
import monopoli.Evento;
import monopoli.Giocatore;
import monopoli.Terreno;
import utilities.Log;

import java.util.ArrayList;

public class Client {

    private Log log;
    private final String nomeFileImprevisti = "imprevisti.xml";
    private final String nomeFileProbabilita = "probabilita.xml";
    private final String nomeFileTerreni = "terreni.xml";
    private LeggiXml lettura;
    private Evento[] imprevisti;
    private Evento[] probabilita;
    private Terreno[] terreni;
    private Giocatore giocatore;
    private Connessione connessione;
    private ArrayList<Giocatore> giocatori;
    

    public Client(String nome,String ip){
        log = new Log();
        lettura = new LeggiXml();
        imprevisti = lettura.getEventi(nomeFileImprevisti,"imprevisto");
        probabilita = lettura.getEventi(nomeFileProbabilita,"probabilita");
        terreni = lettura.getTerreni(nomeFileTerreni);
        giocatore = new Giocatore(nome);
        giocatori = new ArrayList<Giocatore>();
        connessione = new Connessione(ip);
        log.append("Inizializzazione client completata");
    }

    public Connessione getConnessione(){
        return this.connessione;
    }

    public Giocatore getGiocatore(){
        return this.giocatore;
    }

    public Terreno[] getTerreni(){
        return terreni;
    }




}
