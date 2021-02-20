package cn.mercury.mercurycloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileStoreInfo implements Serializable {

    /**
     * 文件仓库文件信息统计
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private FileStore fileStore;
    /**
     * 文档数
     */
    private int doc;
    /**
     * 音乐数
     */
    private int music;
    /**
     * 视频数
     */
    private int video;
    /**
     * 图像数
     */
    private int image;
    /**
     * 其他
     */
    private int other;
    /**
     * 文件数
     */
    private int fileCount;
    /**
     * 文件夹数
     */
    private int folderCount;
}
