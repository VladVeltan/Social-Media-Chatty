package com.example.lab4final.service;


import com.example.lab4final.domain.Friendship;
import com.example.lab4final.domain.User;
import com.example.lab4final.domain.Validator.UserValidator;
import com.example.lab4final.domain.Validator.ValidationException;
import com.example.lab4final.repository.dbrepo.UserDbRepository;

import java.util.List;
import java.util.Objects;

public class ServiceUser {

    ServiceFriendship serviceFriendship = ServiceFriendship.getInstance();
    UserValidator userValidator = new UserValidator();
    UserDbRepository userRepository = UserDbRepository.getInstance();

    private static ServiceUser instance = null;

    private ServiceUser(){}

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }


    public void addElem(User user) {
        for(User user1: userRepository.findAll()){
            if(Objects.equals(user.getId(), user1.getId())){
                throw new ValidationException("Acest user exista deja!");
            }
            if(Objects.equals(user1.getEmail(), user.getEmail())){
                throw new ValidationException("Acest email este deja utilizat!");
            }
        }
        userValidator.validate(user);
        userRepository.save(user);
    }

    public Integer getId() {
        Integer k=0;
        for (User user : userRepository.findAll()) {
            if(k<user.getId())
                k= user.getId();
        }
        return k;
    }

    public void deleteElem(User user) {
        for(int i = 0; i < serviceFriendship.getAll().size(); i++) {
            Friendship friendship = serviceFriendship.getAll().get(i);
            if(friendship.getIdUser1() == user.getId() || friendship.getIdUser2() == user.getId()) {
                i--;
                serviceFriendship.deleteElem(friendship);
            }
        }
        boolean ok = false;
        for(User user1: userRepository.findAll()){
            if (user1 == user) {
                ok = true;
                break;
            }
        }
        if(!ok) {
            throw new ValidationException("Userul nu exista!");
        }
        else {
            userRepository.delete(user);
        }
    }

    public User getById(int id){
        for(User user: userRepository.findAll()){
            if(user.getId() == id)
                return user;
        }
        throw new ValidationException("Nu exista un user cu acest id!");
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
