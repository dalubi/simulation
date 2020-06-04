package com.ices.simulation.service.parseXML.pojo;

import java.util.List;

public class FederatePS {
    private List<Integer> subscribes;
    private List<Integer> publishes;

    public FederatePS(List<Integer> subscribes, List<Integer> publishes) {
        this.subscribes = subscribes;
        this.publishes = publishes;
    }

    public List<Integer> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(List<Integer> subscribes) {
        this.subscribes = subscribes;
    }

    public List<Integer> getPublishes() {
        return publishes;
    }

    public void setPublishes(List<Integer> publishes) {
        this.publishes = publishes;
    }
}
