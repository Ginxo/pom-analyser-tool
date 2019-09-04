package com.mingorance.cano.pat.core.service;

import com.mingorance.cano.pat.core.entity.EffectiveModel;
import com.mingorance.cano.pat.core.entity.Project;
import org.junit.Assert;
import org.junit.Test;

public class ProjectServiceTest {

    @Test
    public void test_project1Load() {
        // Arrange
        final String projectPath = this.getClass().getClassLoader().getResource("project1").getPath();

        // Act
        final Project result = ProjectService.getInstance().loadProject(projectPath);

        // Assert
        Assert.assertEquals(2, result.getModels().size());
    }

    @Test
    public void test_loadModel() {
        // Arrange
        final String projectPath = this.getClass().getClassLoader().getResource("project1").getPath();
        final Project project = ProjectService.getInstance().loadProject(projectPath);

        // Act
        final EffectiveModel result = ProjectService.getInstance().getModel(project, "com.mingorance.cano.pat:core2_2:jar:0.0.1-SNAPSHOT");

        // Assert
        Assert.assertNotNull(result);
    }

    @Test
    public void test_isDependencyProjectModel_true() {
        // Arrange
        final String projectPath = this.getClass().getClassLoader().getResource("project1").getPath();
        final Project project = ProjectService.getInstance().loadProject(projectPath);

        // Act
        final Boolean result = ProjectService.getInstance().isDependencyProjectModel(project, "com.mingorance.cano.pat", "core2_2");

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void test_isDependencyProjectModel_false() {
        // Arrange
        final String projectPath = this.getClass().getClassLoader().getResource("project1").getPath();
        final Project patProject = ProjectService.getInstance().loadProject(projectPath);

        // Act
        final Boolean result = ProjectService.getInstance().isDependencyProjectModel(patProject, "com.mingorance.cano.pat", "core2_3");

        // Assert
        Assert.assertFalse(result);
    }
}
