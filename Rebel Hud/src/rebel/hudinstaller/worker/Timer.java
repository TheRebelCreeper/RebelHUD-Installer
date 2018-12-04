package rebel.hudinstaller.worker;


import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * TimeWorker continually updates the progress bar while the HUD is installing
 */
public class Timer extends SwingWorker<Object, Object> implements PropertyChangeListener
{
    private final int delay;

    /**
     * Constructor sets the delay for the progress bar
     *
     * @param delay The delay in milliseconds
     */
    public Timer(int delay)
    {
        this.delay = delay;
    }

    @Override
    /**
     * Creates a timer to count from 0 to 99
     *
     * @return null
     */
    protected Object doInBackground()
    {
        for (int i = 0; i < 100; i++)
        {
            setProgress(i);
            try
            {
                Thread.sleep(delay);
            }
            catch (InterruptedException e)
            {
                return null;
            }
        }
        return null;
    }

    @Override
    /**
     * Updates the progress of the progress bar
     *
     * @param evt Contains the property that was changed
     */
    public void propertyChange(PropertyChangeEvent evt)
    {
        if ("progress" == evt.getPropertyName())
        {
            int progress = (Integer) evt.getNewValue();
            setProgress(progress);      // Sets the current progress
        }
    }

    public void setMyProgress(int i)
    {
        setProgress(i);
    }
}
