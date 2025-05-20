package com.example.projectgraphic;

public class Timer {
    private int Hour;
    private int Minute;
    private int Second;

    public Timer(int Hour, int Minute, int Second) {
        this.Hour = Hour;
        this.Minute = Minute;
        this.Second = Second;
    }


    public int getHour() {
        return Hour;
    }

    public int getMinute() {
        return Minute;
    }

    public int getSecond() {
        return Second;
    }

    public void setHour(int Hour) {
        this.Hour = Hour;
    }
    public void setMinute(int Minute) {
        this.Minute = Minute;
    }
    public void setSecond(int Second) {
        this.Second = Second;
    }
    public Object initializeTimer() {
        int totalSeconds = (getHour() * 3600) + (getMinute() * 60) + getSecond();
//        while (totalSeconds > 0) {
           int remainingHours = totalSeconds / 3600;
            int remainingMinutes = (totalSeconds % 3600) / 60;
           int  remainingSeconds = totalSeconds % 60;
//            System.out.printf("%02d:%02d:%02d\n", remainingHours, remainingMinutes, remainingSeconds);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//            }
//            totalSeconds--;
//        }
       // System.out.println("Time's up!");
        System.out.println(remainingHours+":"+remainingMinutes+":"+remainingSeconds);
        return (remainingHours+":"+remainingMinutes+":"+remainingSeconds);
    }
}