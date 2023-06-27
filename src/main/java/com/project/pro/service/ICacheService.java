package com.project.pro.service;

import com.project.pro.model.beans.CacheBean;
import org.springframework.cache.caffeine.CaffeineCache;

import java.util.List;

public interface ICacheService {

    List<CacheBean> listAllCaches();

    void clearAllCaches();

    void clearCache(String cacheName);

    CaffeineCache findCacheByName(String cacheName);

}
