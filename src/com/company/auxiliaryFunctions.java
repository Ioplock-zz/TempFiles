package com.company;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class auxiliaryFunctions {

    static ArrayList<Character> allow_chars = new ArrayList<>();

    static public void init() {
        allow_chars.add('1');
        allow_chars.add('2');
        allow_chars.add('3');
        allow_chars.add('4');
        allow_chars.add('5');
        allow_chars.add('6');
        allow_chars.add('7');
        allow_chars.add('8');
        allow_chars.add('9');
        allow_chars.add('0');
        allow_chars.add('d');
        allow_chars.add('h');
        allow_chars.add('m');
        allow_chars.add('s');
        allow_chars.add('.');
    }

    static public int getTimeByName(String filename) {
        if(filename.split("\\$").length > 1) {
            return parseTime(filename.split("\\$")[1]);
        } else return 0;
    }

    static private int parseTime(String time) {
        int secs = 0;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < time.length(); i++) {
            char character = time.charAt(i);

            if(!allow_chars.contains(character)) return 0;
            if(character == '.') break;

            if(Character.isDigit(character)) {
                number.append(character);
            } else if(Character.isLetter(character)) {
                switch (character) {
                    case 'd':
                        secs += Integer.parseInt(number.toString()) * 86400;
                        number = new StringBuilder();
                        break;
                    case 'h':
                        secs += Integer.parseInt(number.toString()) * 3600;
                        number = new StringBuilder();
                        break;
                    case 'm':
                        secs += Integer.parseInt(number.toString()) * 60;
                        number = new StringBuilder();
                        break;
                    case 's':
                        secs += Integer.parseInt(number.toString());
                        number = new StringBuilder();
                        break;
                }
            } else {
                return 0;
            }
        }
        return secs == 0 ? 0 : (int) (System.currentTimeMillis() / 1000) + secs;
    }

    static public void showErr(Exception ex) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                "This is output, You can show it to me. When I know to fix everything in my program\n" + ex.getMessage(),
                "Had some error",
                JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }

    public static void showWarr(Exception ex) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                "Program can't find file or something else, show it to me and I say what go wrong. When I know to fix everything in my program\n" + ex.getMessage(),
                "Had some warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void showWarr(File file) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                "Can't delete following file\n" + file.toString(),
                "Had some warning",
                JOptionPane.WARNING_MESSAGE);
    }
}
