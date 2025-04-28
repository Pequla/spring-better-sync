package com.pequla.sync.service;

import com.pequla.sync.repo.ConfigDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigService {

    private final ConfigDataRepository repository;

    public String getBotToken() {
        return repository.findByKey("bot.token").orElseThrow().getValue();
    }

    public String getGuildId() {
        return repository.findByKey("guild.id").orElseThrow().getValue();
    }
}
