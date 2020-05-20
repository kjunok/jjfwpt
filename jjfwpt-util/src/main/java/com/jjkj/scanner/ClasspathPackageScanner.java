package com.jjkj.scanner;

import java.io.File;
import java.io.IOException;

public class ClasspathPackageScanner {
    public static void main(String[] args) throws IOException {
        String defaultClassPath = ClasspathPackageScanner.class.getResource("/").getPath();
        File file =  new File(defaultClassPath+"com/jjkj/api");
        if (file.isDirectory()) {
            for (File f1 : file.listFiles()) {
                System.out.println(f1.getName());
            }
        }
    }
}