/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.accessingdatamysql.list;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author mihai
 */

@Entity
public class Node {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String value;

    private Integer nextNodeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(Integer nextNodeId) {
        this.nextNodeId = nextNodeId;
    }
}
