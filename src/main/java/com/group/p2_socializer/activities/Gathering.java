package com.group.p2_socializer.activities;

public class Gathering extends Activity{
    public int gatheringID;
    public static class Builder extends Activity.Builder<Builder>{
        public Gathering build(){
            return new Gathering(this);
        }
    }
    public Gathering(Builder builder) {
        super(builder);
    }
}
