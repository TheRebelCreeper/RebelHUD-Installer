package rebel.hudinstaller.lib;

public class Constants
{
    /**
     * Version of the program
     **/
    public static final double INSTALLER_VERSION = 2.52;

    /**
     * Path for 64 bit Windows
     **/
    public static final String TF_CUSTOM_WIN = "C:/Program Files (x86)/Steam/SteamApps/common/Team Fortress 2/tf/custom/";

    /**
     * Path for 64 bit Windows Custom Steam Library
     **/
    public static final String TF_CUSTOM_WIN_LIBRARY = "C:/Program Files (x86)/SteamLibrary/SteamApps/common/Team Fortress 2/tf/custom/";

    /**
     * Path for 32 bit Windows
     **/
    public static final String TF_CUSTOM_WIN_86 = "C:/Program Files/Steam/SteamApps/common/Team Fortress 2/tf/custom/";

    /**
     * Path for 32 bit Windows Custom Steam Library
     **/
    public static final String TF_CUSTOM_WIN_86_LIBRARY = "C:/Program Files/SteamLibrary/SteamApps/common/Team Fortress 2/tf/custom/";

    /**
     * Path for 64 bit Windows D Drive
     **/
    public static final String TF_CUSTOM_WIN_D = "D:/Program Files (x86)/Steam/SteamApps/common/Team Fortress 2/tf/custom/";

    /**
     * Path for 64 bit Windows Custom Library D Drive
     **/
    public static final String TF_CUSTOM_WIN_D_LIBRARY = "D:/Program Files (x86)/SteamLibrary/SteamApps/common/Team Fortress 2/tf/custom/";

    /**
     * Path for 32 bit Windows
     **/
    public static final String TF_CUSTOM_WIN_86_D = "D:/Program Files/Steam/SteamApps/common/Team Fortress 2/tf/custom/";

    /**
     * Path for 32 bit Windows Custom Steam Library
     **/
    public static final String TF_CUSTOM_WIN_86_D_LIBRARY = "D:/Program Files/SteamLibrary/SteamApps/common/Team Fortress 2/tf/custom/";

    /**
     * Path for OSX
     **/
    public static final String TF_CUSTOM_MAC = System.getProperty(
            "user.home") + "/Library/Application Support/Steam/SteamApps/common/Team Fortress 2/tf/custom/";

    public static String[] tf2Locations = {TF_CUSTOM_WIN, TF_CUSTOM_WIN_LIBRARY, TF_CUSTOM_WIN_86, TF_CUSTOM_WIN_86_LIBRARY,
            TF_CUSTOM_WIN_D, TF_CUSTOM_WIN_D_LIBRARY, TF_CUSTOM_WIN_86_D, TF_CUSTOM_WIN_86_D_LIBRARY, TF_CUSTOM_MAC};

    /**
     * URL for the HUD download
     **/
    public static final String GIT_URL = "https://github.com/TheRebelCreeper/RebelHud/archive/master.zip";

    /**
     * URL for that latest version of the HUD
     **/
    public static final String LATEST_HUD = "https://raw.githubusercontent.com/TheRebelCreeper/RebelHud/master/custom/RebelHud/version.txt";

    /**
     * URL for the latest version of the installer
     **/
    public static final String LATEST_INSTALLER = "https://raw.githubusercontent.com/TheRebelCreeper/RebelHud-Installer/master/latest.txt";

    /**
     * URL for the installer download
     **/
    public static final String INSTALLER_URL = "https://github.com/TheRebelCreeper/RebelHud-Installer/blob/master/RebelHUD%20Installer.jar?raw=true";
}
