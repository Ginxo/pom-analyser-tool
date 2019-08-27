package com.mingorance.cano.pat.core.service;

import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.junit.Assert;
import org.junit.Test;

public class PomServiceTest {

    @Test
    public void test_read_simple() {
        // Act
        final Model model = PomService.getInstance().readPom(this.getClass().getClassLoader().getResource("pom-test.xml").getPath());

        // Assert
        Assert.assertEquals("core", model.getArtifactId());
    }

    @Test
    public void test_loadModules() {
        // Arrange
        final Model model = PomService.getInstance().readPom(this.getClass().getClassLoader().getResource("project1/pom.xml").getPath());

        // Act
        final List<Model> modules = PomService.getInstance().loadModules(model);

        // Assert
        Assert.assertEquals("parent", model.getArtifactId());
        Assert.assertEquals(1, modules.size());
        Assert.assertEquals("core", modules.get(0).getArtifactId());
        Assert.assertEquals("../pom.xml", modules.get(0).getParent().getRelativePath());
    }


    @Test
    public void test_loadParent() {
        // Arrange
        final Model model = PomService.getInstance().readPom(this.getClass().getClassLoader().getResource("project1/pom.xml").getPath());
        final List<Model> modules = PomService.getInstance().loadModules(model);
        final Model child = modules.get(0);

        // Act
        final Model parent = PomService.getInstance().loadParent(child);
        // Assert
        Assert.assertEquals(model.getId(), parent.getId());
    }

    @Test
    public void test_check_dependency_with_version() {
        // Arrange
        final Model model = PomService.getInstance().readPom(this.getClass().getClassLoader().getResource("project1/core/pom.xml").getPath());
        final List<Dependency> dependencies = model.getDependencies();

        // Act
        final Dependency dependency = dependencies.stream().filter(d -> "org.apache.maven".equals(d.getGroupId())).findFirst().get();

        // Assert
        Assert.assertNotNull(dependency.getVersion());
    }

    @Test
    public void test_check_dependency_without_version() {
        // Arrange
        final Model model = PomService.getInstance().readPom(this.getClass().getClassLoader().getResource("project1/core/pom.xml").getPath());
        final List<Dependency> dependencies = model.getDependencies();

        // Act
        final Dependency dependency = dependencies.stream().filter(d -> "org.projectlombok".equals(d.getGroupId())).findFirst().get();

        // Assert
        Assert.assertNull(dependency.getVersion());
    }

    @Test
    public void test_check_dependency_with_classifier() {
        // Arrange
        final Model model = PomService.getInstance().readPom(this.getClass().getClassLoader().getResource("project1/core/pom.xml").getPath());
        final List<Dependency> dependencies = model.getDependencies();

        // Act
        final Dependency dependency = dependencies.stream().filter(d -> "org.apache.maven".equals(d.getGroupId())).findFirst().get();

        // Assert
        Assert.assertNotNull(dependency.getClassifier());
    }

    @Test
    public void test_check_dependency_without_classifier() {
        // Arrange
        final Model model = PomService.getInstance().readPom(this.getClass().getClassLoader().getResource("project1/core/pom.xml").getPath());
        final List<Dependency> dependencies = model.getDependencies();

        // Act
        final Dependency dependency = dependencies.stream().filter(d -> "org.projectlombok".equals(d.getGroupId())).findFirst().get();

        // Assert
        Assert.assertNull(dependency.getClassifier());
    }
}
