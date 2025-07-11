package com.pequla.sync.controller;

import com.pequla.sync.entity.Access;
import com.pequla.sync.service.AccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/api/access")
public class AccessController {

    private final AccessService service;

    @GetMapping
    public Page<Access> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Access> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getById(id));
    }
}