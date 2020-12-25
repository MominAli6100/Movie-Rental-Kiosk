// Name:Ali Momin
// NetID:ASM180014
//package com.company;

public class DVD implements Comparable {
    // creation of variables that are to be used within the DVD class
    private String title;
    private int available;
    private int rented;

    // default constructor that sets the value to default ones
    public DVD()
    {
        title = "";
        available = 0;
        rented = 0;
    }

    // overloaded constructor that sets the class values to what the user entered
    public DVD(String eTitle, int eAvailable, int eRented)
    {
        title = eTitle;
        available = eAvailable;
        rented = eRented;
    }

    // getter and setter methods to access the values inside of the class from the outside
    public void setTitle(String eTitle)
    {
        title = eTitle;
    }
    public void setAvailable(int eAvailable)
    {
        available = eAvailable;
    }
    public void setRented(int eRented)
    {
        rented = eRented;
    }
    public String getTitle()
    {
        return title;
    }
    public int getAvailable()
    {
        return available;
    }
    public int getRented()
    {
        return rented;
    }

    // overridden function so that this may be used with the node class
    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
