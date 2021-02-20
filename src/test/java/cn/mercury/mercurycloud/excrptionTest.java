package cn.mercury.mercurycloud;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author: Mercury-Z
 */
public class excrptionTest {
    public static  void main(String[] args){
//        boolean flag = true;
//        System.out.println("test run");
//        try {
//            if (flag){
//                System.out.println("in the try");
//                throw  new RuntimeException("error");
//            }
//        } catch (Exception e) {
//            System.out.println("进入了catch代码块");
//            e.printStackTrace();
//        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("aaa"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fos !=null){
            try {
                fos.close();
                System.out.println("close 1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (fos !=null){
            try {
                System.out.println("close 2");
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
