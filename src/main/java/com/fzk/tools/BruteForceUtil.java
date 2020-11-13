package com.fzk.tools;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * BruteForceUtil：暴力破解口令密码生成工具
 *
 * @author: fzk
 * @date: 2020-11-13 09:26
 */
public class BruteForceUtil {

    public static void main(String[] args) {

        //这里以pdf文件口令密码为例，由于文件解析较慢（大概2秒一次），结果集越大消耗时间也会越长
        File file = new File("./test.pdf");
        System.out.println("文件大小：" + file.length() / 1024 / 1024 + "M");

        Set<String> set1 = combinationByLen("208", 4);
        System.out.println("结果集数量：" + set1.size());
        long start = System.currentTimeMillis();
        int i = 0;
        for (String password : set1) {
            i++;
            System.out.println("当前password:" + password);
            try (PDDocument document = PDDocument.load(file, password)) {
                long end = System.currentTimeMillis();
                System.out.println("口令密码破解成功：" + password + ",尝试次数" + i + "，耗时" + (end - start) / 1000d / 60d + "分钟。");
                return;
//                document.getClass();
//                if (!document.isEncrypted())
//                {
//                    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
//                    stripper.setSortByPosition(true);
//                    PDFTextStripper tStripper = new PDFTextStripper();
//                    String pdfFileInText = tStripper.getText(document);
//                    String[] lines = pdfFileInText.split("\\r?\\n");
//                    for (String line : lines)
//                    {
//                        System.out.println(line);
//                    }
//                }
//                else
//                {
//                    System.out.println(1);
//                }
            } catch (InvalidPasswordException e) {
                // e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    /**
     * 获取指定字符串内的所有字符指定长度的所有组合字符串，其中单个字符可重复使用
     * 例如str="abc",len =2,则得到aa,bb,cc,ab,ba。。。以此类推
     *
     * @param str
     */
    public static Set<String> combinationByLen(String str, int targetLen) {
        Set<String> set = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        emu(set, sb, str, targetLen);
        return set;
    }

    /**
     * 递归运算方法
     *
     * @param set
     * @param sb
     * @param s
     * @param targetLen
     */
    public static void emu(Set<String> set, StringBuilder sb, String s, int targetLen) {

        if (sb.length() > targetLen) {
            //目标字符串长度大于指定长度则donothing
        } else if (sb.length() == targetLen) {
            //目标字符串长度等于指定长度，符合要求加入结果集
            set.add(sb.toString());
        } else {
            //目标字符串长度小于指定长度，补齐长度
            for (int i = 0; i < s.length(); i++) {
                sb.append(s.charAt(i));
                emu(set, sb, s, targetLen);
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }


}
