package org.tensorflow.ChoiEomParkLee;

import java.util.ArrayList;

public class LocationDataObject {
    public LocationDataObject() {
    }

    @Override
    public String toString() {
        return "LocationDataObject{" +
                "comment='" + comment + '\'' +
                ", facilities=" + facilities +
                ", hashtag=" + hashtag +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    public String comment;
    public ArrayList<String> facilities;
    public ArrayList<String> hashtag;
    public String image;
    public String name;
    public String tel;
}
