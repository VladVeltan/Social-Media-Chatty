package com.example.lab4final.controllers;

import com.example.lab4final.domain.Friendship;
import com.example.lab4final.domain.User;
import com.example.lab4final.service.ServiceFriendship;
import com.example.lab4final.service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserAccount  implements Initializable {
    ServiceUser serviceUser = ServiceUser.getInstance();
    ServiceFriendship serviceFriendship = ServiceFriendship.getInstance();
    @FXML
    private Label label;
    private User userlogged;
    private ObservableList<User> model = FXCollections.observableArrayList();
    @FXML
    Button buttonAddFriend;
    @FXML
    Button buttonRequestAccepted;
    @FXML
    Button buttonRequestDeclined;
    @FXML
    Button buttonDeleteFriend;

    public void setUserlogged(User userlogged1) {
        this.userlogged = userlogged1;
    }
    @FXML
    TableView<User> tableView =  new TableView<>();
    @FXML
    TableColumn<User, String> Prenume = new TableColumn<>();
    @FXML
    TableColumn<User, String> Nume = new TableColumn<>();
    @FXML
    TableColumn<User, String> Email = new TableColumn<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Prenume.setCellValueFactory(new PropertyValueFactory<User, String>("Prenume"));
        Nume.setCellValueFactory(new PropertyValueFactory<User, String>("Nume"));
        Email.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
        buttonAddFriend.setVisible(false);
        buttonRequestAccepted.setVisible(false);
        buttonRequestDeclined.setVisible(false);
        buttonDeleteFriend.setVisible(false); /// De aici crapa
        tableView.setItems(model);
    }

    @FXML
    public void deleteFriend() throws  IOException{
            //sterge un prieten din lista de prieteni
            User selecterUser=tableView.getSelectionModel().getSelectedItem();
            serviceFriendship.deleteElem(serviceFriendship.getByIdsofUsers(selecterUser.getId(),userlogged.getId()));
            showMyFriends();
    }

    private void initModel(){
        ArrayList<User> list = setFriends();
        List<User> user = StreamSupport.stream(list.spliterator(),false)
                .collect(Collectors.toList());
        model.setAll(user);
     }

     @FXML
     public void showMyFriends() throws IOException {
        //afiseaza prietenii din lista de prieteni
         buttonAddFriend.setVisible(false);
         buttonRequestAccepted.setVisible(false);
         buttonRequestDeclined.setVisible(false);
         buttonDeleteFriend.setVisible(true);
         initModel();
     }

     @FXML
     public void addFriend(){
        ///trimite o invitatie de prieteni catre alt utilizator.
        try {
            User selectedUser = tableView.getSelectionModel().getSelectedItem();
            Friendship friendship = new Friendship(ServiceFriendship.getInstance().getId(), userlogged.getId(), selectedUser.getId(), new Timestamp(System.currentTimeMillis()),"pending");
            serviceFriendship.addElem(friendship);
            showMyUnfriends();
        }
        catch (Exception e){
            label.setText(e.getMessage());
        }
     }

     @FXML
     public void setButtonRequestAccepted() throws IOException {
        ///accept un friend request
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        for(Friendship friendship:serviceFriendship.getAll()){
            if((userlogged.getId() == friendship.getIdUser1() && selectedUser.getId() == friendship.getIdUser2()) || (selectedUser.getId() == friendship.getIdUser1() && userlogged.getId() == friendship.getIdUser2()))
                serviceFriendship.update(friendship,new Friendship(friendship.getId(),userlogged.getId(),selectedUser.getId(),new Timestamp(System.currentTimeMillis()),"accepted"));
        }
        showRequests();
     }
     @FXML
    public void setButtonRequestDecline() throws IOException {
        ///nu accept un friend request
        ///trec prin prietenii, verific condiitiile, sterg prietenia cu statusul pending,afisez prietenii //TO DO
         User selectedUser = tableView.getSelectionModel().getSelectedItem();
         for(Friendship friendship:serviceFriendship.getAll()){
             if((userlogged.getId() == friendship.getIdUser1() && selectedUser.getId() == friendship.getIdUser2()) || (selectedUser.getId() == friendship.getIdUser1() && userlogged.getId() == friendship.getIdUser2()))
                 serviceFriendship.deleteElem(friendship);
         }
         showRequests();

    }
     @FXML
     public void showMyUnfriends() throws IOException{
         buttonAddFriend.setVisible(true);
         buttonRequestAccepted.setVisible(false);
         buttonRequestDeclined.setVisible(false);
         buttonDeleteFriend.setVisible(false);
         ArrayList<User> list = unfriends();
         List<User> user = StreamSupport.stream(list.spliterator(),false)
                 .collect(Collectors.toList());
         model.setAll(user);
     }

     @FXML
     public void showRequests() throws IOException{
        ///afiseaza cererile de prietenie
         buttonAddFriend.setVisible(false);
         buttonRequestAccepted.setVisible(true);
         buttonRequestDeclined.setVisible(true);
         buttonDeleteFriend.setVisible(false);
        ArrayList<User> list = requests();
         List<User> user = StreamSupport.stream(list.spliterator(),false)
                 .collect(Collectors.toList());
         model.setAll(user);
     }

    public ArrayList<User> unfriends(){
        //lista de persoane cu care nu este userul logged prieten
        boolean ok;
        ArrayList<User> myUnfriends = new ArrayList<>();
        for(User user: serviceUser.getAll()){
            ok = false;
            for(Friendship friendship: serviceFriendship.getAll()){
                if ((userlogged.getId() == friendship.getIdUser1() && user.getId() == friendship.getIdUser2()) || (user.getId() == friendship.getIdUser1() && userlogged.getId() == friendship.getIdUser2())){
                    ok = true;
                    break;
                }
            }
            if(!ok && !Objects.equals(userlogged.getId(), user.getId())) {
                myUnfriends.add(user);
            }
        }
        return myUnfriends;
    }

    public ArrayList<User> requests(){
        ///verifica statusul prieteniei si o aduga
        ArrayList<User> myRequests = new ArrayList<>();
        for(Friendship friendship: serviceFriendship.getAll()){
            if(Objects.equals(friendship.getStatus(), "pending")){
                if(friendship.getIdUser2() == userlogged.getId()){
                    myRequests.add(serviceUser.getById(friendship.getIdUser1()));
                }
            }
        }
        return myRequests;
    }

    public ArrayList<User> setFriends(){
        ///returneaza toate prieteniile
        ArrayList<User> friends = new ArrayList<>();
        for (Friendship friendship : serviceFriendship.getAll()) {
            if (friendship.getIdUser1() == userlogged.getId() && Objects.equals(friendship.getStatus(), "accepted")) {
                friends.add(serviceUser.getById(friendship.getIdUser2()));
            }
            if(friendship.getIdUser2() == userlogged.getId() && Objects.equals(friendship.getStatus(), "accepted")){
                friends.add(serviceUser.getById(friendship.getIdUser1()));
            }
        }
        return friends;
    }

}
