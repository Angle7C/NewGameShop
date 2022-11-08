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
            return new JsonResult(450,"已登录");
        }
    }
    //注册按钮
    @PostMapping("registe")
    public JsonResult registe(ObjectAndString<User,String> oas){
        User user=oas.getFirst();
        String codeFornt=oas.getSecond();
        try{
            String codeBack=redisService.get(user.getEmail());
            if(codeBack.equals(codeFornt)){
                userService.addUser(user);
                return new JsonResult(450,"SUCCESS");
            }else {
                return new JsonResult(200,"Fail");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JsonResult(200,"系统异常");

    }
    //显示分页用户数据
    @GetMapping("user/page/{pageNum}/{pageSize}")
    public JsonResult findAllUser(@PathVariable int pageNum,@PathVariable int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return new JsonResult(450,"查询成功",userService.findAll());
    }
    //退出登录
    @PostMapping("logout")
    public JsonResult logOut(HttpSession session){
        session.removeAttribute("userId");
        return new JsonResult(450,"退出登录成功");
    }
    //修改用户
    @PutMapping("user")
    public JsonResult updateUser(User user){
        userService.updateUser(user);
        return new JsonResult(450,"修改成功");
    }

//    @RequestMapping("/updateuser.html")
//    @ResponseBody
//    @Transactional
//    public Map<String,Object> updataUser(@RequestBody User user , HttpSession session){
//        Map<String,Object> map=new TreeMap<>();
//        Integer userId=UserVerify.verify(session);
//        User t=new User();
//        if(userId==null) {
//            map.put("message",false);
//            map.put("data","未登录");
//            return map;
//        }
//        t = userService.findId(userId);
//        if(t.getUserPwd().equals(MD5.toMD5(user.getEmail()))) {
//
//            t.setUserPwd(MD5.toMD5(user.getUserPwd()));
//            t.setUserName(user.getUserName());
//            map.put("message", true);
//            userService.updateUser(t);
//            map.put("data", t);
//        }else{
//            map.put("message", false);
////            userService.updateUser(t);
//            map.put("data", "原密码错误");
//        }
//            return map;
//    }
    //删除用户
    @DeleteMapping("user")
    public JsonResult deleteUser(User user){
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
    public JsonResult charge(@PathVariable double money,HttpSession session){
        User user=userService.findId(UserVerify.verify(session));
        user.setMoney(user.getMoney()+money);
        userService.updateUser(user);
        return new JsonResult(450,"充值成功");
    }
//    @RequestMapping("/deleteuser.html")
//    @ResponseBody
//    public Map<String,Object> deleteUser(@RequestParam("userId") Integer userId, HttpSession session){
//        Map<String,Object> map=new TreeMap<>();
//        User user=new User();
//        if(userId==null) {
//            map.put("message",false);
//            map.put("data","不能为空");
//            return map;
//        }
//        map.put("message",true);
//        user.setUserId(userId);
//        userService.deleteUser(user);
//        map.put("data","删除成功");
//        return map;
//    }
//    @RequestMapping("adminupdatauser.html")
//    @ResponseBody
//    @Transactional
//    public Map<String ,Object> updateAdminUser(@RequestBody User user,HttpSession session){
//        Map<String,Object> map=new HashMap<String,Object>();
//        try {
//            User t=userService.findId(user.getUserId());
//            t.setUserPwd(MD5.toMD5(user.getUserPwd()));
//            userService.updateUser(t);
//            map.put("message", true);
//            map.put("data", "修改成功");
//        }catch (Exception e){
//            map.put("message", false);
//            map.put("data", "用户不存在");
//        }
//        return map;
//    }
//    @RequestMapping("adminupdeleteuser.html")
//    @ResponseBody
//    @Transactional
//    public Map<String ,Object> deleteAdminUser(@RequestBody User user,HttpSession session){
//        Map<String,Object> map=new HashMap<String,Object>();
//        Integer userId=user.getUserId();
//        User t=userService.findId(user.getUserId());
//        t.setUserName(user.getUserName());
//        t.setUserPwd(MD5.toMD5(user.getUserPwd()));
//        userService.updateUser(t);
//        map.put("message",true);
//        map.put("data",t);
//        return map;
//    }
//
//    @RequestMapping("adminseekeuser.html")
//    @ResponseBody
//    @Transactional
//    public Map<String ,Object> seekAdminUser(@RequestParam("name") String name,HttpSession session){
//        Map<String,Object> map=new HashMap<String,Object>();
//        User user=new User();
//        List<User> users = userService.seekUser(name);
//        map.put("message",true);
//        map.put("data",users);
//        return map;
//    }
//    @RequestMapping("/charge.html")
//    @ResponseBody
//    @Transactional
//    public Map<String ,Object> charge(@RequestParam Double money, HttpSession session) {
//        Map<String, Object> map = new HashMap<>();
//        User user = new User();
//        Integer id = UserVerify.verify(session);
//        User users = userService.findId(id);
//        users.setMoney(users.getMoney()+money);
//        userService.updateUser(users);
//        map.put("message", true);
//        map.put("data", "成功");
//        return map;
//    }
}
