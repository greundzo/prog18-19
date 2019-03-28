/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author greundzo
 */
public class Client extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Sign In");
        btn.setOnAction(new EventHandler<ActionEvent>() {                
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button Pressed");
            }
        });
        
        GridPane grid = new GridPane();
        
        Label userName = new Label("Username:");
        TextField userTextField = new TextField();
        Label passWord = new Label("Password:");
        TextField passTextField = new TextField();
        
        grid.add(userName, 3, 5);
        grid.add(userTextField, 4, 5);
        grid.add(passWord, 3, 6);
        grid.add(passTextField, 4, 6);
        grid.add(btn, 5, 7);
        
        //StackPane root = new StackPane();
        //root.getChildren().add(btn);
        //root.getChildren().add(grid);
      
        Scene scene = new Scene(grid, 500, 300);
        
        primaryStage.setTitle("@DiMailService");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
