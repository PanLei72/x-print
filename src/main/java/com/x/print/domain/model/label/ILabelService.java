package com.x.print.domain.model.label;

import com.x.print.domain.model.printqueue.PrintQueue;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
public interface ILabelService {

    Label createLabelHistory();

    Label saveLabelHistory(Label label);


}
