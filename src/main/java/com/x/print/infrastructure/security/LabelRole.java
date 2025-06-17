package com.x.print.infrastructure.security;

import com.x.print.domain.model.label.Label;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "LabelRole", code = LabelRole.CODE)
public interface LabelRole {
    String CODE = "label-role";

    @MenuPolicy(menuIds = "Label.browse")
    @ScreenPolicy(screenIds = "Label.browse")
    void screens();

    @EntityAttributePolicy(entityClass = Label.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Label.class, actions = EntityPolicyAction.ALL)
    void label();
}