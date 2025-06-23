package com.pequla.sync.service;

import com.pequla.sync.entity.CachedData;
import com.pequla.sync.model.AccountModel;
import com.pequla.sync.model.DataModel;
import com.pequla.sync.repo.CachedDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DataService {

    private final WebService webService;
    private final CachedDataRepository dataRepository;
    private final MemberService memberService;

    public List<CachedData> getCachedData() {
        return dataRepository.findAll();
    }

    public Page<CachedData> getCachedDataPaged(Pageable pageable) {
        return dataRepository.findAll(pageable);
    }

    public Optional<CachedData> getCachedDataById(Integer id) {
        return dataRepository.findById(id);
    }

    public List<CachedData> getCachedDataByIds(List<Integer> ids) {
        return dataRepository.findAllById(ids);
    }

    public Optional<CachedData> getCachedDataByDiscordId(String id) {
        return dataRepository.findByDiscordId(id);
    }

    public List<CachedData> getCachedDataByDiscordIds(List<String> ids) {
        return dataRepository.findByDiscordIdIn(ids);
    }

    public Optional<CachedData> getCachedDataByUUID(String uuid) {
        return dataRepository.findByUuid(uuid);
    }

    public List<CachedData> getCachedDataByUUIDs(List<String> uuids) {
        return dataRepository.findByUuidIn(uuids);
    }

    @Scheduled(fixedRate = 15 * 60000, initialDelay = 15000)
    public void syncCacheWithGuild() {
        log.info("Synchronizing cache with guild");
        synchronizeDiscordAndCache();
        addOrUpdateNewData();
    }

    @Scheduled(cron = "0 0 */2 * * *")
    public void refreshCache() {
        log.info("Validating existing cache");
        List<CachedData> cachedDataList = dataRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (CachedData cachedData : cachedDataList) {
            if (cachedData.getCachedAt().isBefore(now.minusMinutes(15))) {
                // Refresh or remove logic here
                try {
                    Member member = memberService.getMemberById(cachedData.getDiscordId());
                    syncDataForDiscordMember(member);
                } catch (NullPointerException e) {
                    // Remove from cache
                    removeAndLog(cachedData);
                }
            }
        }
    }

    public void synchronizeDiscordAndCache() {
        List<Member> members = memberService.getMembers();
        List<CachedData> cachedDataList = dataRepository.findAll();

        // Convert Discord Member list to a set of Discord IDs for quick lookup
        Set<String> discordIds = members.stream()
                .map(ISnowflake::getId)
                .collect(Collectors.toSet());

        for (CachedData cachedData : cachedDataList) {
            if (!discordIds.contains(cachedData.getDiscordId())) {
                // Remove from cache if not in guild anymore
                removeAndLog(cachedData);
            }
        }
    }

    public void addOrUpdateNewData() {
        List<Member> members = memberService.getMembers();
        List<CachedData> cachedDataList = dataRepository.findAll();

        // Extract UUIDs from the cache to identify new members
        Set<String> cachedDiscordIds = cachedDataList.stream()
                .map(CachedData::getDiscordId)
                .collect(Collectors.toSet());

        for (Member member : members) {
            if (!cachedDiscordIds.contains(member.getId())) {
                // Create new cache entry for new member
                syncDataForDiscordMember(member);
            }
        }
    }

    private void removeAndLog(CachedData cachedData) {
        log.info("Removing data for {}", cachedData.getDiscordId());
        dataRepository.delete(cachedData);
    }

    private void syncDataForDiscordMember(Member member) {
        try {
            DataModel data = webService.getDataByDiscordId(member.getId());
            AccountModel account = webService.getAccount(data.getUuid());
            dataRepository.save(CachedData.builder()
                    .id(data.getId())
                    .discordId(member.getId())
                    .uuid(account.getId().replace("-", ""))
                    .tag(member.getEffectiveName())
                    .avatar(member.getEffectiveAvatarUrl())
                    .name(account.getName())
                    .guildId(data.getGuild().getDiscordId())
                    .createdAt(data.getCreatedAt())
                    .cachedAt(LocalDateTime.now())
                    .build());
            log.info("Successfully saved data for {}", member.getId());
        } catch (Exception e) {
            log.error("Failed to save data for {}", member.getId());
        }
    }
}
