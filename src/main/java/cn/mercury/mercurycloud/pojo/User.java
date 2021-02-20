package cn.mercury.mercurycloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User implements Serializable {
    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//oracle不支持
    private Integer userId;
    /**
     * 用户的激活码
     */
    private String openId;
    /**
     * 文件仓库ID
     */
    private Integer fileStoreId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 头像地址
     */
    private String imagePath;
    /**
     * 用户角色，0管理员，1普通用户
     */
    private Integer role;

}
