package com.company;

import java.io.File;

public class HashedFile {

    File file;
    int hash;

    public HashedFile(String path, int hash) {
        this.file = new File(path);
        this.hash = hash;
    }

    public void delete() {
        try {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }catch (Exception exception) {
            auxiliaryFunctions.showWarr(exception);
        }
        Main.db.deleteByHash(hash);
    }
}
