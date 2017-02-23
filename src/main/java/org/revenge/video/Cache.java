package org.revenge.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sdecleeen on 23/02/17.
 */
public class Cache {

    public int id;

    public double averageBenefit = -1;

    public int cacheFilledWithXMB  = 0;

    public HashMap<EndPoint, Integer> endPointsWithLatency;

    public List<Video> videos;

    public Cache(int id) {
        this.id = id;
        endPointsWithLatency = new HashMap<>();
        videos = new ArrayList<>();
    }

    public void addEndPoint(EndPoint endPoint, int latency) {
        endPointsWithLatency.put(endPoint, latency);
    }

    public double getMinLatency(){
        double lowestLatency = 5000;
        for(Map.Entry<EndPoint, Integer> entry : endPointsWithLatency.entrySet()){
            lowestLatency = lowestLatency > entry.getValue() ? entry.getValue(): lowestLatency;
        }
        return lowestLatency;
    }

}
