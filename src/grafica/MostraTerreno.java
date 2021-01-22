package grafica;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import monopoli.Terreno;


public class MostraTerreno {

    private String nomeTerritorio;
    private Image immagine;
    private Terreno terrenoXml;

    @FXML
    private Label nome,colore,valore,singola,albergo,terreno,prezzoCasa,prezzoAlbergo;

    @FXML
    private ImageView view;

    public MostraTerreno(){}

    @FXML
    public void initialize(){

        Platform.runLater( () -> {

            view.setImage(immagine);
            view.setFitHeight(288);
            view.setFitWidth(162);
            nome.setText("Nome terreno: " + nomeTerritorio);
            colore.setText("Colore terreno: "+ terrenoXml.getColore());
            valore.setText("Valore territorio: "+terrenoXml.getValore());
            singola.setText("Costo costruzione per ogni casa: "+terrenoXml.getCostoCostruzioneCasa());
            albergo.setText("Costo costruzione per albergo: "+terrenoXml.getCostoCostruzioneAlbergo());
            terreno.setText("Pagamento solo terreno: "+terrenoXml.getPrezzoTerreno());
            prezzoCasa.setText("Pagamento per ogni casa: "+terrenoXml.getPrezzoACasa());
            prezzoAlbergo.setText("Pagamento per albergo: "+terrenoXml.getPrezzoAlbergo());
        });

    }

    public void setNomeTerritorio(String nomeTerritorio){
        this.nomeTerritorio = nomeTerritorio;
    }

    public void setImmagine(Image immagine){
        this.immagine = immagine;
    }

    public void setTerreno(Terreno terreno){
        this.terrenoXml = terreno;
    }

}
