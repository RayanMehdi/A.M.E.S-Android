package com.example.iem.ames.controller;

import com.example.iem.ames.model.element.Button;
import com.example.iem.ames.model.event.EventButton;

/**
 * Created by iem on 18/01/2018.
 */

public class ButtonController {
    public void runEvent(EventButton eventButton) {
        // For all buttons :
        for (Button button:eventButton.getButtons()) {
            displayNewButton
/*

            // Loading ith button image
            NSString* tempParameterString = [NSString stringWithFormat:@"Image filename for button %d",i];
            //UIImageView* currentButton = [[UIImageView alloc] initWithImage:[UIImage imageNamed:[_event.parameters objectForKey:tempParameterString]]];
            UIImage* tempImage = [UIImage imageNamed:[_event.parameters objectForKey:tempParameterString]];
            CGRect frame = CGRectMake(0.0, 0.0, tempImage.size.width, tempImage.size.height);
            UIButton* currentButton = [[UIButton alloc] initWithFrame:frame];
        [currentButton setTitle:@"Buttoni" forState:UIControlStateNormal];
        [currentButton setImage:tempImage forState:UIControlStateNormal];

            // Associate gesture recognizer for each button
            currentButton.userInteractionEnabled = YES;
            //[currentButton addGestureRecognizer:buttonTapRecognizer];
        [currentButton addTarget:self action:@selector(buttonIsPressed:) forControlEvents:UIControlEventTouchUpInside];
            // Adding button to the displayable buttons dictionnary. Key is the next event index, value is view
            tempParameterString = [NSString stringWithFormat:@"Next event index for button %d",i];
            NSNumber* nextEventIndex = [_event.parameters objectForKey:tempParameterString];
        [self.displayableButtons setObject:currentButton forKey:nextEventIndex];

            // Setting button position
            tempParameterString = [NSString stringWithFormat:@"X position for button %d",i];
            NSNumber* tempNumber = [_event.parameters objectForKey:tempParameterString];
            CGPoint tempPoint;
            tempPoint.x = [tempNumber floatValue] * self.associatedAMESController.viewSize.x;
            tempParameterString = [NSString stringWithFormat:@"Y position for button %d",i];
            tempNumber = [_event.parameters objectForKey:tempParameterString];
            tempPoint.y = (1.0 - [tempNumber floatValue]) * self.associatedAMESController.viewSize.y;
            currentButton.center = tempPoint;
            // Displaying button
        [self.associatedAMESController.currentActiveView addSubview:currentButton];
            // releasing unused pointers
        [currentButton release];
            //[buttonTapRecognizer release];
            */
        }
    }
}
