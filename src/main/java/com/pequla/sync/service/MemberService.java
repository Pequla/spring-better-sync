package com.pequla.sync.service;

import com.pequla.sync.DiscordBot;
import com.pequla.sync.model.MemberModel;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ConfigService service;
    private final DiscordBot discordBot;

    public List<Member> getMembers() {
        return Objects.requireNonNull(discordBot.getJda().getGuildById(service.getGuildId())).getMembers();
    }

    public Member getMemberById(String id) {
        Guild guild = discordBot.getJda().getGuildById(service.getGuildId());
        assert guild != null;
        return guild.getMemberById(id);
    }

    public List<MemberModel> getMappedMembers() {
        return getMembers().stream().map(m -> MemberModel.builder()
                .id(m.getId())
                .name(m.getEffectiveName())
                .joinedAt(m.getTimeJoined().toLocalDateTime())
                .build()).toList();
    }
}
