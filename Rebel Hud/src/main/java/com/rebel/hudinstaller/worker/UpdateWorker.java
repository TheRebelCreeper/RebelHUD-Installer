package com.rebel.hudinstaller.worker;

import javax.swing.JOptionPane;

import com.rebel.hudinstaller.util.Tools;

/**
 * Used to update the HUD
 */

public class UpdateWorker extends Worker
{
	/**
	 * Constructor sets the delay for the progress bar
	 
	 * @param	delay	The delay in milliseconds
	 */
	public UpdateWorker(int delay)
	{
		super(delay);
	}
	
	/**
	 * Updates the HUD by checking if the installed version is the newest
	 * 
	 * @pre		The HUD is installed
	 * @param	none
	 * @return	none
	 * @post	The HUD is updated
	 */
	public void process()
	{
		TimeWorker Timer = new TimeWorker(myDelay);			// Creates a timer with a delay of 50 ms
		try
		{
			if (Tools.updateHud() == 1)						// If the user wants to update, complete the installation
			{
				Timer.execute();							// Starts the timer on a separate thread
				Timer.addPropertyChangeListener(this);		// Sets the timer to update the property listener
				Tools.installHud(1);
				while(true)									// While loop pauses program
				{
					if (Timer.isDone() == true)				// Program resumes once Timer is done with its task
					{
						break;
					}
				}
				setProgress(100);
				// Message showing update status is displayed
				JOptionPane.showMessageDialog(null, "RebelHud updated successfully.", "Update Status", JOptionPane.INFORMATION_MESSAGE);
				setProgress(0);			// Progress reset to 0%
			}
		}
		catch(Throwable e)				// Handles the possible errors
		{
			Timer.cancel(true);			// Cancels the timer if an error is encountered
			Tools.cleanUp();			// Deletes the ZIP download and temporary files

			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), e.getCause().getMessage(), JOptionPane.ERROR_MESSAGE);
			setProgress(0);
		}
	}
}
