package edu.umass.cs.sensors.falcon.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Class implementing APPM algorithm
 * <p>
 * APPM algorithm has two components:
 * <ul>
 * <li><b>Prediction Model:</b> Refer {@link edu.umass.cs.sensors.falcon.model.PPM} for details</li>
 * <li><b>Temporal Model:</b> Refer {@link edu.umass.cs.sensors.falcon.model.TemporalModel} for details</li>
 * </ul>
 * <p>The following details assume that you have read the documentation of above two models. Now, we describe
 * the steps in using APPM.
 * 
 * <h4>Instantiate Models</h4>
 * The first step is to indicate:
 * <ul>
 * <li><small>N_PREDICTIONS_TO_GET</small> i.e. the number of top predictions to get for the next likely event.</li>
 * <li><small>N_MODELS_CONTEXT1</small> i.e. Cardinality of the first context associated with the event. For example, 
 * if {@code HOUR OF DAY}
 * is the context associated with the event then its cardinality is 24. Use a number &leq; 0 if
 * context-specific prediction is not desired.</li>
 * <li><small>N_MODELS_CONTEXT2</small> i.e. Cardinality of the second context associated with the event. Use a number &leq; 0 if
 * context-specific prediction is not desired.</li>
 * <li><small>N_TIME_BINS</small> Number of temporal models to create. This number should be at least 1. 
 * A value
 * greater than 1 will divide the duration of the day into <small>N_TIME_BINS</small> equal periods and 
 * will create
 * a unique temporal model for each period.</li>
 * </ul>
 * Now, create an APPM instance as follows:
 * <pre>{@code APPM appm = new APPM(N_PREDICTIONS_TO_GET, N_MODELS_CONTEXT1, N_MODELS_CONTEXT2, N_TIME_BINS);}</pre>
 * 
 * <h4>Register Events</h4>
 * Now, Specify the list of event names for which temporal model must be created. Example:
 * <pre>
 * {@code
 * LinkedList<String> events = new LinkedList<String>();
 * events.add("Email");
 * events.add("Facebook");
 * appm.registerEventsForTemporalModeling(events);
 * }
 * </pre>
 * 
 * <h4>Updating Models and Getting Predictions, Event Likelihoods</h4>
 * For any system observing a sequence of events, it must update APPM models with the latest
 * observed event. Once the models have been updated, we can use it to predict the next
 * event likely to occur or to compute likelihood of the event to occur within some interval.
 * Example code:
 * <pre>
 * {@code
 * //Let 'Email' be observed at UTC time '1368468829000' at 4th hour of day (first context).
 * appm.addEventObservation(1368468829L, 'Email', 3, 0);
 * 
 * //Get top-k predictions for the next event and assuming the contexts remain the same.
 * String predictedEvents[] = appm.getEventPredictions(3, 0);
 * for(int i=0;i<predictedEvents.length;i++)
 * 	System.out.println("Predicted Event at Rank "+i+" "+predictedEvents[i]);
 * 
 * //Get likelihood of event 'Facebook' to occur in next 5 minutes 
 * //if the current UTC time is '1368468830000'. Assuming the contexts remain the same.
 * String targetEvents[] = new String[]{"Facebook"};
 * long delta = 5*60*1000L;
 * double likelihood[] = appm.getEventLikelihoodInDeltaTime(1368468830000L, delta, 3, 0, targetEvents);
 * for(int i=0;i<likelihood.length;i++)
 * 	System.out.println("Likelihood of "+targetEvents[i]+" is "+likelihood[i]);
 * }
 * </pre> 
 *  
 *
 * @author Abhinav Parate aparate@cs.umass.edu
 */
public class APPM {

	/**
	 * PPM Prediction model
	 */
	private static PPM predictionModel = null;
	/**
	 * Temporal model
	 */
	private TemporalModel temporalModel = null;
	/**
	 * Mapping between events and corresponding encoding to be used in prediction model
	 */
	private AllEventsInfo eventMap = null;
	/**
	 * List of recent observed events 
	 */
	private LinkedList<EventInfo> recentEventsList = null;
	

	/**
	 * Number of events to predict
	 */
	public static int NUM_PREDICTIONS = 5;

	/**
	 * List of events for which temporal model is needed
	 */
	private LinkedList<String> eventList = new LinkedList<String>();



