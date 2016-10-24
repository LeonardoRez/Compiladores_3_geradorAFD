/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Leonardo
 */
public class Compiladores3 extends Application {

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader interfaceGrafica = new FXMLLoader();
        //System.out.println(getClass().getResource("view/InterfacePrincipal.fxml"));
        interfaceGrafica.setLocation(getClass().getResource("view/JanelaPrincipal.fxml"));
        AnchorPane root;
        try {
            root = (AnchorPane) interfaceGrafica.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Trabalho 2");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
