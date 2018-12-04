package rebel.hudinstaller.worker;

import rebel.hudinstaller.util.Tools;

import javax.swing.*;
import java.beans.PropertyChangeListener;

/**
 * Used to update the HUD
 */

public class Updater extends Thread
{
    /**
     * Delay of the update timer
     **/
    private final int updateDelay = 50;

    /**
     * Holds the PropertyChangeListener for Timer
     */
    private PropertyChangeListener propertyListener;

    /**
     * Constructor for UpdateWorker
     *
     * @param p PropertyChangeListener
     */
    public Updater(PropertyChangeListener p)
    {
        propertyListener = p;
    }

    /**
     * Updates the HUD by checking if the installed version is the newest
     */
    public void run()
    {
        Timer timer = new Timer(updateDelay);           // Creates a timer with a delay of 50 ms
        try
        {
            if (Tools.updateHud())                      // If the user wants to update, complete the installation
            {
                timer.execute();                                        // Starts the timer on a separate thread
                timer.addPropertyChangeListener(
                        propertyListener);      // Sets the timer to update the property listener
                Tools.installHudUpdating();
                while (true)                                 // While loop pauses program
                {
                    if (timer.isDone())                      // Program resumes once Timer is done with its task
                    {
                        break;
                    }
                }
                timer.setMyProgress(100);
                // Message showing update status is displayed
                JOptionPane.showMessageDialog(null, "RebelHud updated successfully.",
                                              "Update Status", JOptionPane.INFORMATION_MESSAGE);
                timer.setMyProgress(0);     // Progress reset to 0%
            }
        }
        catch (Exception e)        // Handles the possible errors
        {
            timer.cancel(true);      // Cancels the timer if an error is encountered
            Tools.cleanUp();                            // Deletes the ZIP download and temporary files

            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(),
                                          e.getCause().getMessage(), JOptionPane.ERROR_MESSAGE);
            timer.setMyProgress(0);
            e.printStackTrace();
        }
    }
}
