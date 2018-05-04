package rebel.hudinstaller.util;

import org.apache.commons.io.FileUtils;
import rebel.hudinstaller.lib.Constants;
import rebel.hudinstaller.ui.HudUI;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Contains methods needed to actually download the HUD
 */

public class Tools
{
    private static String strInstallPath = "";
    private static File zipPath;
    private static URL urlHUD;

    static
    {
        try
        {
            urlHUD = new URL(Constants.GIT_URL);        // URL to download the HUD from
        } catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    //Public Methods

    /**
     * Installs the HUD
     *
     * @throws Exception Throws an error if the HUD could not download
     */
    public static void installHud() throws Exception
    {
        checkOS();                  // Checks what the OS is
        if(checkInstalled())        // Checks if the HUD is installed
        {
            throw new Exception("The HUD is already installed", new Exception("Installation Error"));
        }
        downloadZip();              // Downloads the HUD as a ZIP file
        extractZip();               // Extracts the ZIP file to a temporary folder
        copyHud();                  // Copies the required files out of the temporary folder
        cleanUp();                  // Deletes the ZIP download and temporary files
    }

    /**
     * Installs the HUD if it is being updated
     *
     * @throws Exception Throws an error if the HUD could not download
     */
    public static void installHudUpdating() throws Exception
    {
        checkOS();      // Checks what the OS is
        downloadZip();  // Downloads the HUD as a ZIP file
        extractZip();   // Extracts the ZIP file to a temporary folder
        copyHud();      // Copies the required files out of the temporary folder
        cleanUp();      // Deletes the ZIP download and temporary files
    }

    /**
     * Checks if there is an update available to the HUD
     *
     * @return Returns 1 if the user wants to update, 0 if they do not
     * @throws Exception Throws an error if the HUD is not installed
     */
    public static boolean updateHud() throws Exception
    {
        checkOS();                  // Checks what the OS is
        if(checkInstalled())        // Checks if the HUD is installed
            return checkVersion();  // Checks if an update is available
        else
            throw new Exception("The HUD is not currently installed", new Exception("Update Error"));
    }

    /**
     * Checks if there is an update to the installer
     *
     * @return Returns 1 if the user wants to update, 0 if they do not
     */
    public static boolean updateInstaller()
    {
        return checkInstallerVersion();     // Checks what the installer version is
    }

    /**
     * Removes the installed HUD from the system
     *
     * @throws Throwable Throws an error if the HUD is not installed
     */
    public static void removeHud() throws Exception
    {
        checkOS();
        if(checkInstalled())
            delete(new File(strInstallPath + "RebelHud/"));
        else
            throw new Exception("The HUD is not currently installed", new Exception("Removal Error"));
    }

    /**
     * Deletes the ZIP download and the temporary folder
     */
    public static void cleanUp()
    {
        delete(new File(strInstallPath + "temp/"));
        delete(new File(strInstallPath + "dl.zip"));
    }


    // Private Methods

    /**
     * Checks if the HUD is currently installed
     *
     * @return Returns True if the HUD is installed
     */
    private static boolean checkInstalled()
    {
        File folder = new File(strInstallPath + "RebelHud/version.txt");
        return folder.exists();        // If the file exists
    }

    /**
     * Checks what the operation system the user has
     *
     * @return Returns 1 if an operation system is found
     * @throws Exception Throws an error if it is unknown
     */
    private static int checkOS() throws Exception
    {
        int count = 0;
        while(count < Constants.tf2Locations.length)
        {
            File folder = new File(Constants.tf2Locations[count]);      // Sets the file to the path at index count
            if(folder.exists())                                         // If the folder exists
            {
                zipPath = new File(Constants.tf2Locations[count] + "dl.zip");
                strInstallPath = Constants.tf2Locations[count];
                return 1;
            }
            count++;
        }
        throw new Exception("TF2 could not be found", new Throwable("TF2 Not Installed"));
    }

    /**
     * Checks if any updates are available for the HUD
     *
     * @return Returns True if the user wants to update, False if they do not
     * @throws Exception an error if the HUD is not installed
     */
    private static boolean checkVersion() throws Exception
    {
        try
        {
            Scanner in;
            int choice;
            String input1;
            String input2;
            double currentVersion;      // Version of the HUD currently installed
            double latestVersion;       // Latest version of the HUD

            in = new Scanner(new URL(Constants.LATEST_HUD).openStream());   // Opens connection to online version.txt
            input1 = in.next();
            latestVersion = toDouble(input1);                               // Reads in the latest version
            in.close();

            in = new Scanner(new File(strInstallPath + "RebelHud/version.txt"));    // Opens local version.txt
            input2 = in.next();
            currentVersion = toDouble(input2);      // Reads in the current version
            in.close();

            if(latestVersion > currentVersion)      // If there is a newer version
            {
                String strUpdateMsg = "There are updates availible.\n"
                        + "Current version: " + input2 + "\n"
                        + "Latest Version: " + input1 + "\n"
                        + "Would you like to install the update?";
                Object[] options = {"Yes", "No"};
                choice = JOptionPane
                        .showOptionDialog(null, strUpdateMsg,
                                          "Availible Updates", JOptionPane.YES_NO_OPTION,
                                          JOptionPane.INFORMATION_MESSAGE,
                                          null, options, options[0]);
                return (choice == 0);
            }
            else    // If there is not a newer version
            {
                String strUpdateMsg = "There are no updates availible.\n";
                JOptionPane.showMessageDialog(null, strUpdateMsg,
                                              "Availible Updates", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        } catch(FileNotFoundException e)
        {
            throw new Exception("The HUD is not currently installed", new Exception("Update Error"));
        }
    }

    /**
     * Checks if any updates are available for the installer
     *
     * @return Returns True if the user wants to update the installer
     */
    private static boolean checkInstallerVersion()
    {
        int answer = -1;
        String path = HudUI.class.getProtectionDomain().
                getCodeSource().getLocation().getPath();    // Path of the running JAR file
        try
        {
            Scanner in = new Scanner(new URL(Constants.LATEST_INSTALLER).openStream());
            // Opens connection to the version.txt

            double inputVersion = in.nextDouble();          // Reads in the current version
            in.close();
            if(inputVersion > Constants.INSTALLER_VERSION)  // If there is a newer version
            {
                answer = JOptionPane.showConfirmDialog(null, "A new version of this " +
                                                               "installer is available" +
                                                               "\nWould you like to install it?",
                                                      "Installer Update", JOptionPane.YES_NO_OPTION,
                                                      JOptionPane.INFORMATION_MESSAGE);
            }

            if(answer == 0)        // If the user wants to update
            {
                Tools.downloadInstaller(URLDecoder.decode(path, "UTF-8"));
                JOptionPane.showMessageDialog(null,
                                              "The update was completed successfuly.\n"
                                                      + "Please restart the application in order" +
                                                      " for the changes take effect.");
                return true;
            }
            else                    // If the user does not want to update
            {
                return false;
            }
        } catch(IOException e)
        {
            JOptionPane.showMessageDialog(null,
                                          "ERROR: " + "Cannot Check Version Info",
                                          "ERROR",
                                          JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Downloads the new version of the installer
     *
     * @param path The path of the current installer
     */
    private static void downloadInstaller(String path)
    {
        File installerPath = new File(path);
        try
        {
            URL urlInstaller = new URL(Constants.INSTALLER_URL);        // URL of the JAR download
            dl(installerPath, urlInstaller);                            // Downloads the JAR file
        } catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,
                                          "ERROR: " + e.getMessage(),
                                          "Download error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Downloads the ZIP of the HUD
     *
     * @throws Exception Throws a download error if it cannot download
     */
    private static void downloadZip() throws Exception
    {
        try
        {
            dl(zipPath, urlHUD);        // Downloads the HUD
        } catch(IOException e)
        {
            throw new Exception("The HUD could not be downloaded.", new Exception("Download Error", e));
        }
    }

    /**
     * Extracts the ZIP file to a temporary folder
     *
     * @throws Exception Throws an error if it cannot extract
     */
    private static void extractZip() throws Exception
    {
        try
        {
            unZip(zipPath, new File(strInstallPath + "temp/"));        // Unzips the ZIP file
        } catch(IOException e)
        {
            throw new Exception("The HUD could not be installed.\nPlease exit TF2 if it is open.",
                                new Exception("Extraction Error", e));
        }
    }

    /**
     * Copies the folders from the temporary folder to the HUD installation path
     *
     * @throws Exception Throws an error if the files cannot copy
     */
    private static void copyHud() throws Exception
    {
        try
        {
            FileUtils.copyDirectory(new File(strInstallPath + "temp/RebelHud-Master/custom/"),
                                    new File(strInstallPath));
        } catch(IOException e)
        {
            throw new Exception("The HUD could not be installed. File is being used by another program." +
                                "\nPlease exit TF2 if it is open.", new Exception("File copying error", e));
        }
    }

    /**
     * Unzips a ZIP file
     *
     * @param zipPath  The location where the ZIP was downloaded to
     * @param destPath The location where the files will be extracted to
     * @throws IOException Throws an error if the ZIP cannot be extracted
     */
    private static void unZip(File zipPath, File destPath) throws IOException
    {
        if(!destPath.exists())
        {
            destPath.mkdir();
        }
        ZipInputStream in = new ZipInputStream(new FileInputStream(zipPath));   // Input stream to read from the ZIP
        ZipEntry entry = in.getNextEntry();                                     // The entry from the ZIP file
        while(entry != null)                                                    // While there are still entries
        {
            String filePath = destPath + File.separator + entry.getName();
            if(!entry.isDirectory())
            {
                extract(in, filePath);
            }
            else
            {
                File dir = new File(filePath);
                dir.mkdir();
            }
            in.closeEntry();
            entry = in.getNextEntry();
        }
        in.close();
    }

    /**
     * Extracts the ZIP file
     *
     * @param filePath Where to extract the files to
     * @param in Input stream of the file
     * @throws IOException
     */
    private static void extract(ZipInputStream in, String filePath) throws IOException
    {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytes = new byte[4096];
        int read ;
        while((read = in.read(bytes)) != -1)
        {
            out.write(bytes, 0, read);
        }
        out.close();
    }

    /**
     * Downloads a ZIP file
     *
     * @param file The file that will be downloaded
     * @param url  The URL of the file to download
     * @throws Exception Throws an error if the file cannot download
     */
    private static void dl(File file, URL url) throws Exception
    {
        if(file != null)
        {
            InputStream in;
            OutputStream out;
            in = url.openStream();
            out = new FileOutputStream(file);
            // Begin transfer
            transfer(in, out);
            in.close();
            out.close();
        }
    }

    /**
     * Deletes a file
     *
     * @param file The file to be deleted
     */
    private static void delete(File file)
    {
        if(file.isDirectory())
        {
            if(file.list().length == 0)
            {
                file.delete();
            }
            else
            {
                String files[] = file.list();
                for(String path : files)
                {
                    File deleteFile = new File(file, path);
                    delete(deleteFile);
                }
                if(file.list().length == 0)
                {
                    file.delete();
                }
            }
        }
        else
        {
            file.delete();
        }
    }

    /**
     * Transfers the download from the instream to a folder
     *
     * @param in  Stream to read from
     * @param out Stream to write to
     * @throws IOException
     */
    private static void transfer(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[2048];
        int bytesRead;
        while((bytesRead = in.read(buffer)) > 0)
        {
            out.write(buffer, 0, bytesRead);
        }
    }

    /**
     * Takes a string and converts to a double
     *
     * @param str Version number as a String
     * @return Double
     */
    private static double toDouble(String str)
    {
        int firstDecimal = str.indexOf('.');
        String temp = str;
        if(firstDecimal != -1)
        {
            String firstHalf = str.substring(0, firstDecimal + 1);
            String secondHalf = str.substring(firstDecimal + 1);
            temp = firstHalf + secondHalf.replaceAll("\\.", "");
        }
        return new Double(temp).doubleValue();
    }
}

