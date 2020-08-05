package Musketeers.kz.dao.impl;

import Musketeers.kz.utils.Const;
import Musketeers.kz.dao.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertiesDao extends AbstractDao<String> {

    public String getPropertiesValue(int id) {
        sql = "SELECT VALUE_1 FROM " + Const.TABLE_NAME + ".PROPERTIES WHERE ID = ?";
        return getJdbcTemplate().queryForObject(sql, setParam(id), String.class);
    }

    @Override
    protected String mapper(ResultSet rs, int index) throws SQLException {
        return rs.getString(1);
    }
}
