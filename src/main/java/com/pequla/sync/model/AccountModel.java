package com.pequla.sync.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AccountModel {
    private String id;
    private String name;
    private List<Object> properties;
    private List<Object> profileActions;
}
