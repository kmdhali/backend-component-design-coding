package org.kmd.system_components.ratelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimiter {

    static java.io.PrintStream p = System.out;
    private final long bucketCapacity;
    private final long refillTokenPerSecond;
    private final Map<String, Long> availableTokensPerUser = new ConcurrentHashMap<>();  //available tokens per user
    private final Map<String, Long> refillTimes = new ConcurrentHashMap<>();


    public TokenBucketRateLimiter(long bucketCapacity, long refillTokenPerSecond) {
        this.bucketCapacity = bucketCapacity;
        this.refillTokenPerSecond = refillTokenPerSecond;

    }

    @Override
    public boolean allowRequest(String usedId) {
        boolean allow = false;
        refillLogic(usedId);
        long availableToken = availableTokensPerUser.getOrDefault(usedId, bucketCapacity);
        if (availableToken > 0) {
            availableTokensPerUser.put(usedId, --availableToken);
            allow = true;
        } else {
            allow = false;
        }
        p.println("allowed : "+allow);
        return allow;
    }

    private void refillLogic(String userId) {

        p.println("Current Tokens = " + userId + " " + availableTokensPerUser.getOrDefault(userId, 0L));

        long lastTime = refillTimes.getOrDefault(userId, 0L);
        if (lastTime == 0) {// user coming for the first time
            availableTokensPerUser.put(userId, bucketCapacity);
            refillTimes.put(userId, System.currentTimeMillis());
        } else {
            long now = System.currentTimeMillis();
            long spannedTime = now - lastTime;
            spannedTime = spannedTime / 1000;
            long tokensEarned = spannedTime * this.refillTokenPerSecond;
            long totalTokens = tokensEarned + availableTokensPerUser.getOrDefault(userId, 0L);
            availableTokensPerUser.put(userId, Math.min(totalTokens, bucketCapacity));
            refillTimes.put(userId, now);

        }
        p.println("Updated  Tokens = " + userId + " " + availableTokensPerUser.getOrDefault(userId, 0L));

    }


    public static void main(String[] args) throws InterruptedException {


        RateLimiter rateLimiter = new TokenBucketRateLimiter(4, 2);
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        Thread.sleep(3000);
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
        Thread.sleep(1000);
        rateLimiter.allowRequest("kmd");
        rateLimiter.allowRequest("kmd");
//        rateLimiter.allowRequest("kmd");
//        rateLimiter.allowRequest("kmd");
//        rateLimiter.allowRequest("kmd2");


    }
}
