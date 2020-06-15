package com.company;

import java.io.File;
import java.util.Objects;

public class HashedFile {

    File file;
    int hash;

    public HashedFile(String path, int hash) {
        this.file = new File(path);
        this.hash = hash;
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
        Main.db.deleteByHash(hash);
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
