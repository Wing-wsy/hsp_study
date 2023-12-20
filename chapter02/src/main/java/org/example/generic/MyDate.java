package org.example.generic;

/**
 * @author wing
 * @create 2023/12/19
 */
public class MyDate implements Comparable<MyDate>{
    private int year;
    private int month;
    private int day;
    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
    // 把对年月日的比较放到这里
    @Override
    public int compareTo(MyDate o) {
        //如果name相同，就比较birthdy - year
        int yearMinus = year - o.getYear();
        if(yearMinus != 0){
            return yearMinus;
        }
        //如果year相同，就比较month
        int monthMinus = month - o.getMonth();
        if(monthMinus != 0){
            return monthMinus;
        }
        return day - o.getDay();    }
}
