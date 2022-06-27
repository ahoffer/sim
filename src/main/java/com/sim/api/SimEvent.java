package com.sim.api;

import java.io.Serializable;

/**
 * SimEvent is the interface for classes that actually simulate something. It might put a message on a queue,
 * send an HTTP request, or write to a file. What ever it does
 * should be separate from any metrics or logging to is not part of the simulation.
 * The SimEvent DOES NOT KNOW IT IS IN A SIMULATION. The SimReporting, on the other hand, does know it is in a simulation, and can report on things when the event was executed.
 * However, it must not participate in the simulation.
 *
 * I considered not having a SimEvent interface. I deleted it once, and then brought it back.
 * It could be replaced with a Consumer<SimReporter> function. Letting the SimEventStreams define
 * a lambda means there would be no need for this interface or its concrete subclasses. That would be much less code-- less prolix.
 * Using Consumer<SimReporter> might still be the best choice, and I might delete this interface again.
 *
 * The counterargument is lambdas carry less semantic information than a real domain object. Looking at lambdas in the debugger is
 * particularly frustrating: "What does this lambda do? I have no idea?". For now, I feel  the extra boilerplate code
 * is the better approach. It is a bit more tedious to create a simulation, but you have the concrete classes to
 * give it substance. I feel that Java is at its best for OO programs. I think Java's functional programming is
 * clumsy. If you want to do functional programming, use a real functional language and not Java.
 */
public interface SimEvent extends Serializable , Runnable{

}
