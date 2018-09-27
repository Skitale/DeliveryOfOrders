package com.training.task2.load;

import com.training.task2.structures.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {


    public static Model parse(File file) {
        Model model = null;
        try {
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            Scanner s = new Scanner(r).useDelimiter("(\r\n)");
            int n = -1;
            if (s.hasNextInt()) {
                n = s.nextInt();
            }
            model = new Model(file.getName(), n);

            s.useDelimiter("(\r\n)|( )");
            for (int i = 1; i <= n; i++) {
                if (s.hasNextInt()) {
                    model.setTd(i, s.nextInt());
                } else {
                    s.next();
                }
            }
            s.useDelimiter("(\n)|(\t\n)|(\t)");
            for (int i = 0; i < n + 1; i++) {
                for (int j = 0; j < n + 1; j++) {
                    if (hasNextIntElseNext(s)) {
                        model.setT(i, j, s.nextInt());
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return model;
    }

    public static List<Model> parseFolderWithFiles(String pathToFolder) {
        File folder = new File(pathToFolder);
        File[] files = folder.listFiles();
        List<Model> modelList = new ArrayList<>();
        assert files != null;
        for (File file : files) {
            modelList.add(parse(file));
        }
        return modelList;
    }

    private static boolean hasNextIntElseNext(Scanner s) {
        if (!s.hasNextInt()) {
            s.next();
        }
        return true;
    }
}
