package org.revenge.video;

import java.util.*;

/**
 * Created by arthu on 23/02/2017.
 */
public class AAlgorithms {

    public void sortCachesBySpeed(List<Cache> caches){
        Collections.sort(caches, new Comparator<Cache>() {
            @Override
            public int compare(Cache o1, Cache o2) {
                if(o1.getMinLatency() > o2.getMinLatency())
                    return 1;
                if(o1.getMinLatency() < o2.getMinLatency())
                    return -1;
                return 0;
            }
        });
    }

    public double averageBenefitOfCache(Cache c) {
        double benefit = 0;
        double numConnected = c.endPointsWithLatency.size();
        for(Map.Entry<EndPoint, Integer> entry: c.endPointsWithLatency.entrySet()){
            benefit += entry.getKey().dataCenterLatency-entry.getValue();
        }
        benefit /= numConnected;
        return benefit;
    }

    @Deprecated
    public List<Cache> excludeCommonCaches(List<Cache> caches){

            return null;
    }
}
