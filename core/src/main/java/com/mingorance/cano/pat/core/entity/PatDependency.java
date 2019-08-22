package com.mingorance.cano.pat.core.entity;

import lombok.Getter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

import java.lang.reflect.InvocationTargetException;

@Getter
public class PatDependency extends Dependency {
    private Model model;
    private Model appearAlsoInModel;

    public PatDependency(final Model model, final Dependency dependency, final Model appearAlsoInModel) {
        try {
            BeanUtils.copyProperties(this, dependency);
            this.model = model;
            this.appearAlsoInModel = appearAlsoInModel;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error copying Dependency to DependencyPat", e);
        }
    }

    public String getId() {
        return String.format("%s:%s:%s:%s:%s", this.getGroupId(), this.getArtifactId(), this.getClassifier(), this.getType(), this.getVersion());
    }
}
