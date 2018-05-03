package rebel.hudinstaller.worker;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingWorker;

/**
 * Used to run a process on a second thread
 */

public abstract class Worker extends SwingWorker<Object, Object> implements PropertyChangeListener
{
    protected final int delay;

    /**
     * Sets the delay of the worker
     *
     * @param delay
     */
    public Worker(int delay)
    {
        this.delay = delay;
    }

    /**
     * Updates the progress of the progress bar
     *
     * @param evt Contains the property that was changed
     */
    public void propertyChange(PropertyChangeEvent evt)
    {
        if("progress" == evt.getPropertyName())
        {
            int progress = (Integer) evt.getNewValue();
            setProgress(progress);      // Sets the current progress
        }
    }

    /**
     * Used to run the install process on a second thread.
     *
     * @return null
     */
    protected Object doInBackground()
    {
        process();      // Starts the worker process
        return null;
    }

    /**
     * Runs a process for a worker
     */
    public abstract void process();
}
