package org.kmd.system_components.ratelimiter;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter implements RateLimiter {

    static java.io.PrintStream p = System.out;

    long windowsSizeMillis = 0;                             //in seconds
    long maxNoRequest = 0;                                  // request allowed in that window

    private final Map<String, Deque<Timestamp>> requestTimestamps = new ConcurrentHashMap<>();


    public SlidingWindowRateLimiter(long windowsSizeMillis, long maxNoRequest) {
        this.windowsSizeMillis = windowsSizeMillis;
        this.maxNoRequest = maxNoRequest;

    }

    @Override
    public boolean allowRequest(String usedId) {
        long currentTimeMillis = System.currentTimeMillis();
        long threshold = currentTimeMillis - windowsSizeMillis;

        Deque<Timestamp> timestamps = requestTimestamps.get(usedId);
        if (timestamps == null) {
            timestamps = new ArrayDeque<>();
            requestTimestamps.put(usedId, timestamps);

        }


        while (timestamps.peekFirst() != null && timestamps.peekFirst().getTime() < threshold) {
            timestamps.removeFirst();
        }

        int noOfRequestInWindows = timestamps.size();
        if (noOfRequestInWindows > maxNoRequest) {
            System.out.println(usedId +"-> allowed ? false . noOfRequestInWindows : " + noOfRequestInWindows + " : maxNoRequest : " + maxNoRequest);
            return false;
        } else {
            timestamps.add(new Timestamp(currentTimeMillis));
            System.out.println(usedId + "-> allowed ? true . noOfRequestInWindows : " + noOfRequestInWindows + " : maxNoRequest : " + maxNoRequest);
            return true;

        }
    }


    public static void main(String[] args) throws InterruptedException {


        RateLimiter rateLimiter = new SlidingWindowRateLimiter(5, 2);

        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd1");
        Thread.sleep(5000);
        rateLimiter.allowRequest("kmd");

    }
}
