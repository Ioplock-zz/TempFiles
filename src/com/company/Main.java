package com.company;

public class Main {

    static public Files db = new Files();

    public static void main(String[] args) throws InterruptedException {
        auxiliaryFunctions.init();

        FindingThread thread = new FindingThread();
        thread.start();

        auxiliaryFunctions.showStart();

        while (true) {
            for(HashedFile file : db.getOldFiles()) {
                file.delete();
            }
            //noinspection BusyWait
            Thread.sleep(10000);
        }
    }
}
