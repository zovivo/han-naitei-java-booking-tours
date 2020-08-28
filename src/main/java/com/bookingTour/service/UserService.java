package com.bookingTour.service;

import com.bookingTour.model.UserModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    public UserModel findUser(Long id);

    public UserModel findUserByUserName(String userName);

    public UserModel findUserByEmail(String email);

    public UserModel addUser(UserModel user) throws Exception;

    public UserModel editUser(UserModel userModel) throws Exception;

    public boolean deleteUser(UserModel userModel) throws Exception;

    public List<UserModel> findAll();

    public Page<UserModel> paginate(UserModel userModel);
}