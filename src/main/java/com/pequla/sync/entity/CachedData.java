package com.pequla.sync.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CachedData {

    @Id
    @Column(name = "data_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false, unique = true)
    private String discordId;

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    private String avatar;

    @Column(nullable = false)
    private String guildId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime cachedAt = LocalDateTime.now();
}