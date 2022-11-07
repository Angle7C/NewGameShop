package com.example.newgameshop.serviceImpl;

import com.gameshop.entity.User;
import com.gameshop.mapper.UserMapper;
import com.gameshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    public UserMapper getUserMapper() {
        return userMapper;
    }
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    public void addUser(User user){
        userMapper.addUser(user);
    }
    public void deleteUser(User user){
        userMapper.deleteUser(user);
    }
    public void updateUser(User user){
        userMapper.updateUser(user);
    }
    public List<User> findAll() { return userMapper.findAll(); }
    public User findId(Integer id){
        return  userMapper.findId(id);
    }
    public List<User> seekUser(String name) {
        return userMapper.seekUser("%"+name+"%");
    }
    public User findEmail(String email){
        return userMapper.findEmail( email );
    }
    public List<User> findPage(Integer size,Integer page){
        return userMapper.findPage(size,(page-1)*size);
    }
}
