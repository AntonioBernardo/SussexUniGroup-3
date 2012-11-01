package uk.ac.sussex.asegr3.tracker.client.location;

/**
 * Defines the contractor for defining a consumer of a batch of location details. Note that
 * implementations of this class are expected to operate in a single threaded model.
 * @author andrewhaines
 *
 */
public interface BatchLocationConsumer {

	/**
	 * Processes a batch. Calls to this method should proceed a call to {@link #isReady()}
	 * @param batch
	 */
	void processBatch(LocationBatch batch);
	
	/**
	 * Determines if this consumer is ready to take any more batches. This method should be called
	 * before any calls to {@link #processBatch(LocationBatch)}.
	 * @return
	 */
	boolean isReady();

}
