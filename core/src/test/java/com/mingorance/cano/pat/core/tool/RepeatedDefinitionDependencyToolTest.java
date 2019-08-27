package com.mingorance.cano.pat.core.tool;

import java.util.List;

import com.mingorance.cano.pat.core.entity.PatDependency;
import com.mingorance.cano.pat.core.service.PomService;
import org.apache.maven.model.Model;
import org.junit.Assert;
import org.junit.Test;

public class RepeatedDefinitionDependencyToolTest {

    @Test
    public void test() {
        // Arrange
        final Model model = PomService.getInstance().readPom(this.getClass().getClassLoader().getResource("dep_management/pom.xml").getPath());

        // Act
        final List<PatDependency> result = RepeatedDefinitionDependencyTool.getInstance().run(model);

        // Assert
        Assert.assertEquals(5, result.size());
        final String[][] expectedPatDependencies = {
                new String[]{"group8:artifact8:null:jar:8", "org.test:test-parent:pom:0.0.1-SNAPSHOT", "org.test:test-parent:pom:0.0.1-SNAPSHOT"},
                new String[]{"group3:artifact3:null:jar:3", "org.test:test-parent:pom:0.0.1-SNAPSHOT", "org.drools:drools-bom:jar:[inherited]"},
                new String[]{"group1:artifact1:null:jar:1", "org.test:test-parent:pom:0.0.1-SNAPSHOT", "org.drools:drools-bom:jar:[inherited]"},
                new String[]{"group1:artifact1:null:jar:1", "org.drools:drools-bom:jar:[inherited]", "org.test:test-parent:pom:0.0.1-SNAPSHOT"},
                new String[]{"group2:artifact2:null:jar:2", "org.drools:drools-bom:jar:[inherited]", "org.test:test-parent:pom:0.0.1-SNAPSHOT"}
        };

        this.assertDependency(result, expectedPatDependencies);
    }

    private void assertDependency(final List<PatDependency> result, String[][] strings) {
        Assert.assertEquals(strings.length, result.size());
        for (int i = 0; i < strings.length; i++) {
            Assert.assertEquals(String.format("Index %s, position %s", i, 0), strings[i][0], result.get(i).getId());
            Assert.assertEquals(String.format("Index %s, position %s", i, 1), strings[i][1], result.get(i).getModel().getId());
            Assert.assertEquals(String.format("Index %s, position %s", i, 2), strings[i][2], result.get(i).getAppearAlsoInModel().getId());
        }
    }
}
