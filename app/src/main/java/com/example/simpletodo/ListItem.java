package com.example.simpletodo;

public class ListItem {
    private String text;
    private boolean useDate;
    private String dateText;
    private String month;
    private int day;
    private int year;

    public ListItem(String text) {
        this.text = text;
        useDate = false;
        dateText = "";
        month = "January";
        day = 1;
        year = 2021;
    }

    public ListItem(String text, boolean useDate, String dateText, String month, int day, int year) {
        this.text = text;
        this.useDate = useDate;
        this.dateText = dateText;
        this.month = month;
        this.day = day;
        this.year = year;
    }

    // Setters
    public void setText(String newText) {
        text = newText;
    }

    public void setUseDate(boolean newUseDate) {
        useDate = newUseDate;
    }

    public void setDateText(String newDateText) {
        dateText = newDateText;
    }

    public void setMonth(String newMonth) {
        month = newMonth;
    }

    public void setDay(int newDay) {
        day = newDay;
    }

    public void setYear(int newYear) {
        year = newYear;
    }

    // Getters
    public String getText() {
        return text;
    }

    public boolean getUseDate() {
        return useDate;
    }

    public String getDateText() {
        return dateText;
    }

    public String getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    // toString
    public String toString() {
        return text + "~" + Boolean.toString(useDate) + "~" + dateText + "~" + month + "~" + day + "~" + year;
    }
}
