package com.example.lab4final.domain.Validator;

import com.example.lab4final.domain.Friendship;


public class FriendshipValidator implements Validator<Friendship>{
    @Override
    public void validate(Friendship friendship) throws ValidationException {
        if(0>friendship.getId()){
            throw new ValidationException("Id-ul nu poate sa fie negativ!");
        }
        if(0>friendship.getIdUser1()){
            throw new ValidationException("Id-ul Userului1 nu poate sa fie negativ!");
        }
        if(0>friendship.getIdUser2()){
            throw new ValidationException("Id-ul Userului2 nu poate sa fie negativ!");
        }
        if(friendship.getIdUser2() == friendship.getIdUser1()){
            throw new ValidationException("Id-ul userilor trebuie sa fie diferite.");
        }
    }
}
