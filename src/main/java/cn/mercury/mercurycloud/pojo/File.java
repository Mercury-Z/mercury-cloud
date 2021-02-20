package cn.mercury.mercurycloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@Data
@Builder
public class File implements Serializable {
    /**
    * 文件ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer myFileId;
    /**
    * 文件名
    */
    private String myFileName;
    /**
    * 文件仓库ID
    */
    private Integer fileStoreId;
    /**
    * 文件MD5ID
    */
    private Integer myFileMd5Id;
    /**
    * 下载次数
    */
    private Integer downloadTime;
    /**
    * 上传时间
    */
    private Date uploadTime;
    /**
    * 父文件夹ID
    */
    private Integer parentFolderId;
    /**
    * 文件大小
    */
    private Integer size;
    /**
    * 文件类型
    */
    private Integer type;
    /**
    * 文件后缀
    */
    private String postfix;

}