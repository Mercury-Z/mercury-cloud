package cn.mercury.mercurycloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class FileStore implements Serializable {

    /**
    * 文件仓库ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileStoreId;
    /**
    * 主人ID
    */
    private Integer userId;
    /**
    * 当前容量（单位KB）
    */
    private Integer currentSize;
    /**
    * 最大容量（单位KB）
    */
    private Integer maxSize;
    /**
     * 仓库权限：0可上传下载、1只允许下载、2禁止上传下载
     */
    private Integer permission;

}