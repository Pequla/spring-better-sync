package com.pequla.sync.service;

import com.pequla.sync.entity.Access;
import com.pequla.sync.repo.AccessRepository;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessService {

    private final AccessRepository repository;

    public Page<Access> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Access> getById(Integer id) {
        return repository.findById(id);
    }

    public void saveAccess(ServletRequest request) {
        HttpServletRequest http = (HttpServletRequest) request;

        // Retrieve XFR
        String address = http.getHeader("X-Forwarded-For");
        if (address == null) {
            address = http.getRemoteAddr();
        }

        Optional<Access> optional = repository.findByAddress(address);
        if (optional.isEmpty()) {
            repository.save(Access.builder()
                    .address(address)
                    .path(http.getServletPath())
                    .method(http.getMethod())
                    .createdAt(LocalDateTime.now())
                    .build());
            return;
        }

        Access access = optional.get();
        access.setPath(http.getServletPath());
        access.setMethod(http.getMethod());
        access.setUpdatedAt(LocalDateTime.now());
        repository.save(access);
    }
}