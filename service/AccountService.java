package com.javafx.librarian.service;

import com.javafx.librarian.dao.AccountDao;
import com.javafx.librarian.model.Account;

import java.util.List;

public class AccountService {
    private static AccountService instance;
    private AccountService(){}

    public static AccountService getInstance(){
        if(instance==null)
            instance = new AccountService();
        return instance;
    }

    public List<Account> getAllUsers(){
        return AccountDao.getInstance().getAllUsers();
    }

    public void addUser(Account user){
        AccountDao.getInstance().addUser(user);
    }

    public Account getUser(String username, String password){
        return AccountDao.getInstance().getUser(username, password);
    }
    public Account getUserById(String username) {
        return AccountDao.getInstance().getUserById(username);
    }

    public boolean checkCreateUser(String username, String email){
        return AccountDao.getInstance().checkCreateUser(username, email);
    }

    public int editUser(Account user) {
        return AccountDao.getInstance().editUser(user);
    }

    public List<Account> getUserNoOwner(){return AccountDao.getInstance().getUserNoOwner();}
}
