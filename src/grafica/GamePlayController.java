package grafica;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Client;
import monopoli.Terreno;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GamePlayController {

    private Client client;
    private GraphicsContext context;

    @FXML
    private Label coins,nomeLabel;
    @FXML
    private Canvas view;
    @FXML
    private ListView lista;

    public GamePlayController(){

    }

    @FXML
    public void initialize(){

        Platform.runLater( () -> {
            //LOADING BOARD
            File file = new File("/media/gabo/extra/downloadHDD/Monopoly/Board.png");
            Image image = new Image(file.toURI().toString());
            context = view.getGraphicsContext2D();
            context.drawImage(image,0,0,view.getWidth(),view.getHeight());

            //SET LABEL
            coins.setText("Badoni coins: " + client.getGiocatore().getBadoniCoins());
            nomeLabel.setText("Nome: "+client.getGiocatore().getNome());

            //START OBSERVABLE LIST
            ObservableList<String> mieiTerritori = FXCollections.observableArrayList();
            mieiTerritori.add("AulaAudiovisiva");
            mieiTerritori.add("LabCAD");
            lista.setItems(mieiTerritori);
            lista.setCellFactory(param -> new ListCell<String>(){
                private ImageView imageView = new ImageView();

                @Override
                public void updateItem(String nome, boolean empty){
                    super.setStyle("-fx-background-color: #3fd196");
                    super.updateItem(nome,empty);
                    if(empty){
                        setText(null);
                        setGraphic(null);
                    }else{
                        Image imageTerritorio = new Image(new File("/media/gabo/extra/downloadHDD/Monopoly/"+nome+".png").toURI().toString());
                        imageView.setImage(imageTerritorio);
                        imageView.setFitWidth(63);
                        imageView.setFitHeight(112);
                        VBox box = new VBox();
                        box.setAlignment(Pos.CENTER);
                        box.setPrefWidth(100);
                        box.getChildren().add(new Label(nome));
                        box.getChildren().add(imageView);
                        box.setStyle("-fx-background-color: #3fd196");
                        setGraphic(box);
                        setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if(mouseEvent.getClickCount() == 2) {   //Double click
                                    Terreno terrenoPremuto = null;
                                    for(Terreno terreno : client.getGiocatore().getTerreni())
                                        if(nome.equals(terreno.getNome()))
                                            terrenoPremuto = terreno;
                                    try {
                                        FXMLLoader loader =new FXMLLoader(getClass().getResource("MostraTerreno.fxml"));
                                        Parent parent = loader.load();
                                        MostraTerreno controller = loader.getController();
                                        controller.setImmagine(imageTerritorio);
                                        controller.setNomeTerritorio(nome);
                                        controller.setTerreno(terrenoPremuto);
                                        Scene scena = new Scene(parent, 525,350);
                                        Stage stage = new Stage();
                                        stage.initModality(Modality.APPLICATION_MODAL);
                                        stage.setScene(scena);
                                        stage.setResizable(false);
                                        stage.showAndWait();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                }
            });
        });
    }

    public void shutDown(){
        System.out.println("Mi sto chiudendo!");
        System.exit(0);
        //TODO on close invia pacchetto disconnessione!
    }


    public boolean connetti(String ip, String nome){
        client = new Client(nome,ip);
        return client.getConnessione().isConnected();
    }

    public Client getClient(){
        return client;
    }


}
