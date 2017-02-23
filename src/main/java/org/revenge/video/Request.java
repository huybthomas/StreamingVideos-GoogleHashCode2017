package org.revenge.video;

/**
 * Created by sdecleeen on 23/02/17.
 */
public class Request {

    public Video video;

    public EndPoint endPoint;

    public int numberOfRequests;

    public Request() {}

    public Request(Video video, EndPoint endPoint, int numberOfRequests) {
        this.video = video;
        this.endPoint = endPoint;
        this.numberOfRequests = numberOfRequests;
    }

}
