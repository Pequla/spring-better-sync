package com.pequla.sync.repo;

import com.pequla.sync.entity.CachedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CachedDataRepository extends JpaRepository<CachedData, Integer> {
    Optional<CachedData> findByDiscordId(String discordId);

    Optional<CachedData> findByUuid(String uuid);

    List<CachedData> findByDiscordIdIn(List<String> discordIds);

    List<CachedData> findByUuidIn(List<String> uuids);

    boolean existsByIdAndDiscordId(Integer id, String discordId);

    boolean existsByIdAndUuid(Integer id, String uuid);
}
