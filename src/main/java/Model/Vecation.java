package Model;


public class Vecation {
    private int flightID;
    private String hotel_name;
    private String vec_Kind;
    private String vec_Hotel;
    private String rate;
    private String priceInc;

    public Vecation(String priceInc,String vec_Kind, String vec_Hotel, String rate,int flightID ,String hotel_name) {
        this.flightID=flightID;
        this.hotel_name=hotel_name;
        this.priceInc=priceInc;
        this.vec_Kind = vec_Kind;
        this.vec_Hotel = vec_Hotel;
        this.rate = rate;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getPriceInc() {
        return priceInc;
    }

    public void setPriceInc(String priceInc) {
        this.priceInc = priceInc;
    }

    public Vecation() { }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public String getVec_Kind() {
        return vec_Kind;
    }

    public void setVec_Kind(String vec_Kind) {
        this.vec_Kind = vec_Kind;
    }

    public String getVec_Hotel() {
        return vec_Hotel;
    }

    public void setVec_Hotel(String vec_Hotel) {
        this.vec_Hotel = vec_Hotel;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Vecation{" +
                "flightID=" + flightID +
                ", hotel_name='" + hotel_name + '\'' +
                ", vec_Kind='" + vec_Kind + '\'' +
                ", vec_Hotel='" + vec_Hotel + '\'' +
                ", rate='" + rate + '\'' +
                ", priceInc='" + priceInc + '\'' +
                '}';
    }
}
