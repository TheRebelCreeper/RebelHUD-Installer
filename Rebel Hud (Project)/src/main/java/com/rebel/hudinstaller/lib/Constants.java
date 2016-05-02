package com.rebel.hudinstaller.lib;

public class Constants 
{
	/**Version of the program**/
	public static final double installerVersion = 1.9;
	
	/**Path for 64 bit Windows**/
	public static final String TF_CUSTOM_WIN = "C:/Program Files (x86)/Steam/SteamApps/common/Team Fortress 2/tf/custom/";
	
	/**Path for 32 bit Windows**/
	public static final String TF_CUSTOM_WIN_86 = "C:/Program Files/Steam/SteamApps/common/Team Fortress 2/tf/custom/";
	
	/**Path for 64 bit Windows**/
	public static final String TF_CUSTOM_WIN_D = "D:/Program Files (x86)/Steam/SteamApps/common/Team Fortress 2/tf/custom/";
	
	/**Path for 32 bit Windows**/
	public static final String TF_CUSTOM_WIN_86_D = "D:/Program Files/Steam/SteamApps/common/Team Fortress 2/tf/custom/";
	
	/**Path for OSX**/
	public static final String TF_CUSTOM_MAC = System.getProperty("user.home") + "/Library/Application Support/Steam/SteamApps/common/Team Fortress 2/tf/custom/";
	
	/**URL for the HUD download**/
	public static final String GIT_URL = "https://github.com/TheRebelCreeper/RebelHud/archive/master.zip";
	
	/**URL for that latest version of the HUD**/
	public static final String LATEST_HUD = "https://raw.githubusercontent.com/TheRebelCreeper/RebelHud/master/custom/RebelHud/version.txt";
	
	/**URL for the latest version of the installer**/
	public static final String LATEST_INSTALLER = "https://dl.dropboxusercontent.com/u/101015703/RebelHUD/latest.txt";
	
	/**URL for the installer download**/
	public static final String INSTALLER_URL = "https://dl.dropboxusercontent.com/u/101015703/RebelHUD/RebelHUD Installer.jar";
}
