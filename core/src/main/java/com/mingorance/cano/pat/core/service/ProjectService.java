package com.mingorance.cano.pat.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mingorance.cano.pat.core.entity.EffectiveModel;
import com.mingorance.cano.pat.core.entity.Project;
import com.mingorance.cano.pat.core.util.PomFileUtil;
import lombok.extern.java.Log;

@Log
public class ProjectService {

    private static ProjectService instance;

    private ProjectService() {
    }

    public static ProjectService getInstance() {
        if (instance == null) {
            instance = new ProjectService();
        }
        return instance;
    }

    public Project loadProject(final String projectPath) {
        final Project project = new Project(UUID.randomUUID().toString());
        project.getModels().addAll(PomFileUtil.getProjectPomFiles(projectPath)
                                           .map(ModelService.getInstance()::loadModel)
                                           .collect(Collectors.toList()));
        return project;
    }

    public Boolean isDependencyProjectModel(final Project project, final String groupId, final String artifactId) {
        return this.getModel(project, groupId, artifactId) != null;
    }

    public List<EffectiveModel> getAllModels(final Project project) {
        final List<EffectiveModel> patModelList = new ArrayList<>();
        project.getModels().forEach(patModel -> this.loadAllModels(patModel, patModelList));
        return patModelList;
    }

    private void loadAllModels(final EffectiveModel model, final List<EffectiveModel> modelList) {
        modelList.add(model);
        if (model.getChildModels() != null) {
            model.getChildModels().forEach(childModel -> this.loadAllModels(childModel, modelList));
        }
    }

    public EffectiveModel getModel(final Project project, final String modelId) {
        return this.getAllModels(project).stream()
                .filter(model -> modelId.equals(model.getId()))
                .findFirst()
                .orElse(null);
    }

    public EffectiveModel getModel(final Project project, final String groupId, final String artifactId) {
        return this.getAllModels(project).stream()
                .filter(model -> groupId.equals(model.getGroupId()) && artifactId.equals(model.getArtifactId()))
                .findFirst()
                .orElse(null);
    }
}
