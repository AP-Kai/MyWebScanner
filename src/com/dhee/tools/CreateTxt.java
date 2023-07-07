package com.dhee.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CreateTxt {
    public static void createtxt(ArrayList<String> results) {
        // 文件路径和名称
        String filePath = "D:/document.txt";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("检测报告： " + "\n");
            // 写入 results 内容
            for (String result : results) {
                writer.write(result + "\n");
            }
            System.out.println("Text document created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}