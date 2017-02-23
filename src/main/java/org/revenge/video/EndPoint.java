package org.revenge.video;

import java.util.HashMap;

/**
 * Created by sdecleeen on 23/02/17.
 */
public class EndPoint {

    public int id;

    public int dataCenterLatency;

    public int numberOfConnectedCaches;

    public HashMap<Integer, Integer> connectedCachesWithLatency;

    public EndPoint(int id, int dataCenterLatency, int numberOfConnectedCaches) {
        this.id = id;
        this.dataCenterLatency = dataCenterLatency;
        this.numberOfConnectedCaches = numberOfConnectedCaches;
    }

}
