package com.pequla.sync.controller;

import com.pequla.sync.entity.CachedData;
import com.pequla.sync.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/api/data")
public class DataController {

    private final DataService dataService;

    @GetMapping
    public Page<CachedData> getData(Pageable pageable) {
       return dataService.getCachedDataPaged(pageable);
    }

    @GetMapping(path = "/list")
    public List<CachedData> getDataList() {
        return dataService.getCachedData();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CachedData> getDataById(@PathVariable Integer id) {
        return ResponseEntity.of(dataService.getCachedDataById(id));
    }

    @PostMapping
    public List<CachedData> getDataFromIds(@RequestBody List<Integer> ids) {
        return dataService.getCachedDataByIds(ids);
    }

    @GetMapping(path = "/uuid/{id}")
    public ResponseEntity<CachedData> getDataByUUID(@PathVariable String id) {
        return ResponseEntity.of(dataService.getCachedDataByUUID(id));
    }

    @PostMapping(path = "/uuid")
    public List<CachedData> getDataFromUUIDs(@RequestBody List<String> ids) {
        return dataService.getCachedDataByUUIDs(ids);
    }

    @GetMapping(path = "/discord/{id}")
    public ResponseEntity<CachedData> getDataByDiscord(@PathVariable String id) {
        return ResponseEntity.of(dataService.getCachedDataByDiscordId(id));
    }

    @PostMapping(path = "/discord")
    public List<CachedData> getDataFroDiscordIds(@RequestBody List<String> ids) {
        return dataService.getCachedDataByDiscordIds(ids);
    }
}
