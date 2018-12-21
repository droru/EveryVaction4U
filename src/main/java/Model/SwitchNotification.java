package Model;

public class SwitchNotification extends Notification {
    private int notificationID;
    private int secondFlightID;

    public SwitchNotification(String fromUser, String toUser, int flightID, boolean isResponsed, boolean isAccept, boolean isPayProcess, int secondFlightID, int notificationID) {
        super(notificationID, fromUser, toUser, flightID, isResponsed, isAccept, isPayProcess);
        this.secondFlightID = secondFlightID;
        this.notificationID = notificationID;
    }

    public SwitchNotification(Notification notification, int secondFlightID) {
        super(notification.getFromUser(), notification.getToUser(), notification.getFlightID(), notification.getIsResponsed(), notification.getIsAccept(), notification.isPayProcess());
        this.secondFlightID = secondFlightID;
    }

    public int getSecondFlightID() {
        return secondFlightID;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setSecondFlightID(int secondFlightID) {
        this.secondFlightID = secondFlightID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }
}
