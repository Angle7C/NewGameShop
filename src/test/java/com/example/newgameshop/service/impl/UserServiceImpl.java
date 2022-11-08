package com.example.newgameshop.service.impl;

import com.example.newgameshop.entity.User;
import com.example.newgameshop.mapper.UserMapper;
import com.example.newgameshop.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findId(Integer id) {
        return userMapper.findId(id);
    }

    @Override
    public User findEmail(String name) {
        return userMapper.findEmail(name);
    }

    @Override
    public List<User> seekUser(String name) {
        return userMapper.seekUser(name);
    }

    @Override
    public void addUser(User user) {
       userMapper.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userMapper.deleteUser(user);
    }

    @Override
    public List<User> findPage(Integer size, Integer page) {
        return userMapper.findPage(size,page);
    }
}
