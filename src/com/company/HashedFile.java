package com.company;

import java.io.File;
import java.util.Objects;

public class HashedFile {

    File file;
    int id;

    public HashedFile(String path, int id) {
        this.file = new File(path);
        this.id = id;
    }

    public void delete() {
        if(file.isDirectory()) {
                deleteFolder(file);
                if(!file.delete()) {
                    auxiliaryFunctions.showWarr(file);
                }
        } else {
            try {
                if(!file.delete()) {
                    auxiliaryFunctions.showWarr(file);
                }
            } catch (Exception exception) {
                auxiliaryFunctions.showWarr(exception);
            }
        }
        Main.db.deleteByID(id);
    }

    private void deleteFolder(File dict) {
        for(File file : Objects.requireNonNull(dict.listFiles())) {
            if(file.isDirectory()) {
                deleteFolder(file);
            }
            if(!file.delete()) {
                auxiliaryFunctions.showWarr(file);
            }
        }
    }
}
