package Musketeers.kz.dao.impl;

import Musketeers.kz.dao.AbstractDao;
import Musketeers.kz.dao.AbstractDao;
import Musketeers.kz.entity.standart.AboutUs;
import Musketeers.kz.utils.Const;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AboutUsDao extends AbstractDao<AboutUs> {

    public List<AboutUs> getAll(String hname) {
        sql = "SELECT * FROM "+ Const.TABLE_NAME+".ABOUT_US_INFO WHERE LANG_ID = ? AND HOST_NAME = ?";
        return getJdbcTemplate().query(sql, setParam(getLanguage().getId(),hname) ,this::mapper);
    }
    public List<AboutUs> getAllbyName(String hname) {
        sql = "SELECT * FROM "+ Const.TABLE_NAME+".ABOUT_US_INFO WHERE LANG_ID = ? AND FULL_NAME = ?";
        return getJdbcTemplate().query(sql, setParam(getLanguage().getId(),hname) ,this::mapper);
    }
    public int getId(String hname) {
        sql = "SELECT ID FROM "+ Const.TABLE_NAME+".ABOUT_US_INFO WHERE LANG_ID = ? AND HOST_NAME = ?";
        return getJdbcTemplate().queryForObject(sql, setParam(getLanguage().getId(),hname) , Integer.class);
    }



    @Override
    protected AboutUs mapper(ResultSet rs, int index) throws SQLException {
        AboutUs aboutUs = new AboutUs();
        aboutUs.setId(rs.getInt(1));
        aboutUs.setName(rs.getString(2));
        aboutUs.setPhone(rs.getString(3));
        aboutUs.setInform(rs.getString(4));
        aboutUs.setHost(rs.getString(5));
        aboutUs.setId(rs.getInt(6));
        aboutUs.setPhoto(rs.getString(7));
        return aboutUs;
    }
}
