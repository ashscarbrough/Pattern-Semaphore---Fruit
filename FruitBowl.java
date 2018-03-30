 /*
 * Name: Ash Scarbrough
 * Class: CSCI-C490
 * Semester: Summer II, 2017
 * Assignment: Lab 10
 */

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FruitBowl
{
	private final Lock bowlLock; // Lock created to ensure conservation of resources
	 
	private boolean available;
	private ArrayList fruit;
	
	public FruitBowl()
	{
		bowlLock = new ReentrantLock();
		available = true;
		fruit = new ArrayList();
	}

	public int countFruit()
	{
		bowlLock.lock();
		int i = fruit.size();
		bowlLock.unlock();
		return i;
	}
	
	public boolean getAvailability()
	{
	    return available;
	}
	
	public void setAvailability(boolean a)
	{
	    available = a;
	}

	public void put(Fruit f)
	{
		bowlLock.lock();
	    fruit.add(f);
	    bowlLock.unlock();
	}
  
	public Fruit take()
	{
		bowlLock.lock();
		
		if (fruit.isEmpty())
	    {
			bowlLock.unlock();
			return null;
	    }
	    else
	    {
	    	Fruit f = (Fruit) fruit.remove(0);
	    	bowlLock.unlock();
	    	return f;
	    }
	}
}
