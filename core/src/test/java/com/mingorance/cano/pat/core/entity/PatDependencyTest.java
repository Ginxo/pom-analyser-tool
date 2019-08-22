package com.mingorance.cano.pat.core.entity;

import org.apache.maven.model.Dependency;
import org.junit.Assert;
import org.junit.Test;

public class PatDependencyTest {

    @Test
    public void test_getId() {
        // Arrange
        final Dependency dependency = new Dependency();
        dependency.setArtifactId("a1");
        dependency.setGroupId("g1");
        dependency.setClassifier("c1");
        dependency.setType("t1");
        dependency.setVersion("v1");

        final PatDependency patDependency = new PatDependency(null, dependency, null);

        // Act
        final String patDependencyId = patDependency.getId();

        // Assert
        Assert.assertEquals("g1:a1:c1:t1:v1", patDependencyId);
    }

    @Test
    public void test_getId_nullValues() {
        // Arrange
        final Dependency dependency = new Dependency();
        dependency.setArtifactId("a1");
        dependency.setGroupId("g1");
        dependency.setType("t1");

        final PatDependency patDependency = new PatDependency(null, dependency, null);

        // Act
        final String patDependencyId = patDependency.getId();

        // Assert
        Assert.assertEquals("g1:a1:null:t1:null", patDependencyId);
    }
}
