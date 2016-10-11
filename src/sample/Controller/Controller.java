package sample.Controller;

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

    private ServerListener serverListener;

    @FXML
    void callBtnPressed(ActionEvent event) {
        int port = Integer.parseInt(inputField.getText().split(":")[1]);
        String ip = inputField.getText().split(":")[0];
        serverListener.inviteRemoteClient(ip, port);

        declineBtn.setDisable(false);
    }

    @FXML
    void declineBtnPressed(ActionEvent event) {
        serverListener.getCurrentClientHandler().send("INVITE");
    }

    void setStatusLabel(String msg){
        statusLabel.setText(msg);
    }

    public void setServerListener(ServerListener serverListener){this.serverListener = serverListener;}
}
