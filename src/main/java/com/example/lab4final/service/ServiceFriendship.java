package com.example.lab4final.service;


import com.example.lab4final.domain.Friendship;
import com.example.lab4final.domain.Validator.FriendshipValidator;
import com.example.lab4final.domain.Validator.ValidationException;
import com.example.lab4final.repository.dbrepo.FriendshipDbRepository;

import java.util.List;
import java.util.Objects;

public class ServiceFriendship{
    FriendshipValidator friendshipValidator = new FriendshipValidator();
    FriendshipDbRepository friendshipRepository = FriendshipDbRepository.getInstance();

    private static ServiceFriendship instance = null;

    private ServiceFriendship(){}

    public static ServiceFriendship getInstance() {
        if (instance == null) {
            instance = new ServiceFriendship();
        }
        return instance;
    }

    public void addElem(Friendship friendship) {
        for(Friendship friendship1: friendshipRepository.findAll()){
            if((Objects.equals(friendship1.getId(), friendship.getId()) || (friendship1.getIdUser1() == friendship.getIdUser1() && friendship1.getIdUser2()==friendship.getIdUser2()) || (friendship1.getIdUser1() == friendship.getIdUser2() && friendship1.getIdUser2()==friendship.getIdUser1()))){
                throw new ValidationException("Aceasta prietenie exista deja!");
            }
        }
        friendshipValidator.validate(friendship);
        friendshipRepository.save(friendship);
    }

    public Integer getId() {
        Integer k=0;
        for (Friendship friendship : friendshipRepository.findAll()) {
            if(k<friendship.getId())
                k= friendship.getId();
        }
        return k+1;
    }
    public Friendship getByIdsofUsers(int idu1,int idu2){
        for(Friendship friendship: friendshipRepository.findAll()){
            if((friendship.getIdUser1() == idu1 && friendship.getIdUser2()==idu2 )||(friendship.getIdUser1() == idu2 && friendship.getIdUser2()==idu1))
                return friendship;
        }
        throw new ValidationException("Nu exista astfel de prietenie cu id-ul dat");
    }
    public Friendship getByIdofFriendship(int id){
        for(Friendship friendship: friendshipRepository.findAll()){
            if(friendship.getId() == id)
                return friendship;
        }
        throw new ValidationException("Nu exista astfel de prietenie cu id-ul dat");
    }

    public List<Friendship> getAll() {
        return friendshipRepository.findAll();
    }

    public void update(Friendship entity, Friendship newEntity){
        friendshipValidator.validate(newEntity);
        friendshipRepository.update(entity,newEntity);
    }

    public void deleteElem(Friendship friendship) {
        friendshipRepository.delete(friendship);
    }
}
