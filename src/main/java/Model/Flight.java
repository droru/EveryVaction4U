package Model;

import java.sql.Date;

public class Flight {
    private int flightID;
    private String destinationCountry;
    private String destinationCity;
    private Date fromDate;
    private Date toDate;
    private User seller;
    private String nameSeller;
    private int price;
    private boolean isConnection;
    private boolean isSeparate;
    private String company;
    private int baggage;

    public Flight(int flightID, String destinationCountry, String destinationCity, Date fromDate, Date toDate, User seller, int price, int isConnection, int isSeparate, String company, int baggage) {
        this.flightID = flightID;
        this.destinationCountry = destinationCountry;
        this.destinationCity = destinationCity;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.seller = seller;
        nameSeller = seller.getFirstName() + " " + seller.getLastName();
        this.price = price;
        this.isConnection = (isConnection == 1 ? true : false);
        this.isSeparate = (isSeparate == 1 ? true : false);
        this.company = company;
        this.baggage = baggage;
    }

    public int getFlightID() {
        return flightID;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public User getSeller() {
        return seller;
    }

    public String getNameSeller() {
        return nameSeller;
    }

    public int getPrice() {
        return price;
    }

    public boolean isConnection() {
        return isConnection;
    }

    public boolean isSeparate() {
        return isSeparate;
    }

    public String getCompany() {
        return company;
    }

    public int getBaggage() {
        return baggage;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public void setDestinationCountry(String destination) {
        this.destinationCountry = destination;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setNameSeller(String nameSeller) {
        this.nameSeller = nameSeller;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setConnection(boolean connection) {
        isConnection = connection;
    }

    public void setSeparate(boolean separate) {
        isSeparate = separate;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setBaggage(int baggage) {
        this.baggage = baggage;
    }
}
