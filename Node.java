// Name:Ali Momin
// NetID:ASM180014
package com.company;

public class Node<T extends Comparable<T>> implements Comparable {
    // private variables that are to be used within the class
    private Node left;
    private Node right;
    private T payload;

    // default constructor that sets the values to default ones
    public Node()
    {
        left = null;
        right = null;
        payload = null;
    }

    // overloaded constructor that takes a payLoad the user entered and sets the local payload to it
    public Node(T ePayload)
    {
        left = null;
        right = null;
        payload = ePayload;
    }

    // overriden compareTo function that will be used to compare nodes
    @Override
    public int compareTo(Object o) {
        int val = 0;
        if(o instanceof Node)
        {
            val = payload.compareTo(((Node<T>)o).payload);
        }
        return val;
    }

    // getter and setter methods to access the variables inside of the node class
    public Node getLeft()
    {
        return left;
    }
    public Node getRight()
    {
        return right;
    }
    public T getPayload()
    {
        return payload;
    }
    public void setLeft(Node eLeft)
    {
        left = eLeft;
    }
    public void setRight(Node eRight)
    {
        right = eRight;
    }
    public void setPayload(T ePayload)
    {
        payload = ePayload;
    }

}
