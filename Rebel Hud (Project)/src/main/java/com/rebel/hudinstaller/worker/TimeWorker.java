package com.rebel.hudinstaller.worker;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * TimeWorker continually updates the progress bar while the HUD is installing
 */
public class TimeWorker extends Worker implements Timer
{
	private ArrayList<Integer> percent = new ArrayList<Integer>();
	
	/**
	 * Constructor sets the delay for the progress bar
	 * 
	 * @param	delay	The delay in milliseconds
	 */
	public TimeWorker(int delay) 
	{
		super(delay);
	}
	
	/**
	 * Used to run the timer on a second thread
	 * 
	 * @pre		none
	 * @param	none
	 * @return	NULL
	 * @post	none
	 */
	protected Object doInBackground() 
	{
		process();
		timer();
		return null;
	}

	/**
	 * Creates a timer to count from 0 to 99
	 * 
	 * @pre		none
	 * @param	none
	 * @return	none
	 * @post	none
	 */
	public void timer() 
	{
		Iterator<Integer> iter = percent.iterator();
		while(iter.hasNext())
		{
			setProgress(iter.next().intValue());
			try 
			{
				Thread.sleep(myDelay);
			} catch (InterruptedException e){break;}
		}
	}

	/**
	 * Fills the ArrayList with numbers from 0-99
	 * 
	 * @pre		none
	 * @param	none
	 * @return	none
	 * @post	Percent is filled
	 */
	public void process() 
	{
		for (int i = 0; i < 100; i++)
		{
			percent.add(new Integer(i));
		}
	}
}
