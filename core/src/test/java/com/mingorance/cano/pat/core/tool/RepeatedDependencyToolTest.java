package com.mingorance.cano.pat.core.tool;

import java.util.List;

import com.mingorance.cano.pat.core.service.PomService;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.junit.Assert;
import org.junit.Test;

public class RepeatedDependencyToolTest {

    @Test
    public void test_repeated_dependency() {
        // Arrange
        final Model model = PomService.getInstance().readPom(this.getClass().getClassLoader().getResource("pom-repeated.xml").getPath());

        // Act
        final List<Dependency> result = RepeatedDependencyTool.getInstance().run(model);

        // Assert
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("junit", result.get(0).getArtifactId());
    }

    @Test
    public void test_non_repeated_dependency() {
        // Arrange
        final Model model = PomService.getInstance().readPom(this.getClass().getClassLoader().getResource("pom-nonrepeated.xml").getPath());

        // Act
        final List<Dependency> result = RepeatedDependencyTool.getInstance().run(model);

        // Assert
        Assert.assertEquals(0, result.size());
    }
}
