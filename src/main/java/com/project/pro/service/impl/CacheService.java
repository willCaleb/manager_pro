package com.project.pro.service.impl;

import com.project.pro.model.beans.CacheBean;
import com.project.pro.service.ICacheService;
import com.project.pro.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class CacheService implements ICacheService {

    private final CacheManager cacheManager;

    @Override
    public List<CacheBean> listAllCaches() {

        List<CacheBean> cacheBeans = new ArrayList<>();
        Collection<String> cacheNames = cacheManager.getCacheNames();

        cacheNames.forEach(
                name -> {
                    CaffeineCache caffeineCache = findCacheByName(name);
                    CacheBean cacheBean = new CacheBean();
                    cacheBean.setName(caffeineCache.getName());
                    ConcurrentMap<Object, Object> cacheMap = caffeineCache.getNativeCache().asMap();
                    if (!cacheMap.isEmpty()) {
                        Set<Object> objects = cacheMap.keySet();
                        cacheBean.setValue(cacheMap.get(objects.toArray()[0]));
                        cacheBeans.add(cacheBean);
                    }
                }
        );
        return cacheBeans;
    }

    @Override
    public void clearAllCaches() {
        List<CacheBean> cacheBeans = listAllCaches();
        cacheBeans.forEach(cache -> clearCache(cache.getName()));
    }

    @Override
    public void clearCache(String cacheName) {
        CaffeineCache caffeineCache = findCacheByName(cacheName);
        if (Utils.isNotEmpty(caffeineCache)) {
            caffeineCache.getNativeCache().invalidateAll();
        }
    }

    @Override
    public CaffeineCache findCacheByName(String cacheName) {

        CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache(cacheName);

        if (Utils.isNotEmpty(caffeineCache)) {
            return caffeineCache;
        }
        return null;
    }

}
