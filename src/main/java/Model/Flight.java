package Model;

import java.sql.Date;

public class Flight {
    private int flightID;
    private String destinationCountry;
    private String destinationCity;
    private Date fromDate;
    private Date toDate;
    private String seller;
    private String userNameSeller;
    private int price;
    private boolean isConnection;
    private boolean isSeparate;
    private String company;
    private int baggage;
    private String isReturn;
    private int numTicket;
    private String cardType;
    private String vecInc;

    public Flight(int flightID, String destinationCountry, String destinationCity, Date fromDate, Date toDate, String seller, int price, int isConnection, int isSeparate, String company, int baggage, String userNameSeller,String isReturn,int numTicket,String vecInc,String cardType) {
        this.flightID = flightID;
        this.destinationCountry = destinationCountry;
        this.destinationCity = destinationCity;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.seller = seller;
        this.price = price;
        this.isConnection = (isConnection == 1 ? true : false);
        this.isSeparate = (isSeparate == 1 ? true : false);
        this.company = company;
        this.baggage = baggage;
        this.userNameSeller = userNameSeller;
        this.isReturn=(isReturn);
        this.numTicket=numTicket;
        this.vecInc=vecInc;
        this.cardType=cardType;
    }

    public void setNumTicket(int numTicket) {
        this.numTicket = numTicket;
    }

    public int getNumTicket() {
        return numTicket;
    }

    public String isReturn() {
        return isReturn;
    }

    public void setReturn(String aReturn) {
        isReturn = aReturn;
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

    public String getSeller() {
        return seller;
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

    public String getUserNameSeller() {
        return userNameSeller;
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

    public void setSeller(String seller) {
        this.seller = seller;
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

    public void setUserNameSeller(String userNameSeller) {
        this.userNameSeller = userNameSeller;
    }
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getVecInc() {
        return vecInc;
    }

    public void setVecInc(String vecInc) {
        this.vecInc = vecInc;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightID=" + flightID +
                ", destinationCountry='" + destinationCountry + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", seller='" + seller + '\'' +
                ", userNameSeller='" + userNameSeller + '\'' +
                ", price=" + price +
                ", isConnection=" + isConnection +
                ", isSeparate=" + isSeparate +
                ", company='" + company + '\'' +
                ", baggage=" + baggage +
                ", isReturn='" + isReturn + '\'' +
                ", numTicket=" + numTicket +
                ", cardType='" + cardType + '\'' +
                ", vecInc='" + vecInc + '\'' +
                '}';
    }
}