	/**
	 * Constructor for APPM. Use a number &leq; 0 if context-specific predictions
	 * are not desired. Set {@code N_TIME_BINS} to at least 1 as at least 1 temporal
	 * model is needed.
	 * @param N_PREDICTIONS_TO_GET number of events that will be predicted
	 * @param N_MODELS_CONTEXT1 number of models to create for first context type
	 * @param N_MODELS_CONTEXT2 number of models to create for second context type
	 * @param N_TIME_BINS number of temporal models to create for a day
	 */
	public APPM(int N_PREDICTIONS_TO_GET, int N_MODELS_CONTEXT1, int N_MODELS_CONTEXT2, int N_TIME_BINS) {
		APPM.NUM_PREDICTIONS = N_PREDICTIONS_TO_GET;
		initialize(N_MODELS_CONTEXT1, N_MODELS_CONTEXT2, N_TIME_BINS);
	}


	
	/**
	 * Registers events for which temporal model is needed.
	 * <p>Temporal model is created only for the registered events to
	 * save computation and memory.
	 * @param events list of events
	 */
	public void registerEventsForTemporalModeling(LinkedList<String> events) {
		eventList = new LinkedList<String>();
		eventList.addAll(events);
	}

	
	/**
	 * Add an event observation along with associated contexts
	 * @param eventTime time of event observation
	 * @param eventName name of the observed event
	 * @param indexModelContext1 index to the model for the first context type
	 * @param indexModelContext2 index to the model for the second context type
	 */
	public void addEventObservation(long eventTime, String eventName, int indexModelContext1, int indexModelContext2) {
		//Create Event
		EventInfo eventInfo = new EventInfo();
		eventInfo.eventTime = eventTime;
		eventInfo.eventName = eventName;
		//Add to recent events
		recentEventsList.addLast(eventInfo);
		
		//check if there is an encoding for the event
		checkEventEncoding(eventInfo);
		//update temporal model
		updateTemporalModel(eventInfo);
		//update prediction model
		updatePredictionModel(eventName, indexModelContext1, indexModelContext2);
		//clean up recent events list
		while(recentEventsList.size()>20)
			recentEventsList.remove();
	}
	
	/**
	 * Returns an array of top k predicted events for a given context where k is {@code NUM_EVENTS_TO_PREDICT}
	 * @param indexModelContext1 index to the model for the first context to be used in calculating predictions
	 * @param indexModelContext2 index to the model for the second context to be used in calculating predictions
	 * @return array of predicted events
	 */
	public String[] getEventPredictions(int indexModelContext1, int indexModelContext2) {
		//Get Top-k predictions for current time of day
		double predictions[][] = predictionModel.getTopPredictions(indexModelContext1, indexModelContext2, NUM_PREDICTIONS);

		double symbols[] = predictions[0];
		//double probabilities[] = predictions[1];
		List<String> candidates = new Vector<String>();
		for(int i=0;i<symbols.length;i++) {
			int encoding = (int)symbols[i];
			if(encoding>0 && encoding<=255) {
				String candidate = eventMap.encodingToEventNameMap[encoding];
				if(candidate!=null) candidates.add(candidate);
			}
		}

		return candidates.toArray(new String[]{});
	}

	
	/**
	 * Get the likelihood of events to occur within some interval
	 * <p>Likelihoods will be 0 if the event was not observed before for this time of the day. 
	 * @param currentTime time at which likelihood is to be determined (in milliseconds)
	 * @param deltaTime interval in milliseconds
	 * @param indexModelContext1 index to the model for the first context to be used in calculating likelihood 
	 * @param indexModelContext2 index to the model for the second context to be used in calculating likelihood
	 * @param events list of events for which likelihood is needed
	 * @return an array consisting of likelihood of events
	 */
	public double[] getEventLikelihoodInDeltaTime(long currentTime, long deltaTime,int indexModelContext1,int indexModelContext2, String events[]) {
		double likelihood[] = new double[events.length];
		LinkedList<EventInfo> recents = getRecentEvents();
		EventInfo lastAppUsage = null, secondLastAppUsage = null;
		if(recents.size()>=2)
		{
			lastAppUsage = recents.get(0);
			secondLastAppUsage = recents.get(1);
		}

		if(lastAppUsage==null || secondLastAppUsage==null)
			return likelihood;

		long time = currentTime+deltaTime;
		int timeSinceLast = (int)(time-lastAppUsage.eventTime);
		int timeSinceSecondLast = (int)(time-secondLastAppUsage.eventTime);
		
		for(int i=0;i<events.length;i++)
		{
			String packageName = events[i];
			Integer encoding = eventMap.eventNameToEncodingMap.get(packageName);

			//Skip if not yet encountered this app
			if(encoding==null){
				continue;
			}
			double ppmNextProb = predictionModel.getEventProbability(encoding, 1,indexModelContext1, indexModelContext2);
			double ppmSecondNextProb = predictionModel.getEventProbability(encoding, 2,indexModelContext1, indexModelContext2);
			likelihood[i] = temporalModel.computeEventLikelihood(packageName, timeSinceLast, timeSinceSecondLast, currentTime, ppmNextProb, ppmSecondNextProb);
			
		}
		return likelihood;
	}

