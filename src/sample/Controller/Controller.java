package sample.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Model.Net.ServerListener;

public class Controller {
    @FXML
    private TextField inputField;

    @FXML
    private Button declineBtn;

    @FXML
    private Button callBtn;

    @FXML
    private Label statusLabel;

    @FXML
    private Button answerBtn;

    @FXML
    private Button byeBtn;

    private ServerListener serverListener;

    @FXML
    void answerBtnPressed(){
        if(serverListener.getCurrentClientHandler() != null){
            serverListener.invokeAcceptInvite();
        }
        incomingCallStop();
    }

    @FXML
    void callBtnPressed(ActionEvent event) {
        int port = Integer.parseInt(inputField.getText().split(":")[1]);
        String ip = inputField.getText().split(":")[0];
        serverListener.inviteRemoteClient(ip, port);
        incomingCallStop();
    }

    @FXML
    void declineBtnPressed(ActionEvent event) {
        serverListener.invokeBye();
        incomingCallStop();
    }

    @FXML
    void byeBtnPressed(ActionEvent event){
        if(serverListener.getCurrentClientHandler()!=null)
            serverListener.invokeBye();
    }

    public void setStatusLabel(String msg){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                statusLabel.setText(msg);
            }
        });
    }

    public void setServerListener(ServerListener serverListener){this.serverListener = serverListener;}

    public void incomingCallStart(){
        //enable call/decline buttons
        setStatusLabel("Incomming call");
        answerBtn.setDisable(false);
        declineBtn.setDisable(false);
    }

    public void incomingCallStop(){
        //disable call/decline buttons
        answerBtn.setDisable(true);
        declineBtn.setDisable(true);
    }
}
