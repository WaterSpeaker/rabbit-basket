package edu.umass.cs.sensors.falcon.model;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * EventTTUMonitor class to store Cumulative Distribution of Time to Use (TTU) for events.
 * 
 * <p> Each instance of EventTTUMonitor stores distributions for some specific event {@code e}.
 * Let E<sub>-1</sub>(e) indicate a variable for an event observed before {@code e} and 
 * let E<sub>-2</sub>(e) give an event that was observed before E<sub>-1</sub>(e). Then 
 * an instance of this class stores following two cumulative distributions for some event {@code e}:
 * <ul>
 * <li><b>CDF of Next TTU</b>: The distribution of time elapsed between event E<sub>-1</sub>(e) and {@code e}.</li>
 * <li><b>CDF of Second Next TTU:</b> The distribution of time elapsed between event E<sub>-2</sub>(e) and {@code e}.</li>
 * </ul>
 * <p>Since the CDF of TTU can vary according to the time of day (an event may occur more frequently during afternoon vs night), 
 * we allow creating CDFs for each equi-length period of day where the number of periods are specified in {@link TemporalModel}
 * class.  
 * 
 * @author Abhinav Parate, aparate@cs.umass.edu
 *
 */
public class EventTTUMonitor{
	
	/**
	 * Array of Next TTU CDFs for each period of day
	 */
	private LinkedList<Integer> nextEventDist[] = null;
	/**
	 * Array of Second Next TTU CDFs for each period of day
	 */
	private LinkedList<Integer> sNextEventDist[] = null;
	/**
	 * Maximum number of TTU's observed after which TTU history is slashed by half
	 * to reflect the latest distribution.
	 */
	private static final int MAX_LIMIT = 100;
	
	
	@SuppressWarnings("unchecked")
	public EventTTUMonitor() {
		nextEventDist = new LinkedList[TemporalModel.NUM_BINS];
		sNextEventDist = new LinkedList[TemporalModel.NUM_BINS];
		for(int i=0;i<TemporalModel.NUM_BINS;i++)
		{
			nextEventDist[i] = new LinkedList<Integer>();
			sNextEventDist[i] = new LinkedList<Integer>();
		}
	}


	/**
	 * Adds new observed TTU to the cumulative distributions
	 * @param timeSinceLastEvent time in ms
	 * @param timeSinceSecondLastEvent time in ms
	 * @param eventTime time of event
	 */
	public void addToDistribution(int timeSinceLastEvent, int timeSinceSecondLastEvent, long eventTime)
	{
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTimeInMillis(eventTime);
		int HOUR_OF_DAY = gc.get(Calendar.HOUR_OF_DAY);
		int MINUTE_OF_DAY = gc.get(Calendar.MINUTE)+HOUR_OF_DAY*60;
		
		int BIN_SIZE_IN_MINUTES = (24*60)/TemporalModel.NUM_BINS;
		int bin = MINUTE_OF_DAY/BIN_SIZE_IN_MINUTES;
		nextEventDist[bin].add(timeSinceLastEvent);
		sNextEventDist[bin].add(timeSinceSecondLastEvent);
		Collections.sort(nextEventDist[bin]);
		Collections.sort(sNextEventDist[bin]);
		LinkedList<Integer> nextList = nextEventDist[bin];
		
		/**
		 * If the number of observed TTUs in CDF exceeds MAX_LIMIT
		 * we slash the history into half its current size.
		 * */
		if(nextList.size()>MAX_LIMIT)
		{
			LinkedList<Integer> tmp = new LinkedList<Integer>();
			for(int i=0;i<nextList.size();i++)
			{
				if(i%2==0) tmp.add(nextList.get(i));
			}
			nextList.clear();
			nextList.addAll(tmp);
		}
		nextList = sNextEventDist[bin];
		if(nextList.size()>MAX_LIMIT)
		{
			LinkedList<Integer> tmp = new LinkedList<Integer>();
			for(int i=0;i<nextList.size();i++)
			{
				if(i%2==0) tmp.add(nextList.get(i));
			}
			nextList.clear();
			nextList.addAll(tmp);
		}
	}

	/**
	 * Get probability of an event based on CDF
	 * @param timeSinceLastEvent time in ms
	 * @param timeSinceSecondLastEvent time in ms
	 * @param eventTime time of event
	 * @return an array with next and second next probability
	 */
	public double[] getCDFProbability(int timeSinceLastEvent, int timeSinceSecondLastEvent, long eventTime)
	{
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTimeInMillis(eventTime);
		int HOUR_OF_DAY = gc.get(Calendar.HOUR_OF_DAY);
		int MINUTE_OF_DAY = gc.get(Calendar.MINUTE)+HOUR_OF_DAY*60;
		
		int BIN_SIZE_IN_MINUTES = (24*60)/TemporalModel.NUM_BINS;
		int bin = MINUTE_OF_DAY/BIN_SIZE_IN_MINUTES;
		double probs[] = new double[2];
		LinkedList<Integer> list = nextEventDist[bin];
		int breakIndex= 0;
		for(int i=0;i<list.size();i++)
		{
			if(timeSinceLastEvent<list.get(i)){
				breakIndex = i;
				break;
			}
			breakIndex++;
		}
		
		probs[0] = (1.0*breakIndex)/list.size();
		if(list.size()==0) probs[0] = 0.0;
		list = sNextEventDist[bin];
		breakIndex= 0;
		for(int i=0;i<list.size();i++)
		{
			if(timeSinceSecondLastEvent<list.get(i)){
				breakIndex = i;
				break;
			}
			breakIndex++;
		}
		
		probs[1] = (1.0*breakIndex)/list.size();
		if(list.size()==0) probs[1] = 0.0;
		return probs;
	}
}