	/**
	 * Adds new event observation and creates encoding if event is seen for the first time
	 * @param curEventInfo event being added
	 */
	private void checkEventEncoding(EventInfo curEventInfo) {
		String name = curEventInfo.eventName;
		Integer encoding = eventMap.eventNameToEncodingMap.get(name);
		if(encoding==null) {
			encoding = eventMap.lastEventEncoding+1;
			eventMap.eventNameToEncodingMap.put(name, encoding);
			eventMap.encodingToEventNameMap[encoding] = name;
			eventMap.lastEventEncoding = encoding;
			if(eventMap.lastEventEncoding == 255){
				eventMap.lastEventEncoding = 0;
			}
		}
		
	}

	/**
	 * Get recent event list
	 * @return LRU list of recent events 
	 */
	private LinkedList<EventInfo> getRecentEvents() {
		LinkedList<EventInfo> recents = new LinkedList<EventInfo>();
		for(int i=recentEventsList.size();i>0;i--){
			EventInfo eventInfo = recentEventsList.get(i-1);
			if(eventInfo==null || eventInfo.eventName==null)
				continue;
			recents.add(eventInfo);

		}
		return recents;
	}

	/**
	 * Updates temporal model with the current event if it is in
	 * the list of events that require temporal model
	 * @param curEventInfo current event
	 */
	private void updateTemporalModel(EventInfo curEventInfo)
	{
		if(eventList.contains(curEventInfo.eventName)){
			LinkedList<EventInfo> recentEvents = getRecentEvents();
			EventInfo lastEventInfo = null, secondLastEventInfo = null;
			if(recentEvents.size()>=2)
			{
				lastEventInfo = recentEvents.get(0);
				secondLastEventInfo = recentEvents.get(1);
			}
			
			if(lastEventInfo==null || secondLastEventInfo==null)
				return;
			long time = curEventInfo.eventTime;
			int timeSinceLast = (int)(time-lastEventInfo.eventTime);
			int timeSinceSecondLast = (int)(time-secondLastEventInfo.eventTime);
			temporalModel.addEventObservation(curEventInfo.eventName, timeSinceLast, timeSinceSecondLast, curEventInfo.eventTime);
		}
	}


	/**
	 * Update PPM model with current event observation
	 * @param currentEvent name of the event
	 * @param indexModelContext1 index to the model for the first context to be updated
	 * @param indexModelContext2 index to the model for the second context to be updated
	 */
	private void updatePredictionModel(String currentEvent, int indexModelContext1, int indexModelContext2) {
		Integer encoding = eventMap.eventNameToEncodingMap.get(currentEvent);
		if(encoding==null) {
			encoding = eventMap.lastEventEncoding+1;
			eventMap.eventNameToEncodingMap.put(currentEvent, encoding);
			eventMap.encodingToEventNameMap[encoding] = currentEvent;
			eventMap.lastEventEncoding = encoding;
			if(eventMap.lastEventEncoding == 255){
				//Recycle if 256 apps encountered
				eventMap.lastEventEncoding = 0;
			}
		}
		predictionModel.updateModel(encoding, indexModelContext1, indexModelContext2);

	}

	/**
	 * Initializes various objects
	 */
	private void initialize(int N_MODELS_CONTEXT1, int N_MODELS_CONTEXT2, int N_TIME_BINS) {
		if(eventMap==null)
			eventMap = new AllEventsInfo();
		if(predictionModel==null)
			predictionModel = new PPM(NUM_PREDICTIONS,N_MODELS_CONTEXT1,N_MODELS_CONTEXT2);
		if(temporalModel==null)
			temporalModel = new TemporalModel(N_TIME_BINS);

		if(recentEventsList==null){
			recentEventsList = new LinkedList<EventInfo>();
			recentEventsList.add(new EventInfo());
			recentEventsList.add(new EventInfo());
		}
	}



	/**
	 * AllEventsInfo class to maintain event encodings required for PPM.
	 * <p>
	 * Use this class to get mapping from 'event name' to 'event code' in PPM
	 * as well as to get reverse mapping from 'event code' to 'event name'.
	 * 
	 * @author Abhinav Parate, aparate@cs.umass.edu
	 *
	 */
	class AllEventsInfo {
		/**
		 * Event Name to Encoding Map
		 */
		HashMap<String,Integer> eventNameToEncodingMap;
		/**
		 * Encoding to Event Name Map
		 */
		String[] encodingToEventNameMap;
		
		/**
		 * Encoding used for last new event observed.
		 */
		int lastEventEncoding = 0;
		
		

		AllEventsInfo() {
			eventNameToEncodingMap = new HashMap<String,Integer>();
			encodingToEventNameMap = new String[256];
			lastEventEncoding = 0;
		}
	}
}
