package Musketeers.kz.dao.impl;

import Musketeers.kz.dao.AbstractDao;
import Musketeers.kz.entity.standart.ExpressSurvey;
import Musketeers.kz.entity.standart.Reports;
import Musketeers.kz.utils.Const;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpressSurveyDao extends AbstractDao<ExpressSurvey> {

    public void insert(ExpressSurvey expressSurvey){
        sql = "INSERT INTO "+ Const.TABLE_NAME+ ".EXPRESS_SURVEY (FIRST_QUEST, SECOND_QUEST, THIRD_QUEST, FORTH_QUEST, CHAT_ID, FULL_NAME, DATE) VALUES (?, ?, ?, ?, ?, ?, ?)";
        getJdbcTemplate().update(sql, expressSurvey.getFirst_quest(), expressSurvey.getSecond_quest(), expressSurvey.getThird_quest(), expressSurvey.getForth_quest(), expressSurvey.getChatID(), expressSurvey.getFullName(), expressSurvey.getDate());
    }

    @Override
    protected ExpressSurvey mapper(ResultSet rs, int index) throws SQLException {
        ExpressSurvey expressSurvey = new ExpressSurvey();
        expressSurvey.setID(rs.getInt(1));
        expressSurvey.setFirst_quest(rs.getString(2));
        expressSurvey.setSecond_quest(rs.getString(3));
        expressSurvey.setThird_quest(rs.getString(4));
        expressSurvey.setForth_quest(rs.getString(5));
        expressSurvey.setChatID(rs.getLong(6));
        expressSurvey.setFullName(rs.getString(7));
        expressSurvey.setDate(rs.getDate(8));
        return expressSurvey;
    }
}
