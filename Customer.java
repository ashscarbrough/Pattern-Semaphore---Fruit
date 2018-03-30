public class Customer extends Thread
{
  private final String name;
  private final FruitShop fruitShop;
  
  public Customer(String name, FruitShop fruitShop)
  {
    this.name = name;
    this.fruitShop = fruitShop;
  }
  
  /**
   * The Customer repeatedly takes Fruit from the FruitShop until no Fruit
   * remains.
   */   
  public void run()
  {     
	  while (fruitShop.countFruit() > 0)
	  {
		  /* 
		   * It is possible for a customer to get an empty bowl,
		   * because of concurrent processes
		   */
    	fruitShop.acquirePermissiontoHavebowl();
		System.out.println(name + " acquired permissions to take a bowl.");
		
    	FruitBowl bowl = fruitShop.takeBowl();
    	Fruit fruit;
            
    	if (bowl != null)
    	{
    		if ((fruit = bowl.take()) != null)
    		{
    			System.out.println(name + " took an " + fruit.getType());
    		
	    		try        
	    		{
	    		    Thread.sleep(1000);
	    			System.out.println(name + " is eating " + fruit.getType());
	    		    Thread.sleep(1000);
	    		} 
	    		catch(InterruptedException ex) 
	    		{
	    		    Thread.currentThread().interrupt();
	    		}
    		}
    		else
    		{
    			System.out.println(name + " got an empty bowl.");
    		}
    		
    		fruitShop.returnBowl(bowl);
    		System.out.println(name + " returned a bowl.");
    		
    		fruitShop.waivePermissiontoHavebowl();
    		System.out.println(name + " waive the permissions to have a bowl.");
    	}
	  }
  }
}
