package model;/* model.Queue.java
 *
 *  Version
 *  $Id$
 *
 *  Revisions:
 *      $Log$
 *
 */

import java.util.Vector;

/**
 * This method represents the queue of the games to be played. Uses a vector to keep track of all the games
 * Kind of like an iterator implementation
 */
public class Queue {
    private Vector v;

    /** model.Queue()
     *
     * creates a new queue
     */
    public Queue(){
        v = new Vector();
    }

    /**
     * This method returns the next object in the queue
     * @return the next item in the queue
     */
    public Object next(){
        return v.remove(0);
    }

    /**
     * This method adds and object to the queue
     * @param o object to be added
     */
    public void add(Object o){
        v.addElement(o);
    }

    /**
     * This method checks to see if the queue has any more elements in it
     * @return size of queue not equal to 0
     */
    public boolean hasMoreElements(){
        return v.size() != 0;
    }

    /**
     * This method turns this queue into a vector object.
     * @return queue as vector object
     */
    public Vector asVector(){
        return v;
    }

}
