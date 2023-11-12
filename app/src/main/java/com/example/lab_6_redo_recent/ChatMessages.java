package com.example.lab_6_redo_recent;

/* This class is meant to hold the message content: whilst using a constructor (used to initialise, no return type only parameters and same name as class)
 *  The constructor to initialize the message content and getter methods to retrieve the content.
 * */
public class ChatMessages {
    /* declaring the variables */
    private final boolean isSentButton;
    private String message;
    private String timeSent;

    /*The constructor*/
    public ChatMessages(String m, String t, boolean sent) {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }

    /*The getter methods that will return the correct values*/

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }
}
