package org.revenge.video;

import org.revenge.pizza.steve.Cell;
import org.revenge.pizza.steve.Slice;

import java.io.*;
import java.util.*;

/**
 * Created by sdecleeen on 23/02/17.
 */
public class Main {

//    private static final String[] INPUT_FILES = {"kittens.in", "me_at_the_zoo.in", "trending_today.in", "videos_worth_spreading.in"};
    private static final String[] INPUT_FILES = {"trending_today.in"};

    private static int numberOfVideos;
    private static int numberOfEndPoints;
    private static int numberOfRequestDescriptions;
    private static int numberOfCacheServers;
    private static int cacheServerCapacity;

    private static List<Video> videos;
    private static List<EndPoint> endPoints;
    private static List<Cache> caches;
    private static List<Request> requests;

    private static List<String> solution;

    public static void main(String[] args) {
        for (String file : INPUT_FILES) {
            readInput(file);
            solve();
            printOutput(file);
        }
    }

//    public static void solve(){
//        //Sort Caches By speed
//        //AAlgorithms.sortCachesByMinlatency(caches);
//        AAlgorithms.sortCachesByBenefit(caches);
//        for(Cache c: caches){
//            List<Video> videosForCache = VideoRequests.SortPopularVideoForCache(c, videos, requests);
//            AAlgorithms.fillCache(c, videosForCache, cacheServerCapacity);
//        }
//    }

    public static void solve() {
        solution = new ArrayList<>();
        for(Cache cache : caches) {
            HashMap<Request, Integer> videoPopularityHM = new HashMap<>();
            Set<EndPoint> endPoints = cache.endPointsWithLatency.keySet();
            for(Request request : requests) {
                if(endPoints.contains(request.endPoint)) {
                    if(videoPopularityHM.containsKey(request.video)) {
                        videoPopularityHM.put(request, videoPopularityHM.get(request.video) + request.numberOfRequests);
                    } else {
                        videoPopularityHM.put(request, request.numberOfRequests);
                    }
                }
            }

            String partialSolution = String.valueOf(cache.id);
            List<Integer> addedVideos = new ArrayList<>();
            int remainingSize = cacheServerCapacity;

            for(int i=0; i<50; i++) {

                Request bestRequest = null;
                Iterator it = videoPopularityHM.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if(!addedVideos.contains(((Request) pair.getKey()).video.id) && ((Request)pair.getKey()).video.size <= remainingSize) {
                        if (bestRequest == null) {
                            bestRequest = (Request) pair.getKey();
                        } else if ((Integer) pair.getValue() > bestRequest.numberOfRequests) {
                            bestRequest = (Request) pair.getKey();
                        }
                    }
                }

                if(bestRequest != null) {
                    partialSolution += " " + bestRequest.video.id;
                    remainingSize -= bestRequest.video.size;
                    addedVideos.add(bestRequest.video.id);
                    requests.remove(bestRequest);
                }

            }

            solution.add(partialSolution);
        }
    }


    private static void readInput(String fileName) {

        videos = new ArrayList<>();
        endPoints = new ArrayList<>();
        caches = new ArrayList<>();
        requests = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/video/input/" + fileName))) {
            int[] fileArgs = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            numberOfVideos = fileArgs[0];
            numberOfEndPoints = fileArgs[1];
            numberOfRequestDescriptions = fileArgs[2];
            numberOfCacheServers = fileArgs[3];
            cacheServerCapacity = fileArgs[4];

            int[] vidData = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for(int i = 0; i < vidData.length; i++) {
                videos.add(new Video(i, vidData[i]));
            }

            for(int i = 0; i < numberOfEndPoints; i++) {
                int[] endPointData = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                EndPoint endPoint = new EndPoint(i, endPointData[0]);
                endPoints.add(endPoint);
                for(int j = 0; j < endPointData[1]; j++) {
                    int[] cacheData = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                    Cache cache = addCacheIfNotYetExists(cacheData[0]);
                    cache.endPointsWithLatency.put(endPoint, cacheData[1]);
                    endPoint.connectedCaches.add(cache);
                }
            }

            for(int i = 0; i < numberOfRequestDescriptions; i++) {
                int[] requestData = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                Request request = new Request();
                for(EndPoint endPoint : endPoints) {
                    if(endPoint.id == requestData[1]) {
                        request.endPoint = endPoint;
                        break;
                    }
                }
                for(Video video : videos) {
                    if(video.id == requestData[0]) {
                        request.video = video;
                        break;
                    }
                }
                request.numberOfRequests = requestData[2];
                requests.add(request);
            }
        } catch (IOException ioe) {
            System.err.println("IOException while trying to setup file access.");
        }
    }

    private static Cache addCacheIfNotYetExists(int id) {
        for(Cache cache : caches) {
            if(cache.id == id) {
                return cache;
            }
        }
        Cache cache = new Cache(id);
        caches.add(cache);
        return cache;
    }

    private static void printOutput(String file) {
        String outputFilePath = file.substring(0, file.indexOf(".")) + ".out";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/video/output/" + outputFilePath))) {
            bufferedWriter.write(Integer.toString(solution.size()));
            bufferedWriter.newLine();
            for(String s : solution) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
//            bufferedWriter.write(Integer.toString(caches.size()));
//            bufferedWriter.newLine();
//            for (Cache c : caches) {
//                bufferedWriter.write(c.toString());
//                bufferedWriter.write("\n");
//            }
        } catch(IOException ioe) {
            System.err.println("IOException while trying to setup file output.");
        }
    }

}
