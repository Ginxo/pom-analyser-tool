package com.mingorance.cano.pat.core.util;

import org.apache.maven.model.Dependency;

public class DependencyUtil {

    public static Boolean compareDependencies(final Dependency dependency1, final Dependency dependency2) {
        return dependency1.getArtifactId().equals(dependency2.getArtifactId()) &&
                dependency1.getGroupId().equals(dependency2.getGroupId()) &&
                (dependency1.getVersion() != null ? dependency1.getVersion().equals(dependency2.getVersion()) : dependency2.getVersion() == null) &&
                (dependency1.getClassifier() != null ? dependency1.getClassifier().equals(dependency2.getClassifier()) : dependency2.getClassifier() == null);

    }

    public static Boolean compareDependenciesNoneVersion(final Dependency dependency1, final Dependency dependency2) {
        return dependency1.getArtifactId().equals(dependency2.getArtifactId()) &&
                dependency1.getGroupId().equals(dependency2.getGroupId()) &&
                (dependency1.getVersion() != null && dependency2.getVersion() != null) &&
                (dependency1.getClassifier() != null ? dependency1.getClassifier().equals(dependency2.getClassifier()) : dependency2.getClassifier() == null);

    }
}
