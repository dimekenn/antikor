package Musketeers.kz.utils.reminder.timerTask;

import Musketeers.kz.config.Bot;
import Musketeers.kz.entity.custom.ReminderTask;
import Musketeers.kz.entity.standart.User;
import Musketeers.kz.utils.DateUtil;
import Musketeers.kz.utils.reminder.Reminder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Date;
import java.util.List;

public class Task extends AbstractTask {

    private List<User>          users;
    private List<ReminderTask>  tasks;
    private Date                start;

    public Task(Bot bot, Reminder reminder) {
        super(bot, reminder);
    }

    @Override
    public  void run() {
        users           = userDao.getAll();
        start           = new Date();
        Date dateEnd    = new Date();
        dateEnd.setDate(dateEnd.getDay() + 1);
        tasks           = reminderTaskDao.getByTime(start, dateEnd);
        checkMessage();
        reminder.setMorningTask(17);
    }

    private void checkMessage() {
        if (tasks != null && tasks.size() != 0) users.forEach(this::sendMessage);
    }

    private void sendMessage(User user) {
        tasks.forEach((e) -> {
            if (DateUtil.getDayDate(e.getDateBegin()).equals(DateUtil.getDayDate(start))) {
                try {
                    bot.execute(new SendMessage().setChatId(user.getChatId()).setText(e.getText()));
                } catch (TelegramApiException ex) {}
            }
        });
    }
}
