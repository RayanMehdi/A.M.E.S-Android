package com.example.iem.ames.model.event;

import com.example.iem.ames.model.element.Text;

/**
 * Created by Jo' on 16/01/2018.
 */

public class EventText extends AMESEvent{
    private Text text;

    public EventText(String name, String type, double delay, Text text) {
        super(name, type, delay);
        this.text = text;
    }

    @Override
    public void run() {

    }
}
