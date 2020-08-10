package Musketeers.kz.dao.impl;

import Musketeers.kz.dao.AbstractDao;
import Musketeers.kz.entity.enums.Language;
import Musketeers.kz.entity.standart.Reception;
import Musketeers.kz.utils.Const;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReceptionDao extends AbstractDao<Reception> {

    public void insert(Reception reception){
        sql = "INSERT INTO "+ Const.TABLE_NAME+".RECEPTION (FULL_NAME, HOST, RECEPTION_DAY, CHAT_ID, TEXT) VALUES(?,?,?,?,?)";
        getJdbcTemplate().update(sql, reception.getName(), reception.getHost(), reception.getReception_day(), reception.getChat_id(), reception.getText());

    }



    @Override
    protected Reception mapper(ResultSet rs, int index) throws SQLException {
        Reception reception = new Reception();
        reception.setId(rs.getInt(1));
        reception.setName(rs.getString(2));
        reception.setHost(rs.getString(3));
        reception.setReception_day(rs.getString(4));
        reception.setChat_id(rs.getLong(5));
        reception.setText(rs.getString(6));
        return reception;
    }
}
