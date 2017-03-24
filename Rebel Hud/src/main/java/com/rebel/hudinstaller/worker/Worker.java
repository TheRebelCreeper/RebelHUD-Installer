package com.rebel.hudinstaller.worker;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingWorker;

/**
 * Used to run a process on a second thread
 */

public abstract class Worker extends SwingWorker<Object, Object> implements PropertyChangeListener
{
	protected int myDelay;
	
	/**
	 * Sets the delay of the worker
	 * 
	 * @param	delay
	 */
	public Worker(int delay)
	{
		myDelay = delay;
	}
	
	/**
	 * Updates the progress of the progress bar
	 * 
	 * @pre		none
	 * @param	evt		Contains the property that was changed
	 * @return	none
	 * @post	The progress is updated
	 */
	public void propertyChange(PropertyChangeEvent evt) 
    {
        if ("progress" == evt.getPropertyName()) 
        {
            int progress = (Integer) evt.getNewValue();
            setProgress(progress);	// Sets the current progress
        } 
    }
	
	/**
	 * Used to run the install process on a second thread.
	 * 
	 * @pre		none
	 * @param	none
	 * @return	NULL
	 * @post	none
	 */
	protected Object doInBackground() 
	{
		process();		// Starts the worker process
		return null;
	}
	
	/**
	 * Runs a process for a worker
	 */
	public abstract void process();
}
