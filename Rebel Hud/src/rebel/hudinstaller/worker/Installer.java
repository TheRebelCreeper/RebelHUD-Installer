package rebel.hudinstaller.worker;

import rebel.hudinstaller.util.Tools;

import javax.swing.*;
import java.beans.PropertyChangeListener;

/**
 * Used to install the HUD
 */

public class Installer extends Thread
{
	/**
	 * Delay of the installer timer
	 **/
	private final int installDelay = 50;

	/**
	 * Holds the PropertyChangeListener for Timer
	 */
	private PropertyChangeListener propertyListener;

	/**
	 * Constructor for Installer
	 *
	 * @param p PropertyChangeListener
	 */
	public Installer(PropertyChangeListener p)
	{
		propertyListener = p;
	}

	/**
	 * Installs the HUD by downloading, and extracting the ZIP file
	 */
	public void run()
	{
		Timer timer = new Timer(installDelay);       // Creates a new timer with a delay of myDelay
		try
		{
			timer.execute();                                        // Starts the timer on a separate thread
			timer.addPropertyChangeListener(propertyListener);      // Sets the timer to update the property listener
			Tools.installHud();
			while (true)                                 // While loop pauses the program
			{
				if (timer.isDone())                      // Program resumes once Timer is done with its task
				{
					break;
				}
			}
			timer.setMyProgress(100);

			// Message showing installation status is displayed
			JOptionPane.showMessageDialog(null, "RebelHud installed successfully.",
										  "Installation Status", JOptionPane.INFORMATION_MESSAGE);
			timer.setMyProgress(0);     // Progress reset to 0%
		} catch (Exception e)    // Handles the possible errors
		{
			timer.cancel(true);    // Cancels the timer if an error is encountered
			Tools.cleanUp();                          // Removes the temporary files

			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(),
										  e.getCause().getMessage(), JOptionPane.ERROR_MESSAGE);
			timer.setMyProgress(0);
			e.printStackTrace();
		}
	}
}

