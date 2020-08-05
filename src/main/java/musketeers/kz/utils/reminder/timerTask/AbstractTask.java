package Musketeers.kz.utils.reminder.timerTask;

import Musketeers.kz.config.Bot;
import Musketeers.kz.utils.reminder.Reminder;
import Musketeers.kz.dao.DaoFactory;
import Musketeers.kz.dao.impl.ReminderTaskDao;
import Musketeers.kz.dao.impl.UserDao;

import java.util.TimerTask;

public abstract class AbstractTask extends TimerTask {

    protected Bot bot;
    protected Reminder reminder;
    protected DaoFactory        daoFactory;
    protected ReminderTaskDao   reminderTaskDao;
    protected UserDao           userDao;

    public AbstractTask(Bot bot, Reminder reminder) {
        this.bot      = bot;
        this.reminder = reminder;
    }
}
