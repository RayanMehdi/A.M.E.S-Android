package com.example.iem.ames.model.event;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.element.Button;

import java.util.ArrayList;

/**
 * Created by iem on 17/01/2018.
 */

public class EventButton extends AMESEvent {
    private ArrayList<Button> buttons;

    public EventButton(String name, String type, double delay, ArrayList<Button> buttons) {
        super(name, type, delay);
        this.buttons = new ArrayList<>();
        this.buttons.addAll(buttons);
    }

    public EventButton(String name, String type, double delay) {
        super(name, type, delay);
        this.buttons = new ArrayList<>();
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<Button> buttons) {
        this.buttons = buttons;
    }

    @Override
    public void run() {
        // Diplay buttons
        AMESApplication.application().getAMESManager().getButtonManager().displayNewButton(this);
    }

    @Override
    public void stop() {

    }
}
