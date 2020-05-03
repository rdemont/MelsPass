package ch.rmbi.melspass.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public class FtpUtils {
    private static final String TAG = "ch.rmbi.melspass.utils.FtpUtils";
    private FTPClient ftpClient = null ;

    public FtpUtils()
    {
        ftpClient = new FTPClient();
    }

    public boolean ftpConnect(String host, String username, String password,int port) {
        try {

            // connecting to the host
            ftpClient.connect(host, port);

            // now check the reply code, if positive mean connection success
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                // login using username & password
                boolean status = ftpClient.login(username, password);

                /*
                 * Set File Transfer Mode
                 *
                 * To avoid corruption issue you must specified a correct
                 * transfer mode, such as ASCII_FILE_TYPE, BINARY_FILE_TYPE,
                 * EBCDIC_FILE_TYPE .etc. Here, I use BINARY_FILE_TYPE for
                 * transferring text, image, and compressed files.
                 */
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();

                return status;
            }
        } catch (Exception e) {
            Log.d(TAG, "Error: could not connect to host " + host);
        }

        return false;
    }

    public boolean isConnected()
    {
        return ftpClient.isConnected() ;
    }

    public boolean ftpDisconnect() {
        try {
            if (ftpClient != null) {
                ftpClient.logout();
                ftpClient.disconnect();
                return true;
            }
        } catch (Exception e) {
            Log.d(TAG, "Error occurred while disconnecting from ftp server.");
        }

        return false;
    }

    public String ftpGetCurrentWorkingDirectory() {
        try {
            String workingDir = ftpClient.printWorkingDirectory();
            return workingDir;
        } catch (Exception e) {
            Log.d(TAG, "Error: could not get current working directory.");
        }

        return null;
    }

    // Method to change working directory:

    public boolean ftpChangeDirectory(String directory_path) {
        try {
            ftpClient.changeWorkingDirectory(directory_path);
        } catch (Exception e) {
            Log.d(TAG, "Error: could not change directory to " + directory_path);
        }

        return false;
    }

    // Method to list all files in a directory:

    public String[] ftpPrintFilesList(String dir_path) {
        String[] fileList = null;
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(dir_path);
            int length = ftpFiles.length;
            fileList = new String[length];
            for (int i = 0; i < length; i++) {
                String name = ftpFiles[i].getName();
                boolean isFile = ftpFiles[i].isFile();

                if (isFile) {
                    fileList[i] = "File :: " + name;
                    Log.i(TAG, "File : " + name);
                } else {
                    fileList[i] = "Directory :: " + name;
                    Log.i(TAG, "Directory : " + name);
                }
            }
            return fileList;
        } catch (Exception e) {
            e.printStackTrace();
            return fileList;
        }
    }

    // Method to create new directory:

    public boolean ftpMakeDirectory(String new_dir_path) {
        try {
            boolean status = ftpClient.makeDirectory(new_dir_path);
            return status;
        } catch (Exception e) {
            Log.d(TAG, "Error: could not create new directory named "
                    + new_dir_path);
        }

        return false;
    }

    // Method to delete/remove a directory:

    public boolean ftpRemoveDirectory(String dir_path) {
        try {
            boolean status = ftpClient.removeDirectory(dir_path);
            return status;
        } catch (Exception e) {
            Log.d(TAG, "Error: could not remove directory named " + dir_path);
        }

        return false;
    }

    // Method to delete a file:

    public boolean ftpRemoveFile(String filePath) {
        try {
            boolean status = ftpClient.deleteFile(filePath);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Method to rename a file:

    public boolean ftpRenameFile(String from, String to) {
        try {
            boolean status = ftpClient.rename(from, to);
            return status;
        } catch (Exception e) {
            Log.d(TAG, "Could not rename file: " + from + " to: " + to);
        }

        return false;
    }

    // Method to download a file from FTP server:

    /**
     * mFTPClient: FTP client connection object (see FTP connection example)
     * srcFilePath: path to the source file in FTP server desFilePath: path to
     * the destination file to be saved in sdcard
     */
    public boolean ftpDownload(String srcFilePath, OutputStream out) {
        boolean status = false;
        try {

            status = ftpClient.retrieveFile(srcFilePath, out);


            return status;
        } catch (Exception e) {
            Log.d(TAG, "download failed");
        }

        return status;

    }

    public boolean ftpDownload(String srcFilePath, String desFilePath) {
        boolean status = false;
        try {

            FileOutputStream desFileStream = new FileOutputStream(desFilePath);
            status = ftpDownload(srcFilePath,desFileStream);
            desFileStream.close();
        } catch (Exception e) {
            Log.d(TAG, "download failed");
        }
        return status;
    }

    // Method to upload a file to FTP server:

    /**
     * mFTPClient: FTP client connection object (see FTP connection example)
     * srcFilePath: source file path in sdcard desFileName: file name to be
     * stored in FTP server desDirectory: directory path where the file should
     * be upload to
     */
    public boolean ftpUpload(InputStream in, String desFileName, String desDirectory) {
        boolean status = false;
        try {
            status = ftpClient.storeFile(desFileName, in);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "upload failed: " + e);
        }

        return status;
    }


    public boolean ftpUpload(String srcFilePath, String desFileName, String desDirectory) {
        boolean status = false;
        try {
            FileInputStream srcFileStream = new FileInputStream(srcFilePath);
            status = ftpUpload(srcFileStream,desFileName,desDirectory);
            srcFileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "upload failed: " + e);
        }
        return status;
    }

}
