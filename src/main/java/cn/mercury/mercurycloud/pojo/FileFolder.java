package cn.mercury.mercurycloud.pojo;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * (FileFolder)文件夹实体类
 */
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@ToString
public class FileFolder implements Serializable {

    /**
    * 文件夹ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileFolderId;
    /**
    * 文件夹名称
    */
    private String fileFolderName;
    /**
    * 父文件夹ID
    */
    private Integer parentFolderId;
    /**
    * 所属文件仓库ID
    */
    private Integer fileStoreId;
    /**
    * 创建时间
    */
    private Date time;

}