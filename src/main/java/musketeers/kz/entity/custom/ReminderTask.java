package Musketeers.kz.entity.custom;

import lombok.Data;

import java.util.Date;

@Data
public class ReminderTask {

    private int     id;
    private String  text;
    private Date    dateBegin;
}
