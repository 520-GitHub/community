package life.majiang.community.enums;

public enum NotificationStatusEnum {
    UNREAD(0), READ(1);

    private int status;

    NotificationStatusEnum(int status) {
        this.status = status;

    }

    public int getstatus() {
        return status;
    }


}
