package com.example.newgameshop.mapper;



import com.example.newgameshop.entity.User;

import java.util.List;

public interface UserMapper {
    public List<User> findAll();
    public User findId(Integer id);
    public User findEmail(String name);
    public List<User> seekUser(String name);
    public void addUser(User user);
    public void updateUser(User user);
    public void updateUserRole(User user);
    public void deleteUser(User user);
    public List<User> findPage(Integer size,Integer page);

}
