package com.nhlstenden.Viewer;

/**
 * Interface as part of the Mediator design pattern.
 * Interface is used to create the ability to manipulate the Presentation instance.
 */
public interface Mediator {
    void update(Events events, int value);
}
