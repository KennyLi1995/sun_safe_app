package com.example.sun_safe_app.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DateTest {
    private static final int FIRST_DAY = Calendar.MONDAY;
    public static void main(String[] args) {
        List bb=dates();
        for (Object object : bb) {
            System.out.println(object);
        }
    }
    private static List dates() {
        List aa=new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd EE");
        aa.add(dateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DATE, 1);

        return aa;
    }

}
