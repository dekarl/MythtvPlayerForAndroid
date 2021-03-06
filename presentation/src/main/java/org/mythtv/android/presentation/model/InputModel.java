package org.mythtv.android.presentation.model;

import lombok.Data;

/**
 * Created by dmfrey on 1/18/16.
 */
@Data
public class InputModel {

    private int id;
    private int cardId;
    private int sourceId;
    private String inputName;
    private String displayName;
    private boolean quickTune;
    private int recordPriority;
    private int scheduleOrder;
    private int liveTvOrder;

}
