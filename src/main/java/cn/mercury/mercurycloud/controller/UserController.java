package cn.mercury.mercurycloud.controller;

import cn.mercury.mercurycloud.pojo.FileStore;
import cn.mercury.mercurycloud.pojo.User;
import cn.mercury.mercurycloud.service.FileStoreService;
import cn.mercury.mercurycloud.service.MailService;
import cn.mercury.mercurycloud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Controller
public class UserController extends BaseController{
    Logger logger = LoggerFactory.getLogger(UserController.class);
    /**
     * 登陆方法
     * @return
     */
    @PostMapping("login")
    public String login(User user,Map<String,Object> map){
        User userByEmail = userService.findUserByEmail(user.getEmail());
        if (userByEmail != null && userByEmail.getPassword().equals(user.getPassword())) {
            session.setAttribute("loginUser", userByEmail);
            return "redirect:/index";
        }else{
            String errorMsg = user == null ? "该邮箱尚未注册" : "密码错误";
            logger.info("登录失败！请确认邮箱和密码是否正确！");
            //登录失败，将失败信息返回前端渲染
            map.put("errorMsg", errorMsg);
            return "index";
        }
    }
    @RequestMapping("logout")
    public String logout(){
        session.invalidate();
        return "redirect:/";
    }
    /**
     *注册方法
     * @return
     */
    @PostMapping("/register")
    @Transactional
    public String register(User user, String code, Map<String,Object> map){
        //校验验证码
        Object regCode = session.getAttribute(user.getEmail()+"regCode");
        long sendTime = (long)session.getAttribute("sendTime");
        long nowTime = new Date().getTime();
        if (nowTime-sendTime>=1000*60*5+1){
            logger.info("nowTime - sendTime = "+(nowTime-sendTime));
            map.put("errorMsg", "验证码超时");
            return "index";
        }
        if (!code.equals(regCode)){
            map.put("errorMsg", "验证码错误");
            return "index";
        }
        logger.info("regCode is ;"+regCode);

        user.setRole(1);
        user.setRegisterTime(new Date());
        user.setImagePath("https://p.qpic.cn/qqconnect/0/app_101851241_1582451550/100?max-age=2592000&t=0");
        //插入用户记录
        userService.insert(user);

        if (user.getUserId()==null){
            throw new RuntimeException("用户创建发生异常,回滚数据库");
        }
        //开始创建仓库
        FileStore fileStore = FileStore.builder().userId(user.getUserId()).maxSize(1024*1024).currentSize(0).permission(0).build();
        fileStoreService.insert(fileStore);
        if (fileStore.getFileStoreId()==null){
            throw new RuntimeException("仓库创建发生异常,回滚数据库");
        }
        //更新user
        user.setFileStoreId(fileStore.getFileStoreId());
        userService.updateByUserId(user.getUserId(),fileStore.getFileStoreId());

        session.removeAttribute(user.getEmail()+"regCode");
        session.setAttribute("loginUser", user);
//        return "redirect:/";
        return "reg_success";
    }

    @RequestMapping("/sendCode")
    @ResponseBody
    public String sendCode(String email){
        User user = userService.findUserByEmail(email);
        //用户邮箱已被使用
        if (user!=null){
            return "exitEmail";
        }
        //生成验证码
        String regCode = mailService.sendCodeToMail("Mercury-Cloud 邮箱验证","",email);
       // HttpSession session = httpServletRequest.getSession();
        session.setAttribute("sendTime",new Date().getTime());
        session.setAttribute(email+"regCode",regCode);
        //发送验证码
        logger.info("regCode is:" + regCode);
        return "200";
    }

}
