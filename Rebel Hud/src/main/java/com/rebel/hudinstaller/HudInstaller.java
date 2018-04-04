package com.rebel.hudinstaller;

import com.rebel.hudinstaller.ui.HudUI;
import com.rebel.hudinstaller.util.Tools;

/**
 * 
 * @author Aaron Lampert
 * @Version 2.50
 * @Since 10-3-2015
 *
 */

public class HudInstaller
{
	/**
	 * Creates the GUI for the program
	 */
	public static void main(String[] args)
	{
		if (Tools.updateInstaller() != 1)	// Checks if there is an update, and runs the installer if there isn't
		{
			@SuppressWarnings("unused")
			HudUI rebelHudUI = new HudUI(); 	// Creates a window for the program
		}
    }
}
