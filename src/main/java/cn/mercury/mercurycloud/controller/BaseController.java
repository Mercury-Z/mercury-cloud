package cn.mercury.mercurycloud.controller;

import cn.mercury.mercurycloud.pojo.User;
import cn.mercury.mercurycloud.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {

    protected HttpSession session;
    @Autowired
    protected UserService userService;
    @Autowired
    protected FileStoreService fileStoreService;
    @Autowired
    protected MailService mailService;
    @Autowired
    protected FileService fileService;
    @Autowired
    protected FileFolderService fileFolderService;
    @Autowired
    protected FileMd5Service fileMd5Service;


    /**
     * 每次执行函数前先设置session
     * @param httpServletRequest
     */
    @ModelAttribute
    public void setSession(HttpServletRequest httpServletRequest){
        this.session = httpServletRequest.getSession(true);
//        User userByEmail = userService.findUserByEmail("2090007549@qq.com");
//        session.setAttribute("loginUser",userByEmail);
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null){
            User userById = userService.findUserById(loginUser.getUserId());
            if (userById == null)
                session.setAttribute("loginUser",null);
        }
    }
}
