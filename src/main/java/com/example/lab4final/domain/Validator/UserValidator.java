package com.example.lab4final.domain.Validator;

import com.example.lab4final.domain.User;

public class UserValidator implements Validator<User>{
    @Override
    public void validate(User user) throws ValidationException {
        if(0>user.getId()){
            throw new ValidationException("Id-ul nu poate sa fie negativ!");
        }
    }
}
