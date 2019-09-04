package com.mingorance.cano.pat.core.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class Project {

    @NonNull
    private String id;

    @Getter(lazy = true)
    private final List<EffectiveModel> models = new ArrayList<>();
}
