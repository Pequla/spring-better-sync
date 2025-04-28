package com.pequla.sync;

import com.pequla.sync.service.ConfigService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
@Getter
@Slf4j
public class DiscordBot implements DisposableBean {

    private final ConfigService service;
    private final JDA jda;

    public DiscordBot(ConfigService service) {
        log.info("Connecting to Discord API");
        this.service = service;
        this.jda = JDABuilder.createDefault(service.getBotToken())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setActivity(Activity.playing("Minecraft"))
                .build();
    }

    public void registerListener(ListenerAdapter adapter) {
        log.info("Registering listener {}", adapter.getClass().getSimpleName());
        jda.addEventListener(adapter);
    }

    @Override
    public void destroy() {
        log.info("Disconnecting from Discord API");
        jda.shutdownNow();
    }
}
