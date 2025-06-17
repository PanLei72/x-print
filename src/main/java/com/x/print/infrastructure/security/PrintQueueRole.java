package com.x.print.infrastructure.security;

import com.x.print.domain.model.printqueue.PrintQueue;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "PrintQueueRole", code = PrintQueueRole.CODE)
public interface PrintQueueRole {
    String CODE = "print-queue-role";

    @MenuPolicy(menuIds = "PrintQueue.browse")
    @ScreenPolicy(screenIds = "PrintQueue.browse")
    void screens();

    @EntityAttributePolicy(entityClass = PrintQueue.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = PrintQueue.class, actions = EntityPolicyAction.ALL)
    void printQueue();
}