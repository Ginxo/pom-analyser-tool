package com.mingorance.cano.pat.core.tool;

import java.util.ArrayList;
import java.util.List;

import com.mingorance.cano.pat.core.service.PomService;
import com.mingorance.cano.pat.core.util.DependencyUtil;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

public class RepeatedDependencyTool {

    private static RepeatedDependencyTool instance;

    private RepeatedDependencyTool() {
    }

    public static RepeatedDependencyTool getInstance() {
        if (instance == null) {
            instance = new RepeatedDependencyTool();
        }
        return instance;
    }

    public List<Dependency> run(final String pomFilePath) {
        return this.run(PomService.getInstance().readPom(pomFilePath));
    }

    public List<Dependency> run(final Model model) {
        final List<Dependency> repeatedDependencies = new ArrayList<>();
        for (int i = 0; i < model.getDependencies().size(); i++) {
            for (int j = i + 1; j < model.getDependencies().size(); j++) {
                if (DependencyUtil.compareDependencies(model.getDependencies().get(i), model.getDependencies().get(j))) {
                    repeatedDependencies.add(model.getDependencies().get(i));
                }
            }
        }
        return repeatedDependencies;
    }
}
