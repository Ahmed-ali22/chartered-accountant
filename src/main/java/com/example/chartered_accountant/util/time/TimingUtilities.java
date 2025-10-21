package com.example.chartered_accountant.util.time;

import java.sql.Timestamp;

public class TimingUtilities {
    private TimingUtilities() {
    }

    public static Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
