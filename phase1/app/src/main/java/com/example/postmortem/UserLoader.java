package com.example.postmortem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserLoader {

    private static List<User> users;
    private static List<Integer> hiscores;

    public static boolean loadUsers(){
        return false;
    }

    public static boolean createUser(){
        return false;
    }

    public static Optional<User> login(){
        return Optional.empty();
    }

    public static boolean LoadHiscores(){
        return false;
    }

    public static List<Integer> getHiscores(){
        return new ArrayList<>();
    }

}
