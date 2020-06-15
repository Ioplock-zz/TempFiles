package com.company;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Files {

    Connection conn;
    public ArrayList<Integer> hashes = new ArrayList<>();

    public Files() {
        String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\files.db";

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Success connected to database");
            String sql = "CREATE TABLE IF NOT EXISTS `files` (\n" +
                    "\t`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t`path` TEXT NOT NULL DEFAULT '',\n" +
                    "\t`time` INT NOT NULL DEFAULT '',\n" +
                    "\t`hash` INT NOT NULL DEFAULT ''\n" +
                    ");";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException exception) {
            auxiliaryFunctions.showErr(exception);
        }
        getHashes();
    }

    public void deleteByID(int id) {
        String sql = "DELETE FROM files WHERE id = ?";
        try{
            PreparedStatement psst = conn.prepareStatement(sql);
            psst.setInt(1, id);
            psst.executeUpdate();
        } catch (SQLException exception) {
            auxiliaryFunctions.showErr(exception);
        }
        getHashes();
    }

    public void insertNewFile(String path, int time, int hash) {
        if(time == 0 || hashes.contains(hash)) return;
        String sql = "INSERT INTO files(path,time,hash) VALUES(?,?,?)";
        try {
            PreparedStatement psst = conn.prepareStatement(sql);
            psst.setString(1, path);
            psst.setInt(2, time);
            psst.setInt(3, hash);
            psst.executeUpdate();
            hashes.add(hash);
        } catch (SQLException exception) {
            auxiliaryFunctions.showErr(exception);
        }
    }

    private void getHashes() {
        hashes.clear();
        String sql = "SELECT hash FROM files;";
        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                hashes.add(rs.getInt("hash"));
            }
        } catch (SQLException exception) {
            auxiliaryFunctions.showErr(exception);
        }
    }

    public ArrayList<HashedFile> getOldFiles() {
        ArrayList<HashedFile> files = new ArrayList<>();
        String sql = "SELECT path, id FROM files WHERE time < ?;"; //TODO: Получать hash для дальнейшего удаления записи из таблицы
        try {
            PreparedStatement stmt  = conn.prepareStatement(sql);
            stmt.setInt(1, (int) (System.currentTimeMillis() / 1000));
            ResultSet rs  = stmt.executeQuery();

            while (rs.next()) {
                files.add(new HashedFile(rs.getString("path"), rs.getInt("id")));
            }
        } catch (SQLException exception) {
            auxiliaryFunctions.showErr(exception);
        }
        return files;
    }
}
