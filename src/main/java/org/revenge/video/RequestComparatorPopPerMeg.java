package org.revenge.video;

import java.util.Comparator;

/**
 * Created by sdecleeen on 23/02/17.
 */
public class RequestComparatorPopPerMeg implements Comparator<Request> {

    @Override
    public int compare(Request request1, Request request2) {
        double popPerMeg1 = calculatePopPerMeg(request1);
        double popPerMeg2 = calculatePopPerMeg(request2);
        return popPerMeg1 < popPerMeg2 ? -1 : popPerMeg1 == popPerMeg2 ? 0 : 1;
    }

    public static double calculatePopPerMeg(Request request) {
        return request.numberOfRequests / request.video.size;
    }

}
