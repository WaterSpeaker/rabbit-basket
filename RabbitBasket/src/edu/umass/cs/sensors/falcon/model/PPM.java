package edu.umass.cs.sensors.falcon.model;

/**
 * JNI wrapper for PPM implementation in C++
 * 
 * <h3>PPM</h3>
 * <p>The core PPM algorithm and its implementation is attributed to <a href="http://compression.ru/ds/">Dmitry Shkarin</a>.
 * We have used <a href="http://compression.ru/ds/">PPMd variant H</a> and modified it for our purpose. We do not
 * make any claim of authorship, ownership or the correctness of the PPMd program. We provide this code here only
 * for experimental and non-commercial use only. Use this program at your own risks.
 * 
 * 
 * <h3>Models</h3>
 * <p>
 * This implementation creates and maintains a number of models for a stream of 
 * events described as follows:
 * <ul>
 * <li><b>Standard Model</b>: This is a default model created using the sequence of events observed
 * till the current time</li>
 * <li><b>Context Specific Model</b>: This model is created using the sequence of events observed in
 * a specific context till the current time</li>
 * </ul>
 * 
 * <p> One can create a model for any discrete context. This context can be {@code Hour of day} 
 * or a {@code location} or any other scenario that helps differentiate an event happening in one context
 * vs another. This implementation allows users to create models for up to two contexts. 
 * In order to do so, one must specify the number of values a context may take while initializing
 * the constructor. We will use following as a <b>running example</b> that uses 
 * {@code Hour of day} and {@code Location} as the two contexts. For {@code Hour of day} context
 * we need one model for each of the 24 hours of the day. Similarly for {@code Location} context
 * we need one model for each of the possible locations, let us say that events can be generated
 * in 10 unique locations. Then, the constructor 
 * must be initialized as follows:
 * <pre>
 * {@code
 * int NUM_TOP_K_TO_PREDICT = 5; // predict top 5 events 
 * int NUM_HOUR_OF_DAY_MODELS = 24; // number of models for hour of day context
 * int NUM_LOCATION_MODELS = 10; // number of models for location context 
 * PPM ppm = new PPM(NUM_TOP_K_TO_PREDICT, NUM_HOUR_OF_DAY_MODELS, NUM_LOCATION_MODELS); 
 * }
 * </pre>  
 * <p><b>Note:</b> If the context specific models are not required, then set the number 
 * of models to a number &leq; 0 for each context. For example, the following code does 
 * not create any model for the second context. 
 * {@code PPM ppm = new PPM(NUM_TOP_K_TO_PREDICT, 24, 0);}</p>
 * 
 * <h3>Updating Model</h3>
 * PPM is an online continuous learning prediction algorithm. Thus, any new event observed
 * should be used for updating the model. To do this, one must specify the event that 
 * occurred along with the associated contexts. The contexts are specified using the index
 * for the context-specific model. In our running example for hour of day and location 
 * contexts, if the event occurred at 4th hour and in 2nd location, we update model as 
 * follows:
 * <pre>
 * {@code
 * int HOUR_OF_DAY_INDEX = 3; //Model index begins at 0 so 4th hour has index 3.
 * int LOCATION_INDEX = 1; //Model index begins at 0 so 2nd location has index 1. 
 * ppm.updateModel(event, HOUR_OF_DAY_INDEX, LOCATION_INDEX);
 * }</pre>
 * <p><b>Note:</b> If PPM was created using 0 models for some context, the corresponding 
 * context index will be ignored. 
 * 
 * <h3>Getting Predictions</h3>
 * Similar to updating model, predictions can be made specific to the contexts. In our 
 * running example, we can get different predictions for different hour of day or a different
 * location. Our implementation fuses predictions from each of the context specific model
 * and the standard model to give final predictions. To get top 5 predictions during 4th hour
 * of the day and 2nd location, we use following code:
 * <pre>
 * {@code
 * int N_PREDICTIONS_TO_GET = 5;
 * int HOUR_OF_DAY_INDEX = 3; //Model index begins at 0 so 4th hour has index 3.
 * int LOCATION_INDEX = 1; //Model index begins at 0 so 2nd location has index 1. 
 * double prediction[][] = ppm.getTopPredictions(HOUR_OF_DAY_INDEX, LOCATION_INDEX, N_PREDICTIONS_TO_GET);
 * }</pre>
 * 
 * <p>The above code returns a 2 dimensional array of size 2 &times; {@code N_PREDICTIONS_TO_GET}. 
 * 
 * <ul>
 * <li>{@code predictions[0][i]} gives <i>i-th</i> predicted event.</li>
 * <li>{@code prediction[1][i]} gives probability of the <i>i-th</i> predicted event.</li>
 * </ul>
 * 
 * <p><b>Note:</b> If PPM was created using 0 models for some context, the corresponding 
 * context index will be ignored.
 * 
 * 
 * @author Abhinav Parate, aparate@cs.umass.edu
 *
 */
public class PPM{

	static {
		String libName = "appm";
		System.loadLibrary(libName);
	}
	
	
	/**
	 * Constructor for PPM
	 * @param TOP_K maximum number of predictions to make
	 * @param N_MODELS_CONTEXT1 number of models for context 1
	 * @param N_MODELS_CONTEXT2 number of models for context 2
	 */
	public PPM(int TOP_K, int N_MODELS_CONTEXT1, int N_MODELS_CONTEXT2) {
		init(N_MODELS_CONTEXT1, N_MODELS_CONTEXT2);
		setTopK(TOP_K);
	}
	
	/**
	 * Initializes PPM to create models for each context.
	 * @param N_MODELS_CONTEXT1
	 * @param N_MODELS_CONTEXT2
	 */
	private native void init(int N_MODELS_CONTEXT1, int N_MODELS_CONTEXT2);
	
	/**
	 * Sets k: maximum number of top predictions to make
	 * @param topK
	 */
	private native void setTopK(int topK);
	
	/**
	 * Get top-k predictions based on current contexts. 
	 * <ul>
	 * <li>Returns 2 dimensional array {@code predictions[2][topK]} with <i>topK</i> predictions.</li>
	 * <li>{@code predictions[0][i]} gives <i>i-th</i> predicted event.</li>
	 * <li>{@code prediction[1][i]} gives probability of the <i>i-th</i> predicted event.</li>
	 * </ul>
	 * @param contextModelIndex1 index of model for first context type to be used in prediction
	 * @param contextModelIndex2 index of model for second context type to be used in prediction
	 * @param topK number of predictions to get
	 * @return 2-dimensional array with events and their probabilities
	 */
	public native double[][] getTopPredictions(int contextModelIndex1, int contextModelIndex2, int topK);
	
	/**
	 * Gives probability of an event to occur within next-N events 
	 * @param event the target event
	 * @param depth N
	 * @param contextModelIndex1 index of model for first context type to be used in probability
	 * computation
	 * @param contextModelIndex2 index of model for second context type to be used in probability
	 * computation
	 * @return probability of the event
	 */
	public native double getEventProbability(int event, int depth, int contextModelIndex1, int contextModelIndex2);
	
	/**
	 * Updates models with current event observation and associated contexts
	 * @param event observed event
	 * @param contextModelIndex1 index of model for first context type to be updated
	 * @param contextModelIndex2 index of model for second context type to be updated
	 */
	public native void updateModel(int event, int contextModelIndex1, int contextModelIndex2);
	
}
