package com.pequla.sync.service;

import com.pequla.sync.error.DataNotFoundException;
import com.pequla.sync.model.AccountModel;
import com.pequla.sync.model.DataModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WebService {

    private final WebClient webClient;

    public DataModel getDataByDiscordId(String discordId) {
        return webClient.get()
                .uri("https://link.samifying.com/api/data/discord/" + discordId)
                .header("X-Name", "BetterSync/1.0")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, rsp ->
                        Mono.error(new DataNotFoundException("User with ID " + discordId + " not found"))
                )
                .bodyToMono(DataModel.class)
                .block();
    }

    public AccountModel getAccount(String uuid) {
        return webClient.get()
                .uri("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                .header("Accept", "application/json")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Connection", "keep-alive")
                .header("DNT", "1")
                .header("Referer", "https://beocraft.net/")
                .retrieve()
                .bodyToMono(AccountModel.class)
                .block();
    }
}
