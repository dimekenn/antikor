package Musketeers.kz.dao.impl;

import Musketeers.kz.dao.AbstractDao;
import Musketeers.kz.entity.standart.CitizenReceptionTime;
import Musketeers.kz.utils.Const;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReceptionTimeDao extends AbstractDao<CitizenReceptionTime> {

    public List<CitizenReceptionTime> getAll(int host_id) {
        sql = "SELECT * FROM "+ Const.TABLE_NAME+".CITIZEN_RECEPTION_TIME WHERE LANG_ID = ? AND HOST_ID = ?";
        return getJdbcTemplate().query(sql, setParam(getLanguage().getId(),host_id) ,this::mapper);
    }

    @Override
    protected CitizenReceptionTime mapper(ResultSet rs, int index) throws SQLException {
        CitizenReceptionTime citizenReceptionTime = new CitizenReceptionTime();
        citizenReceptionTime.setId(rs.getInt(1));
        citizenReceptionTime.setTime(rs.getString(2));
        citizenReceptionTime.setHost_id(rs.getLong(3));
        citizenReceptionTime.setLang_id(rs.getInt(4));
        return citizenReceptionTime;
    }
}
