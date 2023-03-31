package com.x.print.domain.model.label;

import com.x.print.domain.model.printqueue.PrintQueue;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
@Component
public class LabelService implements ILabelService {
    @Autowired
    private DataManager dataManager;

    @Override
    public Label createLabelHistory() {
        return dataManager.create(Label.class);
    }

    @Override
    public Label saveLabelHistory(Label label) {
        if(label == null)
        {
            return null;
        }
        return dataManager.save(label);
    }


}
