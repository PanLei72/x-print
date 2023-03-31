package com.x.print.ui.screen.printer;

import com.x.print.domain.model.printer.IPrinterService;
import io.jmix.core.LoadContext;
import io.jmix.ui.screen.*;
import com.x.print.domain.model.printer.Printer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@UiController("Printer.browse")
@UiDescriptor("printer-browse.xml")
@LookupComponent("printersTable")
public class PrinterBrowse extends StandardLookup<Printer> {
    @Autowired
    private IPrinterService printerService;

    @Install(to = "printersDl", target = Target.DATA_LOADER)
    private List<Printer> printersDlLoadDelegate(LoadContext<Printer> loadContext) {
        // Here you can load entities from an external store
        List<Printer> printers =printerService.lookupPrint();

        return printers;
    }
}