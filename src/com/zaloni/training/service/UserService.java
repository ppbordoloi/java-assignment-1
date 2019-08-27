package com.zaloni.training.service;

import java.util.List;

import com.zaloni.training.dao.UserDao;
import com.zaloni.training.entity.User;

public class UserService {
    UserDao userdao;

    public UserService() {
        userdao = new UserDao();
    }

    public String getInvalidErrorMessage(String name, String email, String password, String confirmPassword) {
        String errorMessage = "";
        if (name == null || name.isEmpty()) {
            errorMessage = "Name can't be empty";
        }
        else if (email == null || email.isEmpty()) {
            errorMessage = "Email can't be empty";
        }
        else if (this.userdao.isEmailExists(email)) {
            errorMessage = "Email already in use";
        }
        else if (password == null || confirmPassword == null) {
            errorMessage = "Password required";
        }
        else if (!password.equals(confirmPassword)) {
            errorMessage = "Password does not match with confirmed password";
        }

        return errorMessage;
    }

    public boolean isEmailExists(String email) {
        return this.userdao.isEmailExists(email);
    }

    public boolean isValidCredential(String email, String password) {
        return this.userdao.isValidCredential(email, password);
    }
    
    public void registerUser(String email, String password, String name) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(name);
        user.setPassword(password);
        
        UserDao userdao = new UserDao();
        userdao.save(user);
    }

    public void updateUser(User user) {
        userdao.save(user);
    }

    public List<User> getUsers(String search) {
        return userdao.getUsers(search);
    }
    
    public User getUser(int id) {
        return userdao.getUser(id);
    }

    public void deleteUser(int id) {
        userdao.deleteUser(id);
    }

    public User getUserByEmail(String email) {
        return userdao.getUserByEmail(email);
    }
}
