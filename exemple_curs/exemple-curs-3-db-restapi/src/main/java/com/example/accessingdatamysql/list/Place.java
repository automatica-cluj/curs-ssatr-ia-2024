package com.example.accessingdatamysql.list;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Place {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private int tokens;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }
}
