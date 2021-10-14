package com.javafx.librarian.utils;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Util {
    public static final String URL_JDBC = "jdbc:mysql://localhost:3306/qltv?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC";
    public static final String USERNAME_JDBC = "root";
    public static final String PASSWORD_JDBC = "root";
    public static final int MAX_CODE = 999999;
    public static final int MIN_CODE = 1;

    public enum PREFIX_CODE {
        S, //SÁCH
        PM,//PHẾU MƯỢN
        PT,//PHIẾU TRẢ
        TP,//PHIẾU PHẠT
        NV,//NHÂN VIÊN
        TL,
        TG,
        DG,
        LDG,
        PTT, //PHIEU THU TIEN
        BCTL,
        BCTT,
    };

    public static String dateFormat(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public static LocalDate convertDateToLocalDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        LocalDate ret = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)+1);
        ret.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return ret;
    }

    public static LocalDate convertDateToLocalDateUI(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        LocalDate ret = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        ret.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return ret;
    }

    //auto generate ID
    public static String generateID(PREFIX_CODE prefix) {
        //get key
        int key = new Random().nextInt((MAX_CODE - MIN_CODE) + 1) + MIN_CODE;
        //generate new format key
        DecimalFormat formatter = new DecimalFormat("000000");

        return  prefix + "_" +formatter.format(key);
    };

    public static void showSuccess(int i, String title ,String s) {
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        tray.setAnimationType(type);
        tray.setTitle(title);
        tray.setMessage(s);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(2000));
    }
}
