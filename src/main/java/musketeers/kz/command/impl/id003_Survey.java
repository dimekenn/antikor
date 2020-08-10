package Musketeers.kz.command.impl;

import Musketeers.kz.command.Command;
import Musketeers.kz.dao.impl.ExpressSurveyDao;
import Musketeers.kz.entity.enums.WaitingType;
import Musketeers.kz.entity.standart.ExpressSurvey;
import Musketeers.kz.utils.Const;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Date;

public class id003_Survey extends Command {

    private ExpressSurvey expressSurvey = new ExpressSurvey();
    private ExpressSurveyDao expressSurveyDao = factory.getExpressSurveyDAO();

    @Override
    public boolean execute() throws TelegramApiException {
        switch (waitingType){
            case START:
                sendMessageWithKeyboard(getText(Const.SURVEY_FIRST_QUEST), Const.SURVEY_FIRST_KEYBOARD);
                waitingType = WaitingType.FIRST_SURVEY;
                return COMEBACK;
            case FIRST_SURVEY:
                if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().length() <= 50) {
                    expressSurvey.setFirst_quest(update.getMessage().getText());
                }
                expressSurvey.setChatID(chatId);
                expressSurvey.setFullName(userDao.getUserByChatId(chatId).getFullName());
                expressSurvey.setDate(new Date());
                sendMessageWithKeyboard(getText(Const.SURVEY_SECOND_QUEST), Const.SURVEY_SECOND_KEYBOARD);
                waitingType = WaitingType.SECOND_SURVEY;
                return COMEBACK;
            case SECOND_SURVEY:
                if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().length() <= 50) {
                    expressSurvey.setSecond_quest(update.getMessage().getText());
                }
                sendMessageWithKeyboard(getText(Const.SURVEY_THIRD_QUEST), Const.SURVEY_THIRD_KEYBOARD);
                waitingType = WaitingType.THIRD_SURVEY;
                return COMEBACK;
            case THIRD_SURVEY:
                if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().length() <= 50) {
                    expressSurvey.setThird_quest(update.getMessage().getText());
                }
                sendMessageWithKeyboard(getText(Const.SURVEY_FORTH_QUEST), Const.SURVEY_FORTH_KEYBOARD);
                waitingType = WaitingType.FORTH_SURVEY;
                return COMEBACK;
            case FORTH_SURVEY:
                if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().length() <= 50) {
                    expressSurvey.setForth_quest(update.getMessage().getText());
                }
                expressSurveyDao.insert(expressSurvey);
                sendMessageWithKeyboard(getText(Const.SURVEY_FINISH), Const.MAIN_MENU);
                return COMEBACK;

        }
        return EXIT;
    }
}
