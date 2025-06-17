package com.x.print.infrastructure.security;

import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "PrinterRole", code = PrinterRole.CODE)
public interface PrinterRole {
    String CODE = "printer-role";

    @MenuPolicy(menuIds = "Printer.browse")
    @ScreenPolicy(screenIds = "Printer.browse")
    void screens();
}