package org.revenge.video;

import java.util.*;

/**
 * Created by arthu on 23/02/2017.
 */
public class AAlgorithms {

    public static void sortCachesByMinlatency(List<Cache> caches){
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

    public static void sortCachesByBenefit(List<Cache> caches){
        Collections.sort(caches, new Comparator<Cache>() {
            @Override
            public int compare(Cache o1, Cache o2) {
                if(averageBenefitOfCache(o1) < averageBenefitOfCache(o2))
                    return 1;
                if(averageBenefitOfCache(o1) > averageBenefitOfCache(o2))
                    return -1;
                return 0;
            }
        });
    }

    public static double averageBenefitOfCache(Cache c) {
        double benefit = 0;
        double numConnected = c.endPointsWithLatency.size();
        for(Map.Entry<EndPoint, Integer> entry: c.endPointsWithLatency.entrySet()){
            benefit += entry.getKey().dataCenterLatency-entry.getValue();
        }
        benefit /= numConnected;
        c.averageBenefit=benefit;
        return benefit;
    }

    public static void fillCache(Cache c, List<Video> videos, int capacity){
        Iterator<Video> videoIterator = videos.iterator();
        while(c.cacheFilledWithXMB < capacity && videoIterator.hasNext()){
             Video v = videoIterator.next();
             if(v.size < capacity - c.cacheFilledWithXMB){
                 c.videos.add(v);
                 c.cacheFilledWithXMB += v.size;
             }
        }
    }

    @Deprecated
    public List<Cache> excludeCommonCaches(List<Cache> caches){

            return null;
    }
}
