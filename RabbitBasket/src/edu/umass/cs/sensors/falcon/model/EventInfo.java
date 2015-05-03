package edu.umass.cs.sensors.falcon.model;

/**
 * Helper class to save event information.
 * 
 * @author Abhinav Parate, aparate@cs.umass.edu
 *
 */
public class EventInfo {
	/**
	 * Name of the event
	 */
	public String eventName;
	/**
	 * Time when event occurred.
	 */
	public long eventTime = System.currentTimeMillis();
}
