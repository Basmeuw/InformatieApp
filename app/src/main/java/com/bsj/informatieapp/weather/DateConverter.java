package com.bsj.informatieapp.weather;

public class DateConverter {
    private static String[] months = {
            "Januari",
            "Februari",
            "Maart",
            "April",
            "Mei",
            "Juni",
            "Juli",
            "Augustus",
            "September",
            "Oktober",
            "Nomvember",
            "December"
    };
    public static String newDate(String date){
        String newDate = "";
        newDate += date.substring(8,10);
        newDate += " ";
        for (int i = 0; i < 12; i++){
            if(Integer.parseInt(date.substring(5,7)) == i){
                newDate += months[i];
            }
        }
        return newDate;
    }
}
