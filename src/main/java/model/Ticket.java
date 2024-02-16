package model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Ticket {
    private String origin;
    @SerializedName("origin_name")
    private String originName;
    private String destination;
    @SerializedName("destination_name")
    private String destinationName;
    @SerializedName("departure_date")
    private String departureDate;
    @SerializedName("departure_time")
    private String departureTime;
    @SerializedName("arrival_date")
    private String arrivalDate;
    @SerializedName("arrival_time")
    private String arrivalTime;
    private String carrier;
    private int stops;
    private double price;

    public String getDepartureDateTime() {
        String time = departureTime.charAt(1) == ':'
                ? 0 + departureTime : departureTime;
        return departureDate + " " + time;
    }

    public String getArrivalDateTime() {
        String time = arrivalTime.charAt(1) == ':'
                ? 0 + arrivalTime : arrivalTime;
        return arrivalDate + " " + time;
    }

}