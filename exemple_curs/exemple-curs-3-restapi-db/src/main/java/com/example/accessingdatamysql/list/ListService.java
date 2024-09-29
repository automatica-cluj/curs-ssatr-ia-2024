/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.accessingdatamysql.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
public class ListService {
    
    @Autowired
    private NodeRepository nodeRepo;

    public Node addNode(String value, Integer nextNodeId){
        Node n = new Node();
        n.setValue(value);
        if(nextNodeId!=null){
            Node next = nodeRepo.findById(nextNodeId).get();
            n.setNextNodeId(next.getNextNodeId());
            n = nodeRepo.save(n);
        }
        return n;
    }

    public List<Node> getAllNodeValuesStartingWith(String ids){
        List<Node> list = new ArrayList<Node>();
        if(ids!=null) {
            Integer id = Integer.parseInt(ids);
            Node n = nodeRepo.findById(id).get();

            list.add(n);
            while (n.getNextNodeId() != null) {
                //get next node
                n = nodeRepo.findById(n.getNextNodeId()).get();
                list.add(n);
            }
            return list;
        }else{
            nodeRepo.findAll().forEach(list::add);
            return list;
        }
    }
    
}
