package grafica;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Client;

import java.io.IOException;

public class MenuInizialeController {

    @FXML
    private Button play;
    @FXML
    private Button esci;

    public MenuInizialeController(){

    }

    @FXML
    public void initialize(){
            play.setOnAction(this::gioca);
            esci.setOnAction(this::esci);
    }

    private void gioca(ActionEvent event) {
        GiocaController controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Gioca.fxml"));
            Parent parent = loader.load();
            controller = loader.<GiocaController>getController();

            Scene scena = new Scene(parent, 400, 200);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scena);
            stage.setResizable(false);
            stage.showAndWait();

            if(controller.check) {
                loader = new FXMLLoader(getClass().getResource("MonopoliView.fxml"));
                parent  = loader.load();
                GamePlayController controllerMonopoli = loader.<GamePlayController>getController();

                if(!controllerMonopoli.connetti(controller.getIp(),controller.getNome())){
                    showDialog("Errore di connessione","Impossibile contattare il server: "+ controller.getIp() + " sulla porta: " + controllerMonopoli.getClient().getConnessione().getPorta(), Alert.AlertType.ERROR);
                    return;
                }

                int risultatoHandshake = controllerMonopoli.getClient().getConnessione().handShake(controller.getNome());
                if(risultatoHandshake == 0){
                    showDialog("Server pieno","Il server che stai provando a contattare e' pieno", Alert.AlertType.INFORMATION);
                    return;
                }
                else if(risultatoHandshake == -1){
                    showDialog("Protocollo non compatibile","Il server con cui stai comunicando utilizza un protocollo diverso dal tuo", Alert.AlertType.INFORMATION);
                    return;
                }

                scena = new Scene(parent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scena);
                window.setResizable(false);
                window.setOnHidden(e -> {
                    controllerMonopoli.shutDown();
                    Platform.exit();
                });
                window.show();

            }
        }
        catch(IOException e ){
            e.printStackTrace();
            return;
        }
    }

    private void showDialog(String header, String msg, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(header);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void esci(ActionEvent event){
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
