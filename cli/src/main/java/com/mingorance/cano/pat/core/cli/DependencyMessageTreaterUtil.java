package com.mingorance.cano.pat.core.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mingorance.cano.pat.core.entity.PatDependency;

public class DependencyMessageTreaterUtil {

    public static String treatPatDependencyList(final List<PatDependency> patDependencies) {
        final Map<String, Map<String, List<String>>> map = new HashMap<>();
        patDependencies.forEach(d -> {
            if (map.get(d.getAppearAlsoInModel().getId()) == null) {
                map.put(d.getAppearAlsoInModel().getId(), new HashMap<>());
            }
            if (map.get(d.getModel().getId()).get(d.getId()) == null) {
                map.get(d.getModel().getId()).put(d.getId(), new ArrayList<>());
            }
            map.get(d.getModel().getId()).get(d.getId()).add(d.getAppearAlsoInModel().getId());
        });

        final StringBuilder stringBuilder = new StringBuilder();
        map.entrySet().forEach(entry -> {
            stringBuilder.append(String.format("Project [%s]", entry.getKey())).append("\n");
            entry.getValue().entrySet().forEach(depEntry -> {
                stringBuilder.append("  ").append(String.format("Dependency [%s] appears in:", depEntry.getKey())).append("\n");
                depEntry.getValue().forEach(value -> stringBuilder.append("        *  ").append(value).append("\n"));
            });
        });

        return stringBuilder.toString();
    }
}
