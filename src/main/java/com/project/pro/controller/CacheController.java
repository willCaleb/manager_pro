package com.project.pro.controller;

import com.project.pro.model.beans.CacheBean;
import com.project.pro.service.ICacheService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cache")
@AllArgsConstructor
public class CacheController {

    private final ICacheService cacheService;

    @GetMapping("/all")
    public ResponseEntity<List<CacheBean>> findAllCaches(){
        return ResponseEntity.ok(cacheService.listAllCaches());
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
