package Musketeers.kz.dao.impl;

import Musketeers.kz.dao.AbstractDao;
import Musketeers.kz.entity.standart.Categories;
import Musketeers.kz.utils.Const;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoriesDao extends AbstractDao<Categories> {

    public List<Categories> getAll() {
        sql = "SELECT * FROM "+Const.TABLE_NAME+".CATEGORIES WHERE LANG_ID = ?";
        return getJdbcTemplate().query(sql, setParam(getLanguage().getId()) ,this::mapper);
    }



    @Override
    protected Categories mapper(ResultSet rs, int index) throws SQLException {
        Categories categories = new Categories();
        categories.setId(rs.getInt(1));
        categories.setName(rs.getString(2));
        categories.setLang(rs.getInt(3));
        return categories;
    }
}
