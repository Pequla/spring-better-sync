package com.pequla.sync.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "config")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConfigData {

    @Id
    @Column(name = "config_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private String value;
}
