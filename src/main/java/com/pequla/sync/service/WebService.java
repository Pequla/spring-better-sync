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
                .uri("https://link.samifying.com/api/data/discord/" + discordId + "?log=0")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, rsp ->
                        Mono.error(new DataNotFoundException("User with ID " + discordId + " not found"))
                )
                .bodyToMono(DataModel.class)
                .block();
    }

    public AccountModel getAccount(String uuid) {
        return webClient.get()
                .uri("https://link.samifying.com/api/cache/uuid/" + uuid + "?log=0")
                .retrieve()
                .bodyToMono(AccountModel.class)
                .block();
    }
}
