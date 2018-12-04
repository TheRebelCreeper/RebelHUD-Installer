package rebel.hudinstaller.ui;

import rebel.hudinstaller.worker.Installer;
import rebel.hudinstaller.worker.Uninstaller;
import rebel.hudinstaller.worker.Updater;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author Aaron Lampert
 */

public class HudUI implements ActionListener, PropertyChangeListener
{
    private final float alpha = 0.85f;

    private JFrame hudFrame;
    private JPanel hudGUI, buttonPanel;
    private JButton installButton, updateButton, removeButton;
    private JProgressBar progressBar;
    private ImageIcon icon;
    private JLabel imgBackground;

    /**
     * Creates the frame of the GUI
     */
    public HudUI()
    {
        JFrame.setDefaultLookAndFeelDecorated(false);
        hudFrame = new JFrame("RebelHUD Installer");           // Sets the name of the window
        hudFrame.setResizable(false);                               // Makes the window non-resizable
        fillPanel();                                                // Creates the controls for the window
        hudFrame.setContentPane(hudGUI);                            // Adds the controls for the window
        hudFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // Sets the window to close when the X is pressed
        hudFrame.setSize(340, 210);                   // Sets the size of the window
        hudFrame.setLocationRelativeTo(null);                       // Sets the program to launch in the center of the screen
        hudFrame.setVisible(true);
    }

    /**
     * Creates the objects to be used in the UI
     */
    public void fillPanel()
    {
        // Creation of a bottom JPanel to place everything on.
        hudGUI = new JPanel();
        hudGUI.setLayout(null);

        // Creation of a Panel to contain all the JButtons.
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(null);
        buttonPanel.setLocation(0, 0);
        buttonPanel.setSize(334, 182);
        hudGUI.add(buttonPanel);

        // Creation of the Install Button
        installButton = new TransparentButton("Install RebelHud", alpha);      // Creates the button and sets its text
        installButton.setFont(new Font("Arial", Font.BOLD, 18));        // Sets the font of the button
        installButton.setLocation(10,42);                                    // Sets the location of the button
        installButton.setSize(310,60);                               // Sets the size of the button
        installButton.addActionListener(this);
        buttonPanel.add(installButton);

        // Creation of the Update Button
        updateButton = new TransparentButton("Check for Updates", alpha);
        updateButton.setFont(new Font("Arial", Font.BOLD, 13));
        updateButton.setLocation(10, 113);
        updateButton.setSize(150, 57);
        updateButton.addActionListener(this);
        buttonPanel.add(updateButton);

        // Creation of the Uninstall Button
        removeButton = new TransparentButton("Uninstall", alpha);
        removeButton.setFont(new Font("Arial", Font.BOLD, 13));
        removeButton.setLocation(170, 113);
        removeButton.setSize(150, 57);
        removeButton.addActionListener(this);
        buttonPanel.add(removeButton);

        // Creation of the progress bar
        UIManager.put("ProgressBar.selectionForeground", Color.black);  // Sets the color of the text to black
        UIManager.put("ProgressBar.selectionBackground", Color.black);  // Sets the color of the text to black
        progressBar = new JProgressBar(0, 100);
        progressBar.setBackground(UIManager.getColor("Button.background"));
        progressBar.setFont(new Font("Arial", Font.PLAIN, 12));
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.GREEN);
        progressBar.setBounds(10, 11, 310, 20);
        buttonPanel.add(progressBar);

        imgBackground = new JLabel("");
        imgBackground.setOpaque(true);
        imgBackground.setBounds(0, 0, 340, 210);
        hudGUI.add(imgBackground);
        icon = new ImageIcon(HudUI.class.getResource("/images/Background.png"));
        imgBackground.setIcon(resize(icon, 340, 210));
    }

    /**
     * Changes the percent finished on the progress bar
     *
     * @param evt The property that was changed
     */
    public void propertyChange(PropertyChangeEvent evt)
    {
        if(evt.getPropertyName() == "progress")
        {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);      // Sets the percentage on the progress bar
        }
    }

    /**
     * Checks for any actions performed with the GUI
     *
     * @param evt The action that was executed
     */
    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource() == installButton)                      // Checks if the Install button was pressed
        {
            Installer install = new Installer(this);          // Used to install the HUD
            install.start();                                     // Starts installation process on a second thread
        }
        else if(evt.getSource() == updateButton)                 // Checks if the Update button was pressed
        {
            Updater update = new Updater(this);              // Used to update the HUD
            update.start();                                     // Starts update process on a second thread
        }
        else if(evt.getSource() == removeButton)                // Checks if the Uninstall button was pressed
        {
            Uninstaller uninstall = new Uninstaller(this);   // Used to remove the HUD
            uninstall.start();                                  // Starts the uninstallation on a second thread
        }
    }

    /**
     * Takes an image and resizes it
     *
     * @param w   The width of the image
     * @param h   The height of the image
     * @param img The image to resize
     * @return Returns a scaled image
     */
    public ImageIcon resize(ImageIcon img, int w, int h)
    {
        return new ImageIcon(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
    }
}
