package com.x.print.domain.model.printer;

import java.util.List;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
public interface IPrinterService {

    List<Printer> lookupPrint();
}
