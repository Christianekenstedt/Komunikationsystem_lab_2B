package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Controller.Controller;
import sample.Model.Net.ServerListener;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/client.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));

        Controller controller = loader.getController();
        ServerListener serverListener = new ServerListener();
        serverListener.setController(controller);
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    serverListener.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        controller.setServerListener(serverListener);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                serverListener.invokeBye();
                serverListener.stopListening();
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
