package com.rebel.hudinstaller.worker;

import javax.swing.JOptionPane;

import com.rebel.hudinstaller.util.Tools;

/**
 * Used to install the HUD
 */

public class InstallWorker extends Worker
{
	/**
	 * Constructor sets the delay for the progress bar
	 
	 * @param	delay	The delay in milliseconds
	 */
	public InstallWorker(int delay)
	{
		super(delay);
	}
	
	/**
	 * Installs the HUD by downloading, and extracting the ZIP file
	 * 
	 * @pre		none
	 * @param	none
	 * @return	none
	 * @post	The HUD is installed
	 */
	public void process()
	{
		TimeWorker Timer = new TimeWorker(myDelay);	// Creates a new timer with a delay of myDelay
		try
		{
			Timer.execute();							// Starts the timer on a separate thread
			Timer.addPropertyChangeListener(this);		// Sets the timer to update the property listener
			Tools.installHud();
			while(true)									// While loop pauses the program
			{
				if (Timer.isDone() == true)				// Program resumes once Timer is done with its task
				{
					break;			
				}
			}
			setProgress(100);
			// Message showing installation status is displayed
			JOptionPane.showMessageDialog(null, "RebelHud installed successfully.", "Installation Status", JOptionPane.INFORMATION_MESSAGE);
			setProgress(0);			// Progress reset to 0%
		}
		catch(Throwable e)			// Handles the possible errors
		{
			Timer.cancel(true);		// Cancels the timer if an error is encountered
			Tools.cleanUp();		// Removes the temporary files
			
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), e.getCause().getMessage(), JOptionPane.ERROR_MESSAGE);
			setProgress(0);
		}
	}
}

