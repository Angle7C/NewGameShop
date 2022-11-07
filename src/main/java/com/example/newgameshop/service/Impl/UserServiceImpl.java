package com.example.newgameshop.service.Impl;

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
        return null;
    }

    @Override
    public User findId(Integer id) {
        return null;
    }

    @Override
    public User findEmail(String name) {
        return userMapper.findEmail(name);
    }

    @Override
    public List<User> seekUser(String name) {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public List<User> findPage(Integer size, Integer page) {
        return null;
    }
}
