package com.x.print.infrastructure.security;

import com.x.print.domain.model.labeldatadefinition.LabelDataDefinition;
import com.x.print.domain.model.labeldatadefinition.LabelVariable;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "LabelDataDefinition", code = LabelDataDefinitionRole.CODE)
public interface LabelDataDefinitionRole {
    String CODE = "label-data-definition";

    @MenuPolicy(menuIds = "LabelDataDefinition.browse")
    @ScreenPolicy(screenIds = {"LabelDataDefinition.browse", "LabelDataDefinition.edit", "LabelVariable.edit"})
    void screens();

    @EntityAttributePolicy(entityClass = LabelDataDefinition.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = LabelDataDefinition.class, actions = EntityPolicyAction.ALL)
    void labelDataDefinition();

    @EntityAttributePolicy(entityClass = LabelVariable.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = LabelVariable.class, actions = EntityPolicyAction.ALL)
    void labelVariable();
}