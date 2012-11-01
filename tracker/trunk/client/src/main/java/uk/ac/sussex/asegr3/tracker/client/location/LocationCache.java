package uk.ac.sussex.asegr3.tracker.client.location;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.service.LocationUpdateListener;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

/**
 * This cache consumes locations and batches them up into accumulated lists. These lists are then forwarded
 * to a consumer in a single go when:
 * 
 *  <ul>
 *  <li>the consumer is ready to receive new updates. defined by {@link BatchLocationConsumer#isReady()}</li>
 *  <li>the size of the batch reaches that defined by {@link #defaultBatchSize}.</li>
 *  or
 *  <li>the time since the first message and the message being added is greater then the value
 *  defined by {@link #cacheFlushTime}. 
 *  </ul>
 *  
 *  To ensure that the cache does not fill up in the event of a consumer consistantly no being ready, there
 *  is a limit of how many location entries can be held. Once this is exceeded, the cache will drop off
 *  LRU items. Please note that callbacks to the consumer will be made synchronously from calls that this class
 *  receives from the {@link #addLocation(LocationDTO)} method. This does impose a possible limitation that
 *  if this stops receiving updates of locations then, it will also stop sending them, even if there are
 *  entries in the cache.
 *  
 *  Please note that this class can be used in a multi thread environment
 * 
 * @author andrewhaines
 *
 */
public class LocationCache implements LocationUpdateListener{

	private final int cacheLimit;
	private final int defaultBatchSize;
	private final long cacheFlushTime;
	private final BatchLocationConsumer consumer;
	private final SortedSet<LocationDto> queue = new TreeSet<LocationDto>();
	private int currentBatchVersion = 0;
	private final Lock readLock;
	private final Lock writeLock;
	private final Logger logger;
	
	public LocationCache(int cacheLimit, int defaultBatchSize, long cacheFlushTime, BatchLocationConsumer consumer, Logger logger) {
		this.cacheLimit = cacheLimit;
		this.defaultBatchSize = defaultBatchSize;
		this.cacheFlushTime = cacheFlushTime;
		this.consumer = consumer;
		this.logger = logger;
		
		ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		
		this.readLock = readWriteLock.readLock();
		this.writeLock = readWriteLock.writeLock();
	}

	public void addLocation(LocationDto locationDto) {
		
		boolean added;
		writeLock.lock();
		try{
			if (!(added = queue.add(locationDto))){
				logger.debug(LocationCache.class, "location: "+locationDto+" has already been added in this batch");
			}
		} finally{
			writeLock.unlock();
		}
			
		if (added){
			processQueue(queue);
		}
	}
	
	public void clear(){
		queue.clear();
	}

	private void processQueue(SortedSet<LocationDto> queue) {
		readLock.lock();
		try{
			if (queue.size() == cacheLimit+1){
				// remove head of queue. As this gets called once for every location this is good enough
				readLock.unlock();
				writeLock.lock();
				
				try{
					queue.remove(queue.first());
				}
				finally{
					readLock.lock();
					writeLock.unlock();
				}
			} else if (queue.size() > cacheLimit){
				throw new IllegalStateException("queue has "+queue.size()+" elements in it. Consider clearing the queue");
			}
			
			if (consumer.isReady()){
				if (queue.size() >= defaultBatchSize || tooMuchTimePastInQueue(queue)){
					readLock.unlock();
					writeLock.lock();
					try{
						processBatch();
					}
					finally{
						readLock.lock();
						writeLock.unlock();
					}
				} 
			} 
		} finally {
			readLock.unlock();
		}
	}

	private boolean tooMuchTimePastInQueue(SortedSet<LocationDto> queue) {
		LocationDto firstLocation = queue.first();
		LocationDto lastLocation = queue.last();
		
		return (lastLocation.getTimestamp() - firstLocation.getTimestamp()) >= cacheFlushTime;
	}

	private void processBatch() {
		LocationBatch batch = new LocationBatch(new ArrayList<LocationDto>(queue), currentBatchVersion++);
		
		consumer.processBatch(batch);
		queue.clear();
	}

	@Override
	public void notifyNewLocation(LocationDto location) {
		addLocation(location);
	}

}