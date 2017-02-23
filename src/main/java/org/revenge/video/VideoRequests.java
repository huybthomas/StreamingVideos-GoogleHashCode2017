package org.revenge.video;

import java.util.*;

/**
 * Created by dries on 23/02/2017.
 */
public class VideoRequests {
    public static Integer numbRequests;
    public static Integer whatVideo;

    public static List<Video> SortPopularVideoForCache(Cache cache, List<Video> videos, List<Request> requests)
    {
        List<Video> videoList = new ArrayList<Video>();
        List<Tuple<Video, Integer>> CacheVideoRequests = new ArrayList<>();
        HashMap<EndPoint, Integer> endPointsWithLatency = cache.endPointsWithLatency;
        Set<EndPoint> endPointsSet = endPointsWithLatency.keySet();

        for(Request request : requests)
        {
            Iterator<EndPoint> it = endPointsSet.iterator();
            while(it.hasNext())
            {
                EndPoint endPoint = it.next();

                if(request.endPoint.equals(endPoint))
                {
                    Iterator<Tuple<Video, Integer>> tupIt = CacheVideoRequests.iterator();
                    boolean found = false;
                    while(tupIt.hasNext())
                    {
                        Tuple<Video, Integer> tuple = tupIt.next();

                        if(request.video.equals(tuple.getLhs()))
                        {
                            Integer videoRequests = tuple.getRhs();
                            videoRequests = tuple.getRhs() + request.numberOfRequests;
                            found = true;
                        }
                    }

                    if(!found)
                    {
                        CacheVideoRequests.add(new Tuple<>(request.video, request.numberOfRequests));
                    }
                }
            }
        }

        Collections.sort(CacheVideoRequests, new TupleComparator());

        for(Tuple<Video, Integer> tuple : CacheVideoRequests)
        {
            videoList.add(tuple.getLhs());
        }

        return videoList;
    }
}
