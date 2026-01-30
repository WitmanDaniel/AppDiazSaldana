package unc.edu.pe.appdiazsaldana;

import java.io.Serializable;

public class Reservation implements Serializable {
    private long id;
    private String clientName;
    private int tableNumber;
    private String diningArea;
    private String reservationDate;
    private int numberOfPeople;
    private String courtesyDrink;

    // Constructor
    public Reservation(long id, String clientName, int tableNumber, String diningArea, String reservationDate, int numberOfPeople) {
        this.id = id;
        this.clientName = clientName;
        this.tableNumber = tableNumber;
        this.diningArea = diningArea;
        this.reservationDate = reservationDate;
        this.numberOfPeople = numberOfPeople;

        updateCourtesyDrink();
    }

    public long getId() { return id; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }
    public String getDiningArea() { return diningArea; }
    public void setDiningArea(String diningArea) { this.diningArea = diningArea; }
    public String getReservationDate() { return reservationDate; }
    public void setReservationDate(String reservationDate) { this.reservationDate = reservationDate; }
    public int getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
        updateCourtesyDrink();
    }
    public String getCourtesyDrink() { return courtesyDrink; }

    private void updateCourtesyDrink() {
        if (this.numberOfPeople > 5) {
            this.courtesyDrink = "Pisco Sour de Cortesía";
        } else {
            this.courtesyDrink = "Ninguna";
        }
    }

    public static int getImageResourceForArea(String diningArea) {
        switch (diningArea) {
            case "Terraza":
                return R.drawable.ic_area_terraza;
            case "Salón VIP":
                return R.drawable.ic_area_vip;
            case "Salón Principal":
            default:
                return R.drawable.ic_area_principal;
        }
    }
}
