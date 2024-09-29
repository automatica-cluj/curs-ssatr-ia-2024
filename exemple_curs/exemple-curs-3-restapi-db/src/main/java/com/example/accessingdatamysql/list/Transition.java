package com.example.accessingdatamysql.list;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transition {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String previousPlaces; // "2,4,6,2"

    private String nextPlaces; // "6,7"

    public String getPreviousPlaces() {
        return previousPlaces;
    }

    public void setPreviousPlaces(String previousPlaces) {
        this.previousPlaces = previousPlaces;
    }

    public String getNextPlaces() {
        return nextPlaces;
    }

    public void setNextPlaces(String nextPlaces) {
        this.nextPlaces = nextPlaces;
    }
}
