package com.example.lab4final.controllers;

import com.example.lab4final.HelloApplication;
import com.example.lab4final.domain.User;
import com.example.lab4final.service.ServiceUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Objects;

public class LogIn {
    ServiceUser serviceUser = ServiceUser.getInstance();
    @FXML
    private Label label;
    @FXML
    private TextField email;
    @FXML
    private PasswordField parola;
    @FXML
    public void login() {
        try {
            String emailUser = email.getText();
            String parolaUser = parola.getText();
            int ok = 0 ;
            for(User user:serviceUser.getAll()){
                if(Objects.equals(user.getEmail(), emailUser) && Objects.equals(user.getParola(), parolaUser)){
                    ok = 2;
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("userAccount.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(),600,500);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    UserAccount userAccount = fxmlLoader.getController();
                    userAccount.setUserlogged(user);
                    stage.show();
                    break;
                }
                else
                    ok = 1;
            }
            if(ok == 1){
                label.setText("Something is wrong! Check email or password!");
            }
        }
        catch(Exception e){
            label.setText(e.getMessage());
        }
    }

    public void onClickCreateAccount() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("createAccount.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),600,500);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            label.setText(e.getMessage());
        }
    }
}
