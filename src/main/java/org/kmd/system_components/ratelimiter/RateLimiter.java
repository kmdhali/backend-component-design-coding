package org.kmd.system_components.ratelimiter;

public interface RateLimiter {
    boolean allowRequest(String userId);
}
