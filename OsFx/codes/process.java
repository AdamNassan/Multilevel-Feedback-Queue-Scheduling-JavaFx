package com.example.demo;


import java.util.ArrayList;

public class process implements Comparable<process>{
    private int ID;
    private int ArrivalTime ;
    private ArrayList<Integer> CPUAndIOBust = new ArrayList<>();
    private int index=0;
    private int Q1TimesQuanta=0;
    private int Q2TimesQuanta=0;
    private double guessTime=50;
    private boolean calc=false;
    private int Q3Times=3;
    private int inQueue=0;
    private int waitingTime=0;
    public process(int iD, int arrivalTime, ArrayList<Integer> cPUAndIOBust) {
        super();
        this.ID = iD;
        this.ArrivalTime = arrivalTime;
        this.CPUAndIOBust = cPUAndIOBust;
    }
    public int getID() {
        return this.ID;
    }
    public void setID(int iD) {
        this.ID = iD;
    }
    public int getArrivalTime() {
        return this.ArrivalTime;
    }
    public void setArrivalTime(int arrivalTime) {
        this.ArrivalTime = arrivalTime;
    }
    public ArrayList<Integer> getCPUAndIOBust() {
        return this.CPUAndIOBust;
    }
    public void setCPUAndIOBust(ArrayList<Integer> cPUAndIOBust) {
        this.CPUAndIOBust = cPUAndIOBust;
    }
    public int getIndex() {
        return this.index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getQ1TimesQuanta() {
        return this.Q1TimesQuanta;
    }
    public void setQ1TimesQuanta(int q1TimesQuanta) {
        this.Q1TimesQuanta = q1TimesQuanta;
    }
    public void callQ1TimesQuanta(int q1) {
        this.Q1TimesQuanta = q1*10;
    }
    public int getQ2TimesQuanta() {
        return this.Q2TimesQuanta;
    }
    public void setQ2TimesQuanta(int q2TimesQuanta) {
        this.Q2TimesQuanta = q2TimesQuanta;
    }
    public void callQ2TimesQuanta(int q2) {
        this.Q2TimesQuanta = q2*10;
    }
    public double getGuessTime() {
        return this.guessTime;
    }
    public void setGuessTime(double guessTime) {
        this.guessTime = guessTime;
    }
    public int getQ3Times() {
        return this.Q3Times;
    }
    public void setQ3Times(int q3Times) {
        this.Q3Times = q3Times;
    }
    public int getInQueue() {
        return this.inQueue;
    }
    public void setInQueue(int inQueue) {
        this.inQueue = inQueue;
    }
    public int getWaitingTime() {
        return waitingTime;
    }
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }


    public boolean isCalc() {
        return calc;
    }
    public void setCalc(boolean calc) {
        this.calc = calc;
    }
    @Override
    public int compareTo(process o) {
        if(this.ArrivalTime>o.ArrivalTime)
            return 1;
        else if(this.ArrivalTime<o.ArrivalTime)
            return -1;
        else
            return 0;
    }

}

