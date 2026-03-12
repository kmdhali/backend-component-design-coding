package org.kmd.system_components.in_memory_cache_design;

class CacheEntry<V>{

    V entry ;
    long  ttl;  //will expire  at ttl

    public CacheEntry(V entry, long ttl){
        this.entry=entry;
        this.ttl=ttl;
    }

    public V getValue(){
        return entry;
    }

    public boolean isExpired(){
       return ttl < System.currentTimeMillis();

    }

    public long getTtl(){
        return ttl;
    }


}