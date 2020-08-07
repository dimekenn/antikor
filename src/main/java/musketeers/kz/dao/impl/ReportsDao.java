package Musketeers.kz.dao.impl;

import Musketeers.kz.dao.AbstractDao;
import Musketeers.kz.entity.standart.Reports;
import Musketeers.kz.utils.Const;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportsDao extends AbstractDao<Reports> {

    public void insert(Reports reports){
        sql = "INSERT INTO "+ Const.TABLE_NAME+ ".REPORTS (REPORT_TEXT, PHOTO, VIDEO, LOCATION, iS_DONE, CHAT_ID, FULL_NAME, CATEGORIES) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        getJdbcTemplate().update(sql, reports.getReportText(), reports.getPhoto(), reports.getVideo(), reports.getLocation(), reports.getIsDone(), reports.getChatID(), reports.getFullName(), reports.getCategoryName());
    }

    @Override
    protected Reports mapper(ResultSet rs, int index) throws SQLException {
        Reports reports = new Reports();
        reports.setReportText(rs.getString(2));
        reports.setPhoto(rs.getString(3));
        reports.setVideo(rs.getString(4));
        reports.setLocation(rs.getString(5));
        reports.setIsDone(rs.getInt(6));
        reports.setChatID(rs.getLong(7));
        reports.setFullName(rs.getString(8));
        reports.setCategoryName(rs.getString(9));
        return reports;
    }
}
