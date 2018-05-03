package rebel.hudinstaller.worker;


/**
 * TimeWorker continually updates the progress bar while the HUD is installing
 */
public class TimeWorker extends Worker
{
    /**
     * Constructor sets the delay for the progress bar
     *
     * @param        delay        The delay in milliseconds
     */
    public TimeWorker(int delay)
    {
        super(delay);
    }

    /**
     * Creates a timer to count from 0 to 99
     *
     * @pre none
     * @param        none
     * @return none
     * @post none
     */
    protected Object doInBackground()
    {
        for(int i = 0; i < 100; i++)
        {
            setProgress(i);
            try
            {
                Thread.sleep(myDelay);
            } catch(InterruptedException e)
            {
                return null;
            }
        }
        return null;
    }

    @Override
    public void process()
    {

    }
}
