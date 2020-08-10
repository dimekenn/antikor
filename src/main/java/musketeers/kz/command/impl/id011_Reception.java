package Musketeers.kz.command.impl;

import Musketeers.kz.command.Command;
import Musketeers.kz.dao.impl.HostDao;
import Musketeers.kz.dao.impl.ReceptionDao;
import Musketeers.kz.entity.enums.Language;
import Musketeers.kz.entity.enums.WaitingType;
import Musketeers.kz.entity.standart.*;
import Musketeers.kz.utils.ButtonsLeaf;
import Musketeers.kz.utils.Const;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class id011_Reception extends Command {

    private List<Host> receptions;
    private List<CitizenReceptionTime> times;
    private ButtonsLeaf buttonsLeaf;
    private int deleteMessageId;
    private ReceptionDao receptionDao = new ReceptionDao();
    private Reception reception = new Reception();
    private Language currentLanguage;

    @Override
    public boolean execute() throws TelegramApiException {
        switch (waitingType) {
            case START:
                deleteMessage(updateMessageId);
                receptions = factory.getHostDao().getAll();
                if (receptions == null || receptions.size() == 0) {
                    deleteMessageId = sendMessage("ERROR");
                    return EXIT;
                }
                List<String> list = new ArrayList<>();
                receptions.forEach((e) -> list.add(e.getName()));
                buttonsLeaf = new ButtonsLeaf(list);
                toDeleteKeyboard(sendMessageWithKeyboard(getText(Const.HOST_CHOOSE), buttonsLeaf.getListButton()));

                waitingType = WaitingType.HOST_SELECTION;
                return COMEBACK;
            case HOST_SELECTION:
                deleteMessage(updateMessageId);
                if (hasCallbackQuery()) {

                    String hostname = receptions.get(Integer.parseInt(updateMessageText)).getName();
                    reception.setHost(hostname);
                    reception.setChat_id(chatId);
                    reception.setName(userDao.getUserByChatId(chatId).getFullName());
                    times = factory.getReceptionTimeDao().getAll(hostDao.getIDbyName(hostname));
                    if (times == null || times.size() == 0) {
                        deleteMessageId = sendMessage("ERROR");
                        return EXIT;
                    }
                    List<String> list1 = new ArrayList<>();
                    times.forEach((e) -> list1.add(e.getTime()));
                    buttonsLeaf = new ButtonsLeaf(list1);
                    toDeleteKeyboard(sendMessageWithKeyboard(getText(Const.HOST_CHOOSE), buttonsLeaf.getListButton()));


                    sendMessage(getText(Const.TIME_CHOOSE));
                    waitingType = WaitingType.HOST_TIME_SELECTION;
                }

                return COMEBACK;

            case HOST_TIME_SELECTION:
                deleteMessage(updateMessageId);

                if(hasCallbackQuery()){
                    String time = times.get(Integer.parseInt(updateMessageText)).getTime();
                    reception.setReception_day(time);
                    reception.setName(userDao.getUserByChatId(chatId).getFullName());
                    sendMessage(1201);
                }
                sendMessage(Const.WRITE_REPORT);
                waitingType = WaitingType.REPORT_TEXT;
                return COMEBACK;


            case REPORT_TEXT:
                deleteMessage(updateMessageId);

                if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().length() <= 50) {
                    reception.setText(update.getMessage().getText());
                    sendMessage(getText(Const.SIGN));
                }
                receptionDao.insert(reception);
                return COMEBACK;

        }

        return EXIT;
    }

}
