package main.java.ba.unsa.etf.rpr.projekat;

import java.io.Serializable;

/***
 *
 */
public class Weight implements Serializable {
    private String date;
    private double weight;

    /***
     *
     */
    public Weight() {
        this.date = "01012020";
        this.weight = 80;
    }

    /***
     *
     * @param date
     * @param weight
     */
    public Weight(String date, double weight) {
        this.date = date;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return  date + "  -  " + weight;
    }
}
