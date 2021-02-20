package cn.mercury.mercurycloud.controller;

import cn.mercury.mercurycloud.pojo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.compiler.Keywords;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author: Mercury-Z
 */
@Controller
public class AdminController extends BaseController {
    /**
     * 用户管理界面
     * @param map
     * @param cur
     * @return
     */
    @GetMapping("/manages-users")
    public String manageUsers(Map<String,Object> map, Integer cur){
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser.getRole() == 1){
            //用于无访问权限
            return "redirect:/error401Page";
        }
        //获取全部的用户
        Integer usersCount = userService.getUsersCount();
        //获取当前查询的页数，如果为空，默认为0
        cur = (cur == null || cur<0)?0:cur;
        //获得统计信息
        FileStoreInfo fileStoreInfo = fileStoreService.countFileStore(loginUser.getFileStoreId());
        //分页获得20个用户信息
        Page<Object> page = PageHelper.startPage(cur, 20);
        List<UserToShow> users = userService.getUsers();
//        PageInfo<UserToShow> page =  new PageInfo<>(users);
        map.put("fileStoreInfo", fileStoreInfo);
        map.put("users", users);
        map.put("page", page);
        map.put("usersCount", usersCount);
        return "admin/manage-users";
    }


    /**
     * 修改用户的权限和最大容量
     * @param uId
     * @param pre
     * @param size
     * @return
     */
    @GetMapping("/updateStoreInfo")
    @ResponseBody
    public String updateStoreInfo(Integer uId,Integer pre,Integer size){
        Integer integer = fileStoreService.updatePermission(uId, pre, size*1024);
        if (integer == 1) {
            //更新成功，返回200状态码
            System.out.println("修改用户"+userService.findUserById(uId).getUserName()+"：的权限和仓库大小成功！");
            return "200";
        }else {
            //更新失败，返回500状态码
            System.out.println("修改用户"+userService.findUserById(uId).getUserName()+"：的权限和仓库大小失败！");
            return "500";
        }
    }

    /**
     * 删除用户
     * @param uId
     * @param cur
     * @return
     */
    @GetMapping("/deleteUser")
    @Transactional
    public String deleteUser(Integer uId,Integer cur){
        cur = (cur == null || cur < 0)?1:cur;
        User user = userService.findUserById(uId);
        FileStore fileStore = fileStoreService.getFileStoreByFileStoreId(user.getFileStoreId());
        //删除该仓库下的所有文件和文件夹
        fileService.deleteFilesByFileStoreId(user.getFileStoreId());
        fileFolderService.deleteFileFoldersByFileStoreId(user.getFileStoreId());
        userService.deleteById(uId);
        fileStoreService.deleteById(fileStore.getFileStoreId());
        return "redirect:/manages-users?cur="+cur;
    }
}
