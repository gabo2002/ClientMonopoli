package grafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GiocaController {

    private String ipString,nomeString;
    public boolean check = false;

    @FXML
    private Button conferma,annulla;

    @FXML
    private TextField ip,nome;

    public GiocaController(){

    }

    @FXML
    public void initialize(){
        conferma.setOnAction(this::clickConferma);
        annulla.setOnAction(this::annulla);
    }

    private void clickConferma(ActionEvent event){
        ipString = ip.getText();
        nomeString = nome.getText();
        check = true;
        annulla(event);
    }

    private void annulla(ActionEvent event){
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public String getIp(){
        return ipString;
    }

    public String getNome(){
        return nomeString;
    }

}
