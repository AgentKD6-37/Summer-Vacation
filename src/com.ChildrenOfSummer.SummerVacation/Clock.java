package com.ChildrenOfSummer.SummerVacation;

import java.util.Calendar;
import java.util.Date;

class Clock {

    private static int NEXTDAYCOUNTER;
    private static int THREEHOURCOUNTER;
    static int daysCounter = 42;

    static void incrementNextDay() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        System.out.println(date);
        daysCounter++;
    }

    void incrementByThreeHours() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, 3);
        date = c.getTime();
        System.out.println(date);
        THREEHOURCOUNTER++;
    }

    static void wakeUpTime() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.add(Calendar.DATE, 1);
        System.out.println(calendar.getTime());
    }

    void today() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        System.out.println(date);
    }

    void timeTravel() {
        if (THREEHOURCOUNTER == 5) {
            daysCounter++;
            incrementNextDay();
        }
    }

}