package cn.mercury.mercurycloud;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Date;

public class md5Test {
    public static void main(String[] args) throws IOException {
        Date date = new Date();
        long start = date.getTime();
     //   String md5Hex = DigestUtils.md5Hex(new FileInputStream("C:\\DATA\\GL\\树莓立方体\\root.pfs.002"));
       // System.out.println(md5Hex);
        System.out.println(System.currentTimeMillis()-start);
        start= System.currentTimeMillis();
        File file = new File("C:\\DATA\\GL\\树莓立方体\\root.pfs.002");
        System.out.println(getMd5ByFile(file));
        System.out.println(System.currentTimeMillis()-start);
    }



        public static String getMd5ByFile(File file) throws FileNotFoundException {
            FileInputStream in = new FileInputStream(file);
            StringBuffer sb = new StringBuffer();
            try {
                FileChannel channel = in.getChannel();
                long position = 0;
                long total = file.length();
                long page = 1024 * 1024 * 500;
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                while (position < total) {
                    long size = page <= total - position ? page : total - position;
                    MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, position, size);
                    position += size;
                    md5.update(byteBuffer);
                }
                byte[] b = md5.digest();

                for (int i = 0; i < b.length; i++) {
                    sb.append(byteToChars(b[i]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sb.toString();
        }

        private static char[] byteToChars(byte b) {
            int h = ((b & 0xf0) >> 4);
            int l = (b & 0x0f);
            char[] r = new char[2];
            r[0] = intToChart(h);
            r[1] = intToChart(l);

            return r;
        }

        private static char intToChart(int i) {
            if (i < 0 || i > 15) {
                return ' ';
            }
            if (i < 10) {
                return (char) (i + 48);
            } else {
                return (char) (i + 55);
            }
        }
//--------------------------------------------------------
public static String getMD5(File file) {
    FileInputStream fileInputStream = null;
    try {
        MessageDigest MD5 = MessageDigest.getInstance("MD5");
        fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[8192*2];
        int length;
        while ((length = fileInputStream.read(buffer)) != -1) {
            MD5.update(buffer, 0, length);
        }
        return new String(Hex.encodeHex(MD5.digest()));
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        try {
            if (fileInputStream != null){
                fileInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    /**
     * 求一个字符串的md5值
     * @param target 字符串
     * @return md5 value
     */
    public static String MD5(String target) {
        return DigestUtils.md5Hex(target);
    }
    @Test
    public  void test() {
        long beginTime = System.currentTimeMillis();
        File file = new File("C:\\DATA\\GL\\树莓立方体\\root.pfs.002");
        String md5 = getMD5(file);
        long endTime = System.currentTimeMillis();
        System.out.println("MD5:" + md5 + "\n 耗时:" + ((endTime - beginTime) / 1000) + "s");
    }

}
