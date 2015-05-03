package edu.umass.cs.sensors.falcon.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;


/**
 * Implementation of Temporal Model used in APPM algorithm.
 * 
 * <p>The temporal model used in APPM is a fairly simple model that maintains the cumulative
 * distribution functions of duration of gaps between two consecutive events (called TTU). For each event
 * {@code e} being modeled, we store i) cumulative distribution of gap duration between {@code e}
 * and the event that precedes {@code e}, and ii) cumulative distribution of gap duration between
 * {@code e} and the second predecessor event of {@code e}. These distributions are stored for
 * each event in a corresponding instance of {@link EventTTUMonitor} class. {@link TemporalModel}
 * class is a wrapper class that simply manages the mapping between event {@code e} and its
 * distribution {@link EventTTUMonitor}.
 * 
 * <h4>Multiple models for various periods of day</h4>
 * Since the distribution of events can vary according to the time of the day, we allow a model
 * to be created for each period of the day. The number of equi-length periods can be specified
 * in the constructor of this class. For example, setting this number to 6 will create a unique
 * model for each 24/6 = 4 hours period of the day. 
 * 
 * <h4>Computing Likelihood of Event  to occur in time interval &Delta;</h4>
 * If we know the cumulative distribution function F<sub>TTU|e</sub>, then the likelihood
 * of {@code e} to occur within time interval &Delta; is F<sub>TTU|e</sub>(&Delta;). If the
 * probability of {@code e} to occur next is p(e) then the likelihood can be corrected as
 * p(e) &times; F<sub>TTU|e</sub>(&Delta;). We extend this approach for the case when we know
 * the probability of {@code e} to occur as second next event and use CDF of gap between {@code e}
 * and the second preceding event before it to compute likelihood.
 * 
 * <p>Refer to  our paper for more details:<br/>
 * Abhinav Parate, Matthias B&#246;hmer, David Chu, Deepak Ganesan, Benjamin Marlin, 
 * <b>Practical Prediction and Prefetch for Faster Access to Applications on Mobile phones</b>, 
 * Proceedings of the 2013 ACM Conference on Ubiquitous Computing (UbiComp 2013)
 * 
 * @author Abhinav Parate, aparate@cs.umass.edu
 *
 */
public class TemporalModel{

	/**
	 * Map between event and its CDF distribution
	 */

	private HashMap<String,EventTTUMonitor> eventTTUMonitorMap = new HashMap<String,EventTTUMonitor>();

	/**
	 * Number of equi-distant periods for which a temporal model will be created
	 */
	protected static int NUM_BINS = 6;
	
	/**
	 * Constructor for {@link TemporalModel}
	 * @param N_TIME_BINS Number of equi-duration periods in a day for which unique temporal
	 * models must be created.
	 */
	public TemporalModel(int N_TIME_BINS){
		NUM_BINS = N_TIME_BINS;
	}
	/**
	 * Add an event observation to the temporal model
	 * @param eventName
	 * @param timeSinceLastEvent time in milliseconds
	 * @param timeSinceSecondLastEvent time in milliseconds
	 */
	public void addEventObservation(String eventName, int timeSinceLastEvent, int timeSinceSecondLastEvent, long eventTime) 
	{
		//First identify the relevant period of the day
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTimeInMillis(eventTime);
		int HOUR_OF_DAY = gc.get(Calendar.HOUR_OF_DAY);
		int MINUTE_OF_DAY = gc.get(Calendar.MINUTE)+HOUR_OF_DAY*60;
		
		int BIN_SIZE_IN_MINUTES = (24*60)/TemporalModel.NUM_BINS;
		int TIME_BIN = MINUTE_OF_DAY/BIN_SIZE_IN_MINUTES;
		
		//Get TTU Monitor for the event
		EventTTUMonitor stats = getEventTTUMonitor(eventName);
		//Add observed times to TTU distribution
		//timeSinceLastEvent = timeSinceLastEvent/1000;//change to secs
		//timeSinceSecondLastEvent = timeSinceSecondLastEvent/1000;//change to secs
		stats.addToDistribution(timeSinceLastEvent, timeSinceSecondLastEvent, TIME_BIN);
	}

	/**
	 * Get TTU Monitor for the specified event
	 * @param eventName
	 * @return distribution for the specified event
	 */
	private EventTTUMonitor getEventTTUMonitor(String eventName){
		EventTTUMonitor stats = eventTTUMonitorMap.get(eventName);
		if(stats==null){
			stats = new EventTTUMonitor();
			eventTTUMonitorMap.put(eventName, stats);
		}
		return stats;
	}
	
	/**
	 * Get Event likelihood
	 * @param eventName name of the event
	 * @param timeSinceLastEventInMS time in ms
	 * @param timeSinceSecondLastEventInMS time in ms
	 * @param currentTime current time in ms
	 * @param ppmNextProb
	 * @param ppmSecondNextProb
	 * @return likelihood of event
	 */
	public double computeEventLikelihood(String eventName, int timeSinceLastEventInMS, int timeSinceSecondLastEventInMS,long currentTime,
			double ppmNextProb, double ppmSecondNextProb)
	{
		EventTTUMonitor stats = eventTTUMonitorMap.get(eventName);
		if(stats==null){
			stats = new EventTTUMonitor();
			eventTTUMonitorMap.put(eventName, stats);
		}
		//timeSinceLastEventInMS = timeSinceLastEventInMS/1000;//change to secs
		//timeSinceSecondLastEventInMS = timeSinceSecondLastEventInMS/1000;//change to secs
		double cdfProb[] = stats.getCDFProbability(timeSinceLastEventInMS, timeSinceSecondLastEventInMS, currentTime);
		return cdfProb[0]*ppmNextProb + cdfProb[1]*ppmSecondNextProb;
	}

}
