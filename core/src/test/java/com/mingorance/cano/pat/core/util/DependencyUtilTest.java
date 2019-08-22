package com.mingorance.cano.pat.core.util;

import org.apache.maven.model.Dependency;
import org.junit.Assert;
import org.junit.Test;

public class DependencyUtilTest {

    @Test
    public void test_simple() {
        final Dependency dependency1 = new Dependency();
        dependency1.setArtifactId("a1");
        dependency1.setGroupId("g1");
        dependency1.setClassifier("c1");
        dependency1.setVersion("v1");

        final Dependency dependency2 = new Dependency();
        dependency2.setArtifactId("a1");
        dependency2.setGroupId("g1");
        dependency2.setClassifier("c1");
        dependency2.setVersion("v1");

        // Act
        final Boolean result = DependencyUtil.compareDependencies(dependency1, dependency2);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void test_version_null() {
        final Dependency dependency1 = new Dependency();
        dependency1.setArtifactId("a1");
        dependency1.setGroupId("g1");
        dependency1.setClassifier("c1");

        final Dependency dependency2 = new Dependency();
        dependency2.setArtifactId("a1");
        dependency2.setGroupId("g1");
        dependency2.setClassifier("c1");

        // Act
        final Boolean result = DependencyUtil.compareDependencies(dependency1, dependency2);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void test_classifier_null() {
        final Dependency dependency1 = new Dependency();
        dependency1.setArtifactId("a1");
        dependency1.setGroupId("g1");
        dependency1.setVersion("v1");

        final Dependency dependency2 = new Dependency();
        dependency2.setArtifactId("a1");
        dependency2.setGroupId("g1");
        dependency2.setVersion("v1");

        // Act
        final Boolean result = DependencyUtil.compareDependencies(dependency1, dependency2);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void test_different_artifactId() {
        final Dependency dependency1 = new Dependency();
        dependency1.setArtifactId("a1");
        dependency1.setGroupId("g1");
        dependency1.setClassifier("c1");
        dependency1.setVersion("v1");

        final Dependency dependency2 = new Dependency();
        dependency2.setArtifactId("a2");
        dependency2.setGroupId("g1");
        dependency2.setClassifier("c1");
        dependency2.setVersion("v1");

        // Act
        final Boolean result = DependencyUtil.compareDependencies(dependency1, dependency2);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void test_different_groupId() {
        final Dependency dependency1 = new Dependency();
        dependency1.setArtifactId("a1");
        dependency1.setGroupId("g1");
        dependency1.setClassifier("c1");
        dependency1.setVersion("v1");

        final Dependency dependency2 = new Dependency();
        dependency2.setArtifactId("a1");
        dependency2.setGroupId("g2");
        dependency2.setClassifier("c1");
        dependency2.setVersion("v1");

        // Act
        final Boolean result = DependencyUtil.compareDependencies(dependency1, dependency2);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void test_different_classifier() {
        final Dependency dependency1 = new Dependency();
        dependency1.setArtifactId("a1");
        dependency1.setGroupId("g1");
        dependency1.setClassifier("c1");
        dependency1.setVersion("v1");

        final Dependency dependency2 = new Dependency();
        dependency2.setArtifactId("a1");
        dependency2.setGroupId("g1");
        dependency2.setVersion("v1");

        // Act
        final Boolean result = DependencyUtil.compareDependencies(dependency1, dependency2);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void test_different_version() {
        final Dependency dependency1 = new Dependency();
        dependency1.setArtifactId("a1");
        dependency1.setGroupId("g1");
        dependency1.setClassifier("c1");
        dependency1.setVersion("v1");

        final Dependency dependency2 = new Dependency();
        dependency2.setArtifactId("a1");
        dependency2.setGroupId("g1");
        dependency2.setClassifier("c1");

        // Act
        final Boolean result = DependencyUtil.compareDependencies(dependency1, dependency2);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void test_notNone_version_true() {
        final Dependency dependency1 = new Dependency();
        dependency1.setArtifactId("a1");
        dependency1.setGroupId("g1");
        dependency1.setClassifier("c1");
        dependency1.setVersion("v1");

        final Dependency dependency2 = new Dependency();
        dependency2.setArtifactId("a1");
        dependency2.setGroupId("g1");
        dependency2.setClassifier("c1");
        dependency2.setVersion("v2");

        // Act
        final Boolean result = DependencyUtil.compareDependenciesNoneVersion(dependency1, dependency2);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void test_notNone_version_false() {
        final Dependency dependency1 = new Dependency();
        dependency1.setArtifactId("a1");
        dependency1.setGroupId("g1");
        dependency1.setClassifier("c1");

        final Dependency dependency2 = new Dependency();
        dependency2.setArtifactId("a1");
        dependency2.setGroupId("g1");
        dependency2.setClassifier("c1");
        dependency2.setVersion("v2");

        // Act
        final Boolean result = DependencyUtil.compareDependenciesNoneVersion(dependency1, dependency2);

        // Assert
        Assert.assertFalse(result);
    }
}
