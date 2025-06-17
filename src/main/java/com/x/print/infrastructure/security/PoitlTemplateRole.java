package com.x.print.infrastructure.security;

import com.x.print.domain.model.poitltemplate.PoitlTemplate;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "PoitlTemplateRole", code = PoitlTemplateRole.CODE)
public interface PoitlTemplateRole {
    String CODE = "poitl-template-role";

    @MenuPolicy(menuIds = "PoitlTemplate.browse")
    @ScreenPolicy(screenIds = {"PoitlTemplate.browse", "PoitlTemplate.edit"})
    void screens();

    @EntityAttributePolicy(entityClass = PoitlTemplate.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = PoitlTemplate.class, actions = EntityPolicyAction.ALL)
    void poitlTemplate();
}