package Musketeers.kz.dao;

import Musketeers.kz.config.Conversation;
import Musketeers.kz.entity.enums.Language;
import Musketeers.kz.services.LanguageService;
import Musketeers.kz.utils.Const;
import Musketeers.kz.utils.DataBaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public abstract class AbstractDao<T> {

    protected String sql;
    protected static DaoFactory factory = DaoFactory.getInstance();

    public int getNextId(String tableNames) {
        sql = "SELECT MAX(ID) FROM " + Const.TABLE_NAME + "." + tableNames;
        try {
            return getJdbcTemplate().queryForObject(sql, Integer.class) + 1;
        } catch (Exception e) {
            log.info("getNextId for {} has exception, return id = 1", tableNames);
            log.error("getNextId:", e);
            return 1;
        }
    }

    protected Object[] setParam(Object... args) {
        return args;
    }

    protected abstract T mapper(ResultSet rs, int index) throws SQLException;

    protected static JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    protected Language getLanguage() {
        if (getChatId() == 0) return Language.ru;
        return LanguageService.getLanguage(getChatId());
    }

    protected static DataBaseUtils getDBUtils() {
        return new DataBaseUtils(DaoFactory.getDataSource());
    }

    private static DataSource getDataSource() {
        return DaoFactory.getDataSource();
    }

    private long getChatId() {
        return Conversation.getCurrentChatId();
    }

}
