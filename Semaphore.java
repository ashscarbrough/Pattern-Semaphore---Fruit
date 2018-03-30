 /*
 * Name: Ash Scarbrough
 * Class: CSCI-C490
 * Semester: Summer II, 2017
 * Assignment: Lab 10
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Semaphore
{
  private final Lock semaphoreLock; // Lock created to ensure conservation of resources
  private final Condition bowlCondition;
	
  private final int licenses; // total resources (bowls)
  private int counter; //current available resources (bowls)
    
  public Semaphore(int licenses)
  {
	  semaphoreLock = new ReentrantLock(); 
	  bowlCondition = semaphoreLock.newCondition();
	  
	  this.licenses = licenses;
	  this.counter = licenses;    
  }
  
  /**
   * Method called by a thread to acquire the lock. If there are no resources
   * available this will wait until the lock has been released to re-attempt
   * the acquire.
   */
  public void acquire() throws InterruptedException
  {
	  semaphoreLock.lock();		// When acquisition of object is made, object is locked
	  
	  while (counter == 0)
	  {
		 bowlCondition.await();
	  }
	  counter = counter - 1;
	  semaphoreLock.unlock();	// When object is released, resource is unlocked

  }
  
  /**
   * Method called by a thread to release the lock.
   */
  public void release()
  {
	  semaphoreLock.lock();
	  
	  if (counter < licenses)
	  {
		  counter = counter + 1;
		  bowlCondition.signal();
	  }
	  semaphoreLock.unlock();
  }
}
