package Musketeers.kz.entity.standart;

import lombok.Data;

import java.util.Date;

@Data
public class Reception {
    private int id;
    private String name;
    private String host;
    private String reception_day;
    private long chat_id;
    private String text;

}
