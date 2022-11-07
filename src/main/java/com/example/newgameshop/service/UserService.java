package com.example.newgameshop.service;

import com.gameshop.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface UserService {
    public List<User> findAll();
    public User findId(Integer id);
    public User findEmail(String name);
    public List<User> seekUser(String name);
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(User user);
    public List<User> findPage(Integer size,Integer page);

}
