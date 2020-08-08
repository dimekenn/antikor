package Musketeers.kz.entity.standart;

import lombok.Data;

import java.util.Date;

@Data
public class ExpressSurvey {
    private int ID;
    private String first_quest;
    private String second_quest;
    private String third_quest;
    private String forth_quest;
    private Long chatID;
    private String fullName;
    private Date date;
}
