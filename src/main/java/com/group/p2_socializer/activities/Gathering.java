package com.group.p2_socializer.activities;

public class Gathering extends Activity {
    private int gatheringID;

    public int getGatheringID() {
        return gatheringID;
    }

    private Gathering(Builder builder) {
        super(builder);
        this.gatheringID = builder.gatheringID;
    }

    public static class Builder extends Activity.Builder<Builder> {
        private int gatheringID;

        public Builder gatheringID(int gatheringID) {
            this.gatheringID = gatheringID;
            return this;
        }

        public Gathering build() {
            return new Gathering(this);
        }
    }
}

