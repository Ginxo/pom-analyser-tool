package com.mingorance.cano.pat.core.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mingorance.cano.pat.core.entity.EffectiveModel;
import com.mingorance.cano.pat.core.entity.EffectiveModel;
import org.apache.maven.model.Dependency;

public class ModelService {

    private static ModelService instance;

    private ModelService() {
    }

    public static ModelService getInstance() {
        if (instance == null) {
            instance = new ModelService();
        }
        return instance;
    }

    public EffectiveModel loadModel(final File pomFile) {
        final EffectiveModel patModel = new EffectiveModel(PomService.getInstance().readPom(pomFile), null);
        this.loadChildren(patModel);
        return patModel;
    }

    public void loadChildren(final EffectiveModel model) {
        model.getChildModels().addAll(PomService.getInstance().loadModuleModels(model).stream()
                                              .map(moduleModel -> new EffectiveModel(moduleModel, model))
                                              .map(childEffectiveModel ->
                                                   {
                                                       this.loadChildren(childEffectiveModel);
                                                       return childEffectiveModel;
                                                   }).collect(Collectors.toList()));
    }

    public List<Dependency> loadDeclaredBoms(final EffectiveModel patModel) {
        final List<Dependency> dependencies = new ArrayList<>();
        if (patModel.getDependencyManagement() != null && patModel.getDependencyManagement().getDependencies() != null) {
            dependencies.addAll(patModel.getDependencyManagement().getDependencies().stream()
                                        .filter(dependency -> "import".equals(dependency.getScope()))
                                        .collect(Collectors.toList()));
        }
        if (patModel.getParentModel() != null) {
            dependencies.addAll(this.loadDeclaredBoms(patModel.getParentModel()));
        }
        return dependencies;
    }
}
