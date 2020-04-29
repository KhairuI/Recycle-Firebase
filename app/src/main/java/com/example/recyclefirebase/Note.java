package com.example.recyclefirebase;

public class Note {

    private String title;
    private String descprition;
    private int priority;

    public Note() {

        //empty constructor
    }

    public Note(String title, String descprition, int priority) {
        this.title = title;
        this.descprition = descprition;
        this.priority = priority;
    }


    public String getTitle() {
        return title;
    }

    public String getDescprition() {
        return descprition;
    }

    public int getPriority() {
        return priority;
    }
}
