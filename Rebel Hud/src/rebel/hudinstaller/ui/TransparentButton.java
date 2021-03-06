package rebel.hudinstaller.ui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class TransparentButton extends JButton
{
    private final float alphaValue;

    public TransparentButton(String text, float alpha)
    {
        super(text);
        setOpaque(false);
        alphaValue = alpha;
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
        super.paint(g2);
        g2.dispose();
    }
}
