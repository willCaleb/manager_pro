package com.project.pro.controller;

import com.project.pro.model.beans.CacheBeanDTO;
import com.project.pro.model.entity.CacheBean;
import com.project.pro.service.ICacheService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.Cache;

import java.util.List;

@RestController
@RequestMapping("/cache")
@AllArgsConstructor
public class CacheController extends AbstractController<CacheBean, CacheBeanDTO>{

    private final ICacheService cacheService;

    @GetMapping("/all")
    public ResponseEntity<List<CacheBeanDTO>> findAllCaches(){
        List<CacheBean> cacheBeans = cacheService.listAllCaches();

        return ResponseEntity.ok(toDtoList(cacheBeans));
    }

    @PutMapping("/clear/{cacheName}")
    public void clearCache(@PathVariable("cacheName") String cacheName){
        cacheService.clearCache(cacheName);
    }

    @PutMapping("/clear/all")
    public void clearAll() {
        cacheService.clearAllCaches();
    }

}
