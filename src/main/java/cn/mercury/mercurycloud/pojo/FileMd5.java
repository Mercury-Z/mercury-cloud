package cn.mercury.mercurycloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: Mercury-Z
 * 文件md5关联对象
 */
@AllArgsConstructor
@Data
@Builder
public class FileMd5 {

    /**
     * 文件md5id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileMd5Id;
    /**
     * 文件md5
     */
    private String fileMd5;
    /**
     * 该md5文件被上传次数
     */
    private Integer uploadCount;


}
