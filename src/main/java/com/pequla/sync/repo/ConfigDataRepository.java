package com.pequla.sync.repo;

import com.pequla.sync.entity.ConfigData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigDataRepository extends JpaRepository<ConfigData, Integer> {

    Optional<ConfigData> findByKey(String key);
}
