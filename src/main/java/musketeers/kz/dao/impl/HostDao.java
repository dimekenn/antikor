package Musketeers.kz.dao.impl;

import Musketeers.kz.dao.AbstractDao;
import Musketeers.kz.entity.enums.Language;
import Musketeers.kz.utils.Const;
import Musketeers.kz.entity.standart.Host;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HostDao extends AbstractDao<Host> {

    public List<Host> getAll() {
        sql = "SELECT * FROM "+ Const.TABLE_NAME+".HOST WHERE LANG_ID = ?";
        return getJdbcTemplate().query(sql, setParam(getLanguage().getId()) ,this::mapper);
    }
    public int getIDbyName(String name){
        sql = "SELECT ID FROM "+ Const.TABLE_NAME+".HOST WHERE NAME = ?";
        return getJdbcTemplate().queryForObject(sql, setParam(name) ,Integer.class);
    }


    @Override
    protected Host mapper(ResultSet rs, int index) throws SQLException {
        Host host = new Host();
        host.setId(rs.getInt(1));
        host.setName(rs.getString(2));
        host.setLang(rs.getInt(3));
        return host;
    }
}
