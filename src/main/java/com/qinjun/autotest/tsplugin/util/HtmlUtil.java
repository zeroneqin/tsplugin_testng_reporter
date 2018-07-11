package com.qinjun.autotest.tsplugin.util;

import org.dom4j.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class HtmlUtil {
    public static String saveAsHtml(Element root, String outDir, String fileName) {
        try {
             new File(outDir).mkdirs();
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(outDir,fileName))));
            pw.println("<!doctype html>");
            pw.println(root.asXML());
            pw.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return outDir + File.separator + fileName;
    }
}
