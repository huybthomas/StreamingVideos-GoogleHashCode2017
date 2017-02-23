package org.revenge.video;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sdecleeen on 23/02/17.
 */
public class Cache {

    public int id;

    public HashMap<EndPoint, Integer> endPointsWithLatency;


    public Cache(int id) {
        this.id = id;
    }

    public void addEndPoint(EndPoint endPoint, int latency) {
        endPointsWithLatency.put(endPoint, latency);
    }

}
