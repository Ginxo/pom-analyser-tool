package com.mingorance.cano.pat.core.service;

import java.io.File;
import java.util.List;

import com.mingorance.cano.pat.core.entity.EffectiveModel;
import org.apache.maven.model.Dependency;
import org.junit.Assert;
import org.junit.Test;

public class ModelServiceTest {

    @Test
    public void test_project1Load() {
        // Arrange
        final String pomFilePath = this.getClass().getClassLoader().getResource("project1/pom.xml").getPath();

        // Act
        final EffectiveModel result = ModelService.getInstance().loadModel(new File(pomFilePath));

        // Assert
        Assert.assertEquals("com.mingorance.cano.pat:parent:pom:0.0.1-SNAPSHOT", result.getId());
        Assert.assertEquals(2, result.getChildModels().size());
        Assert.assertEquals(result, result.getChildModels().get(0).getParentModel());
        Assert.assertEquals("com.mingorance.cano.pat:core1:jar:0.0.1-SNAPSHOT", result.getChildModels().get(0).getId());
        Assert.assertEquals(result, result.getChildModels().get(1).getParentModel());
        Assert.assertEquals("com.mingorance.cano.pat:core2:pom:0.0.1-SNAPSHOT", result.getChildModels().get(1).getId());
        Assert.assertEquals(0, result.getChildModels().get(0).getChildModels().size());
        Assert.assertEquals(1, result.getChildModels().get(1).getChildModels().size());
        Assert.assertEquals(result.getChildModels().get(1), result.getChildModels().get(1).getChildModels().get(0).getParentModel());
        Assert.assertEquals("com.mingorance.cano.pat:core2_2:jar:0.0.1-SNAPSHOT", result.getChildModels().get(1).getChildModels().get(0).getId());
    }

    @Test
    public void test_loadBoms() {
        // Arrange
        final String pomFilePath = this.getClass().getClassLoader().getResource("kie/pom.xml").getPath();
        final EffectiveModel patModel = ModelService.getInstance().loadModel(new File(pomFilePath));

        // Act
        final List<Dependency> dependencies = ModelService.getInstance().loadDeclaredBoms(patModel);

        // Assert
        Assert.assertEquals(13, dependencies.size());
    }
}
