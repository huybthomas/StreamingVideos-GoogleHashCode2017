package org.revenge.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sdecleeen on 23/02/17.
 */
public class EndPoint {

    public int id;

    public int dataCenterLatency;

    public List<Cache> connectedCaches;

    public List<Video> videosAlreadyCached;

    public EndPoint(int id, int dataCenterLatency) {
        this.id = id;
        this.dataCenterLatency = dataCenterLatency;
        connectedCaches = new ArrayList<>();
        videosAlreadyCached = new ArrayList<>();
    }
}
