package com.example.newgameshop.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.newgameshop.entity.JsonResult;
import com.example.newgameshop.entity.ObjectAndString;
import com.example.newgameshop.entity.User;
import com.example.newgameshop.service.RedisService;
import com.example.newgameshop.service.UserService;
import com.example.newgameshop.untils.UserVerify;
import com.example.newgameshop.utils.RegisteredEmailUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@Setter(onMethod_ = {@Autowired})
public class UserController {
    private UserService userService;
    private RedisService redisService;
    private RegisteredEmailUtil registeredEmailUtil;
    //用户登录
    @PostMapping("/login/{email}/{password}")
    public JsonResult login(@PathVariable String email, @PathVariable String password,HttpSession session){
        User user=userService.findEmail(email);
        if(user.getUserPwd().equals(password)){
            session.setAttribute("userId", user.getUserId());
            return new JsonResult(450,"登录成功",user);
        }else{
            return new JsonResult(200,"登录失败");
        }
    }
    //发送Email密匙(注册时获取验证码按钮)
    @PostMapping("/sendCode/{email}")
    public JsonResult sendCode(@PathVariable("email") String email){
        if(userService.findEmail(email)!=null){
            return new JsonResult(200,"邮箱已使用");
        }else {
            if(registeredEmailUtil.registerCode(email)){
                return new JsonResult("SUCCESS");
            }else {
                return new JsonResult(200,"Fail");
            }
        }
    }
    //登录状态判断
    @GetMapping("islogin")
    public JsonResult isLogin(HttpSession session){
        if(ObjectUtil.isEmpty(UserVerify.verify(session))){
            return new JsonResult(200,"未登录");
        }else {
            User user = userService.findId(UserVerify.verify(session));
            return new JsonResult(450,"已登录",user);
        }
    }
    //注册按钮
    @PostMapping("registe")
    public JsonResult registe(@RequestBody ObjectAndString<User,String> oas){
        User user=oas.getFirst();
        String codeFornt=oas.getSecond();
        if(registeredEmailUtil.checkCode(user.getEmail(),codeFornt)){
            userService.addUser(user);
            return new JsonResult(450,"注册成功");

        }else{
            return new JsonResult(200,"系统异常");
        }

    }
    //显示分页用户数据
    @GetMapping("user/page/{pageNum}/{pageSize}")
    public JsonResult findAllUser(@PathVariable int pageNum,@PathVariable int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList=userService.findAll();
        PageInfo<User> pageInfo=new PageInfo<>(userList);
        return new JsonResult(450,"查询成功",pageInfo);
    }
    //退出登录
    @PostMapping("logout")
    public JsonResult logOut(HttpSession session){
        session.removeAttribute("userId");
        return new JsonResult(450,"退出登录成功");
    }
    //修改用户（前台）
    @PutMapping("userfront")
    public JsonResult updateUser(@RequestBody User user){
        User u=userService.findId(user.getUserId());
        if(!u.getUserPwd().equals(user.getEmail())){
            return new JsonResult("200","密码不一致");
        }
        u.setUserPwd(user.getUserPwd());
        u.setUserName(user.getUserName());
        userService.updateUser(u);
        return new JsonResult(450,"修改成功");
    }
    //修改用户权限(后台)
    @PutMapping("user")
    public JsonResult updateUsereFront(@RequestBody User user){
        User u= userService.findId(user.getUserId());
        System.out.println(user.getUserPwd());
        if(user.getUserPwd().equals("admin")){
            u.setRole(1);
        }else{
            u.setRole(0);
        }
        userService.updateUserRole(u);
        return new JsonResult(450,"修改成功");
    }
    //删除用户
    @DeleteMapping("user")
    public JsonResult deleteUser(@RequestBody User user){
        System.out.println(user.getUserId());
        userService.deleteUser(userService.findId(user.getUserId()));
        return new JsonResult(450,"删除用户成功");
    }

    //查询用户
    @GetMapping("user/{name}")
    public JsonResult searchUser(@PathVariable String name){
        List<User> list=userService.seekUser(name);
        if(list.size()==0){
            return new JsonResult(200,"查无此人");
        }else {
            return new JsonResult(450,"查询成功",list);
        }
    }

    //充值
    @PostMapping("charge/{money}")
    public JsonResult charge(@PathVariable Double money,HttpSession session){
        User user=userService.findId(UserVerify.verify(session));
        if(money<0){
            return new JsonResult(200,"傻逼");

        }
        user.setMoney(user.getMoney()+money);
        userService.updateUser(user);
        return new JsonResult(450,"充值成功");
    }

}
