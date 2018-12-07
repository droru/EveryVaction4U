package Model;

public class Notification {
    private String toUser;
    private String fromUser;
    private int flightID;
    private boolean isResponsed;
    private boolean isAccept;

    public Notification(String fromUser, String toUser, int flightID, boolean isResponsed, boolean isAccept) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.flightID = flightID;
        this.isResponsed = isResponsed;
        this.isAccept = isAccept;
    }

    public String getToUser() {
        return toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public int getFlightID() {
        return flightID;
    }

    public boolean getIsResponsed() {
        return isResponsed;
    }

    public boolean getIsAccept() {
        return isAccept;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public void setIsResponsed(boolean isResponsed) {
        this.isResponsed = isResponsed;
    }

    public void setIsAccept(boolean isAccept) {
        this.isAccept = isAccept;
    }
}
