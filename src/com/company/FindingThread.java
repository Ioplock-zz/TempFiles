package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class FindingThread extends Thread {

    ArrayList<File> founded_files = new ArrayList<>();

    @Override
    public void run() {
        while (true) {
            scanFolder(new File(System.getProperty("user.home")));
            for (File file : founded_files) {
                System.out.println(file);
                Main.db.insertNewFile(file.toString(), auxiliaryFunctions.getTimeByName(file.getName()), file.hashCode());
            }
            founded_files.clear();
            try {
                //noinspection BusyWait
                Thread.sleep(60000);
            } catch (InterruptedException ignored) {}
        }
    }

    public void scanFolder(File dict) {
        try {
            for (File file : Objects.requireNonNull(dict.listFiles())) {
                if (file.isDirectory()) {
                    if (!file.isHidden() && !file.getName().equals("AppData") && !file.getName().equals("Application Data") && !file.getName().startsWith(".")) {
                        scanFolder(file);
                        if(file.getName().contains("$")) founded_files.add(file);
                    }
                } else {
                    if(file.getName().contains("$")) founded_files.add(file);
                }
            }
        }catch (Exception ignored) {}
    }

}
