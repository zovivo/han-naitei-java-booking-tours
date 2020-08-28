package com.bookingTour.model.enu;

public enum Status {

    ORDERING(1),
    APPROVE(2),
    CANCEL(3),
    PAID(4),
    ;

    public final int value;

    private Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
