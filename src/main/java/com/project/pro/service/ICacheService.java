package com.project.pro.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.project.pro.model.beans.CacheBeanDTO;
import com.project.pro.model.entity.CacheBean;
import org.springframework.cache.caffeine.CaffeineCache;

import java.util.List;

public interface ICacheService {

    List<CacheBean> listAllCaches();

    void clearAllCaches();

    void clearCache(String cacheName);

    CaffeineCache findCacheByName(String cacheName);

    Cache findNativeCacheByName(String cacheName);

}
