package com.x.print.infrastructure.security;

import com.x.print.domain.model.labeldesign.LabelDesign;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "LabelDesignRole", code = LabelDesignRole.CODE)
public interface LabelDesignRole {
    String CODE = "label-design-role";

    @MenuPolicy(menuIds = "LabelDesign.browse")
    @ScreenPolicy(screenIds = {"LabelDesign.browse", "LabelDesign.edit"})
    void screens();

    @EntityAttributePolicy(entityClass = LabelDesign.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = LabelDesign.class, actions = EntityPolicyAction.ALL)
    void labelDesign();
}