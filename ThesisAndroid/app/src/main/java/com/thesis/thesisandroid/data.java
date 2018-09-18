package com.thesis.thesisandroid;

public class data {
    private String exername;
    private String power;
    private String weight;
    private String reps;
    private String date;
    private String onerm;

    public data(String exername, String power, String weight, String reps,String date, String onerm){
        this.exername = exername;
        this.power = power;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
        this.onerm = onerm;
    }public String getexername(){
        return exername;
    }
    public void setexername(){
        this.exername = exername;
    }
    public String getpower(){
        return power;
    }
    public void setpower(){
        this.power = power;
    }
    public String getweight(){
        return weight;
    }
    public void setweight(){
        this.weight = weight;
    }
    public String getreps(){
        return reps;
    }
    public void setreps(){
        this.reps = reps;
    }
    public String getOnerepmax() { return onerm; }
    public void setOnerepmax() { this.onerm = onerm; }
    public String getdate(){
        return date;
    }
    public void setdate(){
        this.date = date;
    }

}
