package Musketeers.kz.services;

import Musketeers.kz.dao.DaoFactory;
import Musketeers.kz.dao.impl.CountHandlingPlanDao;
import Musketeers.kz.dao.impl.PropertiesDao;
import Musketeers.kz.dao.impl.RegistrationHandlingDao;
import Musketeers.kz.dao.impl.UserDao;
import Musketeers.kz.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class WeekService {
    private DaoFactory factory                 = DaoFactory.getInstance();
    private RegistrationHandlingDao registrationHandlingDao = factory.getRegistrationHandlingDao();
    private CountHandlingPlanDao countHandlingPlanDao    = factory.getCountHandlingPlanDao();
    private PropertiesDao propertiesDao           = factory.getPropertiesDao();
    private UserDao userDao                 = factory.getUserDao();
    private XSSFWorkbook workbook                = new XSSFWorkbook();
    private XSSFCellStyle style                   = workbook.createCellStyle();
    private XSSFWorkbook            originWorkbook;
    private Sheet firstOriginSheet;
    private Sheet                   firstSheet;
    private Sheet                   secondOriginSheet;
    private Sheet                   secondSheet;
    private Sheet                   thirdOriginSheet;
    private Sheet                   thirdSheet;

    private Sheet                   fourthOriginSheet;
    private Sheet                   fourthSheet;
    private Sheet                   fifthOriginSheet;
    private Sheet                   fifthSheet;
    private Date                    dateBegin;
    private Date                    dateEnd;


    public  void sendServiceReport  (long chatId, DefaultAbsSender bot, Date dateBegin, Date dateEnd, int messagePrevReport) throws TelegramApiException {
        this.dateBegin = dateBegin;
        this.dateEnd   = dateEnd;
        try {
            sendReport(chatId, bot, messagePrevReport);
        } catch (Exception e) {
            log.error("Can't create/send report", e);
            try {
                bot.execute(new SendMessage(chatId, "Ошибка при создании отчета"));
            } catch (TelegramApiException ex) {
                log.error("Can't send message", ex);
            }
        }
    }

    private void sendReport         (long chatId, DefaultAbsSender bot, int messagePrevReport) throws IOException, TelegramApiException {
        try {
            originWorkbook = new XSSFWorkbook(new FileInputStream(new File(propertiesDao.getPropertiesValue(4))));
        } catch (Exception e) {
            log.error("Can't read file, error: ", e);
        }

        createFirstTitle();
        addFirstPageInfo();

//        createSecondTitle();
//        addSecondPageInfo();

//        createThirdTitle();
//        addThirdPageInfo();

        addInfo();
        sendFile(chatId, bot, dateBegin, dateEnd);
    }


    // Sheet first start
    private void createFirstTitle() {
        firstOriginSheet    = originWorkbook.getSheetAt(3);
        firstSheet          = workbook.createSheet(firstOriginSheet.getSheetName());
        for (int indexRow = 0; indexRow < firstOriginSheet.getLastRowNum() + 1; indexRow++) {
            Row originRow = firstOriginSheet.getRow(indexRow);
            Row row       = firstSheet.createRow(indexRow);
            if (indexRow == 2) row.setHeight((short) 500);
            for (int indexCell = 0; indexCell < 4; indexCell++) {
                setFirstTitleValue(row, indexCell, getTitleValue(originRow, indexCell), indexRow);
            }
        }
    }

    private void addFirstPageInfo() {
        int rowIndex = 4;
        int workSpaceCount       = countHandlingPlanDao.getCountById(29).getCountPeople();
        int businessProjectCount = countHandlingPlanDao.getCountById(30).getCountPeople();
        int workCount            = countHandlingPlanDao.getCountById(31).getCountPeople();
        int businessManCount     = countHandlingPlanDao.getCountById(32).getCountPeople();
        int allCount             = workSpaceCount + businessProjectCount + workCount + businessManCount;
        setFirstTitleValue(firstSheet.getRow(rowIndex++),3, String.valueOf(String.valueOf(workSpaceCount)),      4);
        setFirstTitleValue(firstSheet.getRow(rowIndex++),3, String.valueOf(String.valueOf(businessProjectCount)),5);
        setFirstTitleValue(firstSheet.getRow(rowIndex++),3, String.valueOf(String.valueOf(workCount)),           6);
        setFirstTitleValue(firstSheet.getRow(rowIndex++),3, String.valueOf(String.valueOf(businessManCount)),    7);
        setFirstTitleValue(firstSheet.getRow(rowIndex),  3, String.valueOf(String.valueOf(allCount)),            8);
    }

    private void setFirstTitleValue(Row row, int numberCell, String cellValue, int rowIndex) {
        Cell cell = row.createCell(numberCell);
        if (        rowIndex == 0 && numberCell == 0) {
            cell.setCellStyle(setBoldStyleWithoutTable(14));
        } else if ( rowIndex == 2 && numberCell == 2 || rowIndex == 2 && numberCell == 8 ) {
            cell.setCellStyle(setStyleBold(11 , true));
        } else if ( rowIndex == 3 && numberCell == 0 || rowIndex == 3 && numberCell == 1 || rowIndex == 3 && numberCell == 2 || rowIndex == 3 && numberCell == 3 ||
                    rowIndex == 3 && numberCell == 4 || rowIndex == 3 && numberCell == 5 || rowIndex == 3 && numberCell == 6 || rowIndex == 3 && numberCell == 7 ||
                    rowIndex == 3 && numberCell == 8 || rowIndex == 3 && numberCell == 9 || rowIndex == 3 && numberCell == 10) {
            cell.setCellStyle(setBoldStyleColorBackground(IndexedColors.YELLOW.getIndex(), true));
        } else if ( rowIndex == 4 && numberCell == 2 || rowIndex == 4 && numberCell == 3 ||
                rowIndex == 5 && numberCell == 2 || rowIndex == 5 && numberCell == 3 ||
                rowIndex == 6 && numberCell == 2 || rowIndex == 6 && numberCell == 3 ||
                rowIndex == 7 && numberCell == 2 || rowIndex == 7 && numberCell == 3 ||
                rowIndex == 8 && numberCell == 2 || rowIndex == 8 && numberCell == 3) {
            cell.setCellStyle(setBoldStyleColorBackground(IndexedColors.YELLOW.getIndex(), false));
        } else if ( rowIndex == 3 && numberCell == 0 || rowIndex == 3 && numberCell == 1 ||
                rowIndex == 4 && numberCell == 0 || rowIndex == 4 && numberCell == 1 ||
                rowIndex == 5 && numberCell == 0 || rowIndex == 5 && numberCell == 1 ||
                rowIndex == 6 && numberCell == 0 || rowIndex == 6 && numberCell == 1 ||
                rowIndex == 7 && numberCell == 0 || rowIndex == 7 && numberCell == 1 ||
                rowIndex == 8 && numberCell == 1) {
            cell.setCellStyle(setStyleBold(12, true));
        } else {

        }
        cell.setCellValue(cellValue);
    }
    // Sheet first end


    // Sheet second start
/*    private void createSecondTitle() {
        secondOriginSheet   = originWorkbook.getSheetAt(1);
        secondSheet         = workbook.createSheet(secondOriginSheet.getSheetName());
        for (int indexRow = 0; indexRow < secondOriginSheet.getLastRowNum() + 1; indexRow++) {
            Row originRow = secondOriginSheet.getRow(indexRow);
            Row row       = secondSheet.createRow(indexRow);
            if (indexRow == 2 || indexRow == 3 || indexRow == 4 || indexRow == 16 || indexRow == 17 || indexRow == 20 || indexRow == 21 || indexRow == 25) row.setHeight((short) 550);
            for (int indexCell = 0; indexCell < 4; indexCell++) {
                setSecondTitleValue(row, indexCell, getTitleValue(originRow, indexCell), indexRow);
            }
        }
    }

    private void addSecondPageInfo() {
        setSecondTitleValue(secondSheet.getRow( 6), 3, String.valueOf(factory.getRecipientDao().getCountByTime(dateBegin, dateEnd)),               6);

        setSecondTitleValue(secondSheet.getRow( 7), 3, String.valueOf(factory.getServiceDao().getAll().size()),            7);
        setSecondTitleValue(secondSheet.getRow( 9), 3, String.valueOf(factory.getServiceDao().getAll(2).size()),9);
        setSecondTitleValue(secondSheet.getRow(10), 3, String.valueOf(factory.getServiceDao().getAll(1).size()),10);
        setSecondTitleValue(secondSheet.getRow(11), 3, String.valueOf(factory.getServiceDao().getAll(4).size()),11);
        setSecondTitleValue(secondSheet.getRow(12), 3, String.valueOf(factory.getServiceDao().getAll(3).size()),12);
        setSecondTitleValue(secondSheet.getRow(13), 3, String.valueOf(factory.getServiceDao().getAll(5).size()),13);


        setSecondTitleValue(secondSheet.getRow(15), 3, String.valueOf(registrationHandlingDao.getCountConsultationById(dateBegin, dateEnd, 1)), 15);
        setSecondTitleValue(secondSheet.getRow(16), 3, String.valueOf(registrationHandlingDao.getCountConsultationById(dateBegin, dateEnd, 2)), 16);
        setSecondTitleValue(secondSheet.getRow(17), 3, String.valueOf(registrationHandlingDao.getCountConsultationById(dateBegin, dateEnd, 3)), 17);
        setSecondTitleValue(secondSheet.getRow(18), 3, String.valueOf(registrationHandlingDao.getCountConsultationById(dateBegin, dateEnd, 4)), 18);
        setSecondTitleValue(secondSheet.getRow(19), 3, String.valueOf(registrationHandlingDao.getCountConsultationById(dateBegin, dateEnd, 5)), 19);
        setSecondTitleValue(secondSheet.getRow(20), 3, String.valueOf(registrationHandlingDao.getCountConsultationById(dateBegin, dateEnd, 6)), 20);
        setSecondTitleValue(secondSheet.getRow(21), 3, String.valueOf(registrationHandlingDao.getCountConsultationById(dateBegin, dateEnd, 7)), 21);

        setSecondTitleValue(secondSheet.getRow(23), 3, String.valueOf(registrationHandlingDao.getCountConsultationById(dateBegin, dateEnd, 8)), 23);
        setSecondTitleValue(secondSheet.getRow(24), 3, String.valueOf(registrationHandlingDao.getCountConsultationById(dateBegin, dateEnd, 9)), 24);
        setSecondTitleValue(secondSheet.getRow(25), 3, String.valueOf(registrationHandlingDao.getCountConsultationById(dateBegin, dateEnd, 10)),25);
        setSecondTitleValue(secondSheet.getRow(26), 3, String.valueOf(registrationHandlingDao.getCountConsultationById(dateBegin, dateEnd, 11)),26);
    }

    private void setSecondTitleValue(Row row, int numberCell, String cellValue, int rowIndex) {
        Cell cell = row.createCell(numberCell);
        if         (rowIndex ==  0 && numberCell == 1) {
            cell.setCellStyle(setBoldStyleWithoutTable(14));
        } else if  (rowIndex ==  2 && numberCell == 1 || rowIndex ==  3 && numberCell == 0 || rowIndex == 3 && numberCell == 1 ||
                rowIndex ==  5 && numberCell == 0 || rowIndex ==  5 && numberCell == 1 ||
                rowIndex ==  8 && numberCell == 0 || rowIndex ==  8 && numberCell == 1 ||
                rowIndex == 27 && numberCell == 1) {
            cell.setCellStyle(setStyleBold(12, true));
        } else if  (rowIndex >=  2 && numberCell == 2 || rowIndex >=  2 && numberCell == 3) {
            cell.setCellStyle(setBoldStyleColorBackground(IndexedColors.YELLOW.getIndex(), true));
        } else if  (rowIndex ==  4 && numberCell == 0 || rowIndex ==  4 && numberCell == 1 ||
                rowIndex == 14 && numberCell == 0 || rowIndex == 14 && numberCell == 1 ||
                rowIndex == 22 && numberCell == 0 || rowIndex == 22 && numberCell == 1 ||
                rowIndex == 26 && numberCell == 0 || rowIndex == 26 && numberCell == 1) {
            cell.setCellStyle(setBoldStyleColorBackground(IndexedColors.GREY_40_PERCENT.getIndex(), true));
        } else {
            cell.setCellStyle(setStyleBold(12, false));
        }
        cell.setCellValue(cellValue);
    }*/
    // Sheet second end

    private void            addInfo() {
        int cellIndex = 0;

        firstSheet .addMergedRegion(new CellRangeAddress(0,0,0, 13));
        firstSheet .addMergedRegion(new CellRangeAddress(2,2,2, 7));

        secondSheet.addMergedRegion(new CellRangeAddress(0,0,0, 3));
        secondSheet.addMergedRegion(new CellRangeAddress(2,2,2, 3));

//        thirdSheet .addMergedRegion(new CellRangeAddress(1,1,0, 3));
//        thirdSheet .addMergedRegion(new CellRangeAddress(2,2,2, 3));

        for (cellIndex = 0; cellIndex < 4; cellIndex++) {
            firstSheet .autoSizeColumn(cellIndex);
            secondSheet.autoSizeColumn(cellIndex);
//            thirdSheet .autoSizeColumn(cellIndex);
        }
    }

    private String          getTitleValue(Row row, int numberCell) {
        Cell cell = row.getCell(numberCell);
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    private void            sendFile(long chatId, DefaultAbsSender bot, Date dateBegin, Date dateEnd) throws IOException, TelegramApiException {
        String fileName = "Болванка за: " + DateUtil.getDayDate(dateBegin) + " - " + DateUtil.getDayDate(dateEnd) + ".xlsx";
        String path     = "C:\\test\\" + fileName;
        path           += new Date().getTime();
        try (FileOutputStream stream = new FileOutputStream(path)) {
            workbook.write(stream);
        } catch (IOException e) {
            log.error("Can't send file error: ", e);
        }
        sendFile(chatId, bot, fileName, path);
    }

    private void            sendFile(long chatId, DefaultAbsSender bot, String fileName, String path) throws TelegramApiException, IOException {
        File file = new File(path);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            bot.execute(new SendDocument().setChatId(chatId).setDocument(fileName, fileInputStream));
        }
        file.delete();
    }

    // Style Cell/Row start
    private XSSFCellStyle   setBoldStyleWithoutTable(int fontSize) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font           = workbook.createFont();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        font     .setFontName("Times New Roman");
        font     .setFontHeight(fontSize);
        font     .setBold(true);
        cellStyle.setFont(font);
        return cellStyle;
    }

    private XSSFCellStyle   setStyleBoldVertical(int size) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle .setRotation((short)90);
        XSSFFont font           = workbook.createFont();
        BorderStyle title       = BorderStyle.THIN;
        cellStyle.setBorderTop   (title);
        cellStyle.setBorderBottom(title);
        cellStyle.setBorderRight (title);
        cellStyle.setBorderLeft  (title);
        cellStyle.setTopBorderColor   (IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor (IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor  (IndexedColors.BLACK.getIndex());
        font     .setFontName("Times New Roman");
        font     .setFontHeight(size);
        font     .setBold(true);
        cellStyle.setFont(font);
        return cellStyle;
    }

    private XSSFCellStyle   setStyleBold(int size, boolean bold) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font           = workbook.createFont();
        BorderStyle title       = BorderStyle.THIN;
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderTop   (title);
        cellStyle.setBorderBottom(title);
        cellStyle.setBorderRight (title);
        cellStyle.setBorderLeft  (title);
        cellStyle.setTopBorderColor   (IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor (IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor  (IndexedColors.BLACK.getIndex());
        font     .setFontName("Times New Roman");
        font     .setFontHeight(size);
        if (bold) font     .setBold(true);
        cellStyle.setFont(font);
        return cellStyle;
    }

    private XSSFCellStyle   setBoldStyleColorBackground(short colorIndex, boolean isBold) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font           = workbook.createFont();
        BorderStyle title       = BorderStyle.THIN;
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderTop   (title);
        cellStyle.setBorderBottom(title);
        cellStyle.setBorderRight (title);
        cellStyle.setBorderLeft  (title);
        cellStyle.setTopBorderColor     (IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor   (IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor  (IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor    (IndexedColors.BLACK.getIndex());
        cellStyle.setFillForegroundColor(colorIndex);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font     .setFontName("Times New Roman");
        font     .setFontHeight(12);
        if (isBold) font.setBold(true);
        cellStyle.setFont(font);
        return cellStyle;
    }
    // Style Cell/Row end
}
