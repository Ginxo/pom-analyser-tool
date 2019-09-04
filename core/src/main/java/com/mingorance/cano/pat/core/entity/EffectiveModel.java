package com.mingorance.cano.pat.core.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.maven.model.Model;

@Getter
public class EffectiveModel extends Model {

    private Model originalModel;
    private EffectiveModel parentModel;
    @Getter(lazy = true)
    private final List<EffectiveModel> childModels = new ArrayList<>();

    public EffectiveModel(final Model originalModel, final EffectiveModel parentModel) {
        try {
            BeanUtils.copyProperties(this, originalModel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error copying Model to EffectiveModel", e);
        }
        this.originalModel = originalModel;
        this.parentModel = parentModel;
        this.overrideInherited();
    }

    private void overrideInherited() {
        if (this.parentModel != null) {
            if (this.getVersion() == null) {
                this.setVersion(parentModel.getVersion());
            }
            if (this.getGroupId() == null) {
                this.setGroupId(parentModel.getGroupId());
            }
        } else {
            this.overrideInheritedFromParentDefinition();
        }
    }

    private void overrideInheritedFromParentDefinition() {
        if (this.getParent() != null) {
            if (this.getVersion() == null) {
                this.setVersion(this.getParent().getVersion());
            }
            if (this.getGroupId() == null) {
                this.setGroupId(this.getParent().getGroupId());
            }
        }
    }
}
