package cn.mercury.mercurycloud.controller;

import cn.mercury.mercurycloud.domain.User;
import cn.mercury.mercurycloud.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class MatisController {
    @Autowired
    private UserMapper userMapper;


   @RequestMapping("/user")
   @ResponseBody
    public List findAll(){
        List<User> all = userMapper.findAll();
       for (User user : all) {
           System.out.println(user);//sadadas
       }
        return all;
    }

}
