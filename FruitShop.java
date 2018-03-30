 /*
 * Name: Ash Scarbrough
 * Class: CSCI-C490
 * Semester: Summer II, 2017
 * Assignment: Lab 10
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FruitShop
{
  private final Lock shopLock; // Lock created to ensure conservation of resources
  
  private FruitBowl B1;
  private FruitBowl B2;
  private FruitBowl B3;

  //The Semaphore that controls access to the class resources (B1, B2, B3).
  private Semaphore semaphore;
  
  //FruitShop constructor
  public FruitShop()
  {
	  shopLock = new ReentrantLock();
	  
	  B1 = new FruitBowl();
	  B2 = new FruitBowl();
	  B3 = new FruitBowl();
	  
	  for (int i = 0; i < 4; i++) // put 4 fruits in each of the three bows
	  {
		  B1.put(new Fruit("APPLE"));
		  B2.put(new Fruit("ORANGE"));
		  B3.put(new Fruit("LEMON"));
	  }
        
	  semaphore = new Semaphore(3); // there are three bowls available for customers
  }

  public int countFruit()
  {
    return B1.countFruit() + B2.countFruit() + B3.countFruit();
  }
  
  /**
   * At most three customers are allowed to take bowls at one time.  
   * They need to get permissions first (there are three tokens/tickets/seats available).
   */ 
  public void acquirePermissiontoHavebowl()
  {
	  try{
		  semaphore.acquire();
	  	} catch (InterruptedException e)
	    {
	      e.printStackTrace();
	    }
	  	// acquired permission
  }
  
  /**
   * Method called by Customer to get a FruitBowl from the shop. 
   */   
  public FruitBowl takeBowl()
  {
	  shopLock.lock();
	  
	  FruitBowl bowl = null;
       
      if (B1.getAvailability()==true)
      {
    	  bowl = B1;
    	  B1.setAvailability(false);
      } 
      else if (B2.getAvailability()==true)
      {
    	  bowl = B2;
    	  B2.setAvailability(false);
      }
      else if (B3.getAvailability()==true)
      {
    	  bowl = B3;
    	  B1.setAvailability(false);
      }

      shopLock.unlock();
      return bowl;
  } 
  
  /**
   * Method called by a Customer instance to return a FruitBowl to the shop. 
   */   
  public void returnBowl(FruitBowl bowl)
  {
	  shopLock.lock();
	  
	  if (bowl == B1)
	  {
		  B1.setAvailability(true);
	  }
	  else if (bowl == B2)
	  {
		  B2.setAvailability(true);
      }
	  else if (bowl == B3)
	  {
		  B3.setAvailability(true);
      }
	  
	  shopLock.unlock();
  }
  
  /**
   * return the permission to have a bowl back to the shop.
   */ 
  public void waivePermissiontoHavebowl()
  {
	  //return the previously acquired permission
	  semaphore.release();
  }
}

