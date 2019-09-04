package com.mingorance.cano.pat.core.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mingorance.cano.pat.core.entity.PatDependency;
import com.mingorance.cano.pat.core.service.PomService;
import com.mingorance.cano.pat.core.util.DependencyUtil;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

public class RepeatedDefinitionDependencyTool {

    private static RepeatedDefinitionDependencyTool instance;

    private RepeatedDefinitionDependencyTool() {
    }

    public static RepeatedDefinitionDependencyTool getInstance() {
        if (instance == null) {
            instance = new RepeatedDefinitionDependencyTool();
        }
        return instance;
    }

    public List<PatDependency> run(final String pomFilePath) {
        return this.run(PomService.getInstance().readPom(pomFilePath));
    }

    public List<PatDependency> run(final Model model) {
        return this.run(model, new ArrayList<>());
    }

    private List<PatDependency> run(final Model model, final List<PatDependency> repeatedDependencies) {
        if (model.getDependencyManagement() != null) {
            this.run(model, model.getDependencyManagement().getDependencies(), model, model.getDependencies(), repeatedDependencies);
            this.check_children(model.getDependencyManagement().getDependencies(), model, repeatedDependencies);
            this.check_parent(model, repeatedDependencies);
        }
        return repeatedDependencies;
    }

    private void run(final Model model, final List<Dependency> dependencies, final Model dependenciesToCheckModel, final List<Dependency> dependenciesToCheck, final List<PatDependency> repeatedDependencies) {
        if (dependencies != null && dependenciesToCheck != null) {
            this.treatDependencies(model, dependencies, dependenciesToCheckModel, dependenciesToCheck)
                    .collect(Collectors.toCollection(() -> repeatedDependencies));
        }
    }

    private Stream<PatDependency> treatDependencies(final Model model, final List<Dependency> dependencies, final Model dependenciesToCheckModel, final List<Dependency> dependenciesToCheck) {
        return dependencies
                .stream()
                .map(dep -> this.checkDependencies(model, dep, dependenciesToCheckModel, dependenciesToCheck))
                .flatMap(List::stream);
    }

    private List<PatDependency> checkDependencies(final Model dependencyModel, final Dependency dependency, final Model dependenciesModel, final List<Dependency> dependencies) {
        return dependencies.stream()
                .filter(dep -> DependencyUtil.compareDependenciesNoneVersion(dependency, dep))
                .map(dep -> new PatDependency(dependencyModel, dependency, dependenciesModel))
                .collect(Collectors.toList());
    }

    private void check_children(final List<Dependency> dependencyManagementDependencies, final Model model, final List<PatDependency> repeatedDependencies) {
        PomService.getInstance().loadModuleModels(model).forEach(child -> {
            this.run(model, dependencyManagementDependencies, child, child.getDependencies(), repeatedDependencies);
            if (child.getDependencyManagement() != null) {
                this.run(model, model.getDependencyManagement().getDependencies(), child, child.getDependencyManagement().getDependencies(), repeatedDependencies);
            }
            this.check_children(dependencyManagementDependencies, child, repeatedDependencies);
            this.run(child, repeatedDependencies);
        });
    }

    private void check_parent(final Model child, final List<PatDependency> repeatedDependencies) {
        final Model parent = PomService.getInstance().loadParent(child);
        if (parent != null) {
            if (child.getDependencyManagement() != null) {
                if (parent.getDependencyManagement() != null) {
                    this.run(child, child.getDependencyManagement().getDependencies(), parent, parent.getDependencyManagement().getDependencies(), repeatedDependencies);
                }
                this.run(child, child.getDependencyManagement().getDependencies(), parent, parent.getDependencies(), repeatedDependencies);
            }
            this.check_parent(parent, repeatedDependencies);
        }
    }
}
