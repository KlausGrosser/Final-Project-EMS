package com.finalproject.model.entity;

/**
 * Reasons for leave entity
 *
 * @see Leave
 */
public enum LeaveReason {

    HOLIDAYS("Holidays"),
    PERSONAL("Personal"),
    FAMILY("Family"),
    SICK("Sick"),
    EMERGENCY("Emergency"),
    OTHER("Other");

    private final String simpleName;

    LeaveReason(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return simpleName;
    }
}


