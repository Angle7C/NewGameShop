package com.example.newgameshop.controller;

import com.gameshop.entity.Game;
import com.gameshop.entity.Page;
import com.gameshop.entity.User;
import com.gameshop.service.UserService;
import com.gameshop.until.*;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserController {
    private UserService userService;
    public UserService getUserService() {
        return userService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login.html")
    @ResponseBody
    public Map<String,Object> login(@RequestBody User user , Model model,HttpSession session){
        Map<String,Object> map=new TreeMap<>();
        Map<String,Integer> m=new TreeMap<String,Integer>();
        try{
            User t=userService.findEmail(user.getEmail());
            if(MD5.toMD5(user.getUserPwd()).equals(t.getUserPwd())){
                session.setAttribute("userId",t.getUserId());
                map.put("message",true);
                m.put("role",t.getRole());
                m.put("userId",t.getUserId());
                map.put("data",m);
            }else{
                map.put("message",false);
                map.put("data","密码错误");
            }
        }catch (Exception e){
            map.put("message",false);
            map.put("data","用户错误");
        }
        return map;
    }
    @RequestMapping("/islogin.html")
    @ResponseBody
    public Map<String,Object> isLogin(
            HttpSession session){
        Map<String,Object> map=new TreeMap<>();
        if(session.getAttribute("userId")==null){
            map.put("message",false);
            map.put("data","未登录");
        }else{
            User user = userService.findId((Integer) session.getAttribute("userId"));
            user.setUserPwd("0");
            map.put("message",true);
            map.put("data",user);
        }
        return map;
    }
    @RequestMapping("/registerEmail.html")
    @ResponseBody
    public Map<String,Object> register(@RequestBody String email, Model model, HttpSession session){
        System.out.println(session.getId());
        Map<String,Object> map=new TreeMap<String,Object>();
        String number=null ;
        System.out.println(email);
        email= Json.toAttribute(email,"email");
        System.out.println(email);

        if( userService.findEmail( email)!=null ){
            map.put("message", false);
            map.put("data","邮箱已使用");
        }else{
            try {
                number=SendEmail.sendTextMail(email);
                map.put("message",true);
                map.put("data",number);
                session.setAttribute("number",number);
            }catch (EmailException e){
                e.printStackTrace();
                map.put("message",false);
                map.put("data","邮箱错误");
            }
            System.out.println(session.getId());

        }
        return map;
    }
    @RequestMapping("/register.html")
    @ResponseBody
    public Map<String,Object> registers(@RequestBody ObjectAndString<User,String> t, Model model, HttpSession session){
        User user=t.getFirst();
        String number=t.getSecond();
        System.out.println(session.getId());
        String str=(String)session.getAttribute("number");
        session.removeAttribute("number");
        Map<String,Object> map=new TreeMap<String,Object>();
        if(number.equals(str)){
            user.setUserPwd(MD5.toMD5(user.getUserPwd()));
            userService.addUser(user);
            map.put("message",true);
            map.put("data","注册成功");
        }else{
            map.put("message",false);
            map.put("data","失败");
        }
        return map;
    }
    @RequestMapping("/userpage.html")
    @ResponseBody
    public Map<String,Object> userPage(@RequestBody ObjectAndString<Integer,Page> t, Model model, HttpSession session){
        Map<String,Object> map=new TreeMap<String,Object>();
        Map<String,Object> m=new TreeMap<String,Object>();
        Page page=t.getSecond();
        if(page.getTotalPages()< page.getCurrentPage()||page.getCurrentPage()<1){
            map.put("message",false);
            map.put("data","页面数出错");
        }else if(t.getFirst()==1){
            m.put("page", page);
            m.put("array", userService.findPage(page.getPageSize(), page.getCurrentPage()));
            map.put("message",true);
            map.put("data",m);
        }else{
            map.put("message",false);
            map.put("data","用户验证失败");
        }
        return  map;

    }
    @RequestMapping("/userpagesize.html")
    @ResponseBody
    public Map<String,Object> pageSize(HttpSession session,@RequestBody  ObjectAndString<Integer,Integer> m , Model model){
//        Integer userId
        Map<String,Object> map=new TreeMap<String,Object>();

        System.out.println(m.getFirst());
        if(m.getFirst()==1){
            Page page=new Page();
            page.setPageSize(m.getSecond());
            List<User>  L= userService.findAll();
            page.setTotalPages(L.size() / page.getPageSize()+(L.size()%page.getPageSize()==0 ? 0 : 1 ));
            page.setCurrentPage(1);
            map.put("message",true);
            map.put("data",page);
        }else{
            map.put("message",false);
            map.put("data","用户验证失败");
        }
        return  map;
    }
    @RequestMapping("/logout.html")
    @ResponseBody
    public Map<String,Object> logout(HttpSession session){
        Integer userId=UserVerify.verify(session);
        session.removeAttribute("userId");
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("message",true);
        map.put("data","登出成功");
        return map;
    }

    @RequestMapping("/updateuser.html")
    @ResponseBody
    @Transactional
    public Map<String,Object> updataUser(@RequestBody User user , HttpSession session){
        Map<String,Object> map=new TreeMap<>();
        Integer userId=UserVerify.verify(session);
        User t=new User();
        if(userId==null) {
            map.put("message",false);
            map.put("data","未登录");
            return map;
        }
        t = userService.findId(userId);
        if(t.getUserPwd().equals(MD5.toMD5(user.getEmail()))) {

            t.setUserPwd(MD5.toMD5(user.getUserPwd()));
            t.setUserName(user.getUserName());
            map.put("message", true);
            userService.updateUser(t);
            map.put("data", t);
        }else{
            map.put("message", false);
//            userService.updateUser(t);
            map.put("data", "原密码错误");
        }
            return map;
    }
    @RequestMapping("/deleteuser.html")
    @ResponseBody
    public Map<String,Object> deleteUser(@RequestParam("userId") Integer userId, HttpSession session){
        Map<String,Object> map=new TreeMap<>();
        User user=new User();
        if(userId==null) {
            map.put("message",false);
            map.put("data","不能为空");
            return map;
        }
        map.put("message",true);
        user.setUserId(userId);
        userService.deleteUser(user);
        map.put("data","删除成功");
        return map;
    }
    @RequestMapping("adminupdatauser.html")
    @ResponseBody
    @Transactional
    public Map<String ,Object> updateAdminUser(@RequestBody User user,HttpSession session){
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            User t=userService.findId(user.getUserId());
            t.setUserPwd(MD5.toMD5(user.getUserPwd()));
            userService.updateUser(t);
            map.put("message", true);
            map.put("data", "修改成功");
        }catch (Exception e){
            map.put("message", false);
            map.put("data", "用户不存在");
        }
        return map;
    }
    @RequestMapping("adminupdeleteuser.html")
    @ResponseBody
    @Transactional
    public Map<String ,Object> deleteAdminUser(@RequestBody User user,HttpSession session){
        Map<String,Object> map=new HashMap<String,Object>();
        Integer userId=user.getUserId();
        User t=userService.findId(user.getUserId());
        t.setUserName(user.getUserName());
        t.setUserPwd(MD5.toMD5(user.getUserPwd()));
        userService.updateUser(t);
        map.put("message",true);
        map.put("data",t);
        return map;
    }

    @RequestMapping("adminseekeuser.html")
    @ResponseBody
    @Transactional
    public Map<String ,Object> seekAdminUser(@RequestParam("name") String name,HttpSession session){
        Map<String,Object> map=new HashMap<String,Object>();
        User user=new User();
        List<User> users = userService.seekUser(name);
        map.put("message",true);
        map.put("data",users);
        return map;
    }
    @RequestMapping("/charge.html")
    @ResponseBody
    @Transactional
    public Map<String ,Object> charge(@RequestParam Double money, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        Integer id = UserVerify.verify(session);
        User users = userService.findId(id);
        users.setMoney(users.getMoney()+money);
        userService.updateUser(users);
        map.put("message", true);
        map.put("data", "成功");
        return map;
    }
}
