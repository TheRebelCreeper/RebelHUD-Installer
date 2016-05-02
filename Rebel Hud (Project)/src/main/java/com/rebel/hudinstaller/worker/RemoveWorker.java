package com.rebel.hudinstaller.worker;

import javax.swing.JOptionPane;

import com.rebel.hudinstaller.util.Tools;

/**
 * Used to uninstall the HUD
 */
public class RemoveWorker extends Worker
{
	/**
	 * Constructor sets the delay for the progress bar
	 * 
	 * @param	delay	The delay in milliseconds
	 */
	public RemoveWorker(int delay)
	{
		super(delay);
	}
	
	/**
	 * Removes the installed HUD
	 * 
	 * @pre		The HUD is installed
	 * @param	none
	 * @return	none
	 * @post	The HUD in uninstalled
	 */
	public void process()
	{
		TimeWorker Timer = new TimeWorker(myDelay);		// Creates a new timer with a delay of 10 ms
		try
		{
			Timer.execute();							// Starts the timer on a separate thread
			Timer.addPropertyChangeListener(this);		// Sets the timer to update the property listener
			Tools.removeHud();
			while(true)									// While loop pauses program
			{
				if (Timer.isDone() == true)				// Program resumes once Timer is done with its task
				{
					break;
				}
			}
			setProgress(100);
			// Message showing uninstallation status is displayed
			JOptionPane.showMessageDialog(null, "RebelHud uninstalled successfully.", "Uninstallation Status", JOptionPane.INFORMATION_MESSAGE);
			setProgress(0);			// Progress reset to 0%
		}
		catch(Throwable e)			// Handles the possible errors
		{
			Timer.cancel(true);		// Cancels the timer if an error is encountered
			
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "Uninstallation Error", JOptionPane.ERROR_MESSAGE);
			setProgress(0);
		}
	}
}
