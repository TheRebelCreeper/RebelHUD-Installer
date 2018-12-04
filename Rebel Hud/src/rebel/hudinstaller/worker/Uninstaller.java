package rebel.hudinstaller.worker;

import rebel.hudinstaller.util.Tools;

import javax.swing.*;
import java.beans.PropertyChangeListener;

/**
 * Used to uninstall the HUD on a second thread
 */
public class Uninstaller extends Thread
{
    /**
     * Delay of the removal timer
     **/
    private final int removeDelay = 10;

    /**
     * Holds the PropertyChangeListener for Timer
     */
    private PropertyChangeListener propertyListener;

    /**
     * Constructor for Uninstaller
     *
     * @param p PropertyChangeListener
     */
    public Uninstaller(PropertyChangeListener p)
    {
        propertyListener = p;
    }

    /**
     * Removes the installed HUD
     */
    public void run()
    {
        Timer timer = new Timer(removeDelay);       // Creates a new timer with a delay of 10 ms
        try
        {
            timer.execute();                                        // Starts the timer on a separate thread
            timer.addPropertyChangeListener(propertyListener);      // Sets the timer to update the property listener
            Tools.removeHud();
            while (true)                                 // While loop pauses program
            {
                if (timer.isDone())                      // Program resumes once Timer is done with its task
                {
                    break;
                }
            }
            timer.setMyProgress(100);
            // Message showing uninstallation status is displayed
            JOptionPane.showMessageDialog(null, "RebelHud uninstalled successfully.",
                                          "Uninstallation Status", JOptionPane.INFORMATION_MESSAGE);
            timer.setMyProgress(0);         // Progress reset to 0%
        }
        catch (Exception e)        // Handles the possible errors
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
