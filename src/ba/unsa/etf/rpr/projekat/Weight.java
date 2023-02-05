package ba.unsa.etf.rpr.projekat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/***
 *
 */
public class Weight implements Serializable {
    private LocalDate date;
    private double weight;

    /***
     *
     */
    public Weight() {
        this.date = converter("01012020");
        this.weight = 80;
    }

    /***
     *
     * @param date
     * @param weight
     */
    public Weight(String date, double weight) {
        this.date = converter(date);
        this.weight = weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    /***
     * Converts string to date
     * @param date
     * @return
     */
    private LocalDate converter(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy", Locale.ENGLISH);
        return LocalDate.parse(date, formatter);
    }
}
