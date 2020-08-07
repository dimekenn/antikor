package Musketeers.kz.dao.impl;

import Musketeers.kz.dao.AbstractDao;
import Musketeers.kz.entity.custom.File;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FileDao extends AbstractDao<File> {

    public void insertDoc(String path, int taskId) {
        sql = "INSERT INTO FILE (NAME, ID_TASK) VALUES(? ,?)";
        getJdbcTemplate().update(sql, path, taskId);
    }

    public void insertAudio(String path, int taskId) {
        sql = "INSERT INTO FILE (TYPE_ID, ID_TASK) VALUES(? ,?)";
        getJdbcTemplate().update(sql, path, taskId);
    }

    public void insertImg(String path, int taskId) {
        sql = "INSERT INTO FILE (LINK, ID_TASK) VALUES (?,?)";
        getJdbcTemplate().update(sql, path, taskId);
    }

//    public void insertImgFromUser(String path, int taskId) {
//        sql = "INSERT INTO FILE_FROM_USERS (IMG, ID_TASK) VALUES (?,?)";
//        getJdbcTemplate().update(sql, path, taskId);
//    }

    public void insertVideo(String path, int taskId) {
        sql = "INSERT INTO FILE (VIDEO, ID_TASK) VALUES (?,?)";
        getJdbcTemplate().update(sql, path, taskId);
    }

//    public void insertVideoFromUsers(String path, int taskId) {
//        sql = "INSERT INTO FILE_FROM_USERS (VIDEO, ID_TASK) VALUES (?,?)";
//        getJdbcTemplate().update(sql, path, taskId);
//    }
//
//    public void updateLocation(int taskId, String location) {
//        sql = "UPDATE FILE SET LOCATION = ? WHERE ID_TASK = ?";
//        getJdbcTemplate().update(sql, location, taskId);
//    }
//
//    public void updateImg(String location, int taskId) {
//        sql = "UPDATE FILE SET LINK = ? WHERE ID_TASK = ?";
//        getJdbcTemplate().update(sql, location, taskId);
//    }

//    public void updateImgFU(String location, int taskId) {
//        sql = "UPDATE FILE_FROM_USERS SET IMG = ? WHERE ID_TASK = ?";
//        getJdbcTemplate().update(sql, location, taskId);
//    }

//    public void updateLocationFromUsers(int taskId, String location) {
//        sql = "UPDATE FILE_FROM_USERS SET LOCATION = ? WHERE ID_TASK = ?";
//        getJdbcTemplate().update(sql, location, taskId);
//    }

    public void updateStatus(int taskId, boolean idStatus) {
        sql = "UPDATE FILE SET DONE = ? WHERE ID = ?";
        getJdbcTemplate().update(sql, idStatus, taskId);
    }

//    public void updateStatusFU(int taskId, boolean idStatus) {
//        sql = "UPDATE FILE_FROM_USERS SET DONE = ? WHERE ID = ?";
//        getJdbcTemplate().update(sql, idStatus, taskId);
//    }

    public List<File> getFilesList(int taskId) {
        sql = "SELECT * FROM FILE WHERE ID_TASK = ?";
        return getJdbcTemplate().query(sql, setParam(taskId), this::mapper);
    }

//    public List<File> getFilesListFromUsers(int taskId) {
//        sql = "SELECT * FROM FILE_FROM_USERS WHERE ID_TASK = ?";
//        return getJdbcTemplate().query(sql, setParam(taskId), this::mapper);
//    }
//
//    public File getFileFromUsers(int taskId) {
//        sql = "SELECT * FROM FILE_FROM_USERS WHERE ID_TASK = ?";
//        return getJdbcTemplate().queryForObject(sql, setParam(taskId), this::mapper);
//    }
//
//    public File getFileFromUsersNotNull(int taskId) {
//        sql = "SELECT * FROM FILE_FROM_USERS WHERE ID_TASK = ? AND LOCATION IS NOT NULL";
//        return getJdbcTemplate().queryForObject(sql, setParam(taskId), this::mapper);
//    }
//
//    public File getFile(int taskId) {
//        sql = "SELECT * FROM FILE WHERE ID_TASK = ?";
//        return getJdbcTemplate().queryForObject(sql, setParam(taskId), this::mapper);
//    }
//
//    public File getFileNotNull(int taskId) throws CommandNotFoundException {
//        sql = "SELECT * FROM FILE WHERE ID_TASK = ? AND LOCATION IS NOT NULL";
//        try {
//            return getJdbcTemplate().queryForObject(sql, setParam(taskId), this::mapper);
//        } catch (Exception e) {
//            if (e.getMessage().contains("Incorrect result size: expected 1, actual 0")) {
//                throw new CommandNotFoundException(e);
//            }
//            throw e;
//        }
//    }
//
//    public List<File> getFilesListWhereLocationNull(int taskId) {
//        sql = "SELECT * FROM FILE WHERE ID_TASK = ? AND LOCATION = NULL";
//        return getJdbcTemplate().query(sql, setParam(taskId), this::mapper);
//    }
//
//    public List<File> getFilesListWhereLocationNullFU(int taskId) {
//        sql = "SELECT * FROM FILE_FROM_USERS WHERE ID_TASK = ? AND LOCATION = NULL";
//        return getJdbcTemplate().query(sql, setParam(taskId), this::mapper);
//    }

    @Override
    protected File mapper(ResultSet rs, int index) throws SQLException {
        File file = new File();
        file.setId(rs.getInt(1));
        file.setImg(rs.getString(2));
        file.setAudio(rs.getString(3));
        file.setDoc(rs.getString(4));
        file.setVideo(rs.getString(5));
        file.setIdTask(rs.getInt(6));
        return file;
    }
}
