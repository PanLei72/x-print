package com.x.print.ui.screen.printqueue;

import io.jmix.ui.screen.*;
import com.x.print.domain.model.printqueue.PrintQueue;

@UiController("PrintQueue.browse")
@UiDescriptor("print-queue-browse.xml")
@LookupComponent("printQueuesTable")
public class PrintQueueBrowse extends StandardLookup<PrintQueue> {
}