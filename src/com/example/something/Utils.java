package com.example.something;

import java.io.File;
import java.lang.SecurityException;
import java.util.List;
import java.util.ArrayList;

import android.os.Environment;


/**
 * Utility singleton.
 * I have no idea when to use singletons,
 * or even if this is one,
 * but this works for me.
 */
public class Utils {
	// Prevent any class instantiation.
	private Utils() {}

    // Root directory
    private static File root_;

    /**
     * @return Root directory string.
     */
    public static String rootDirName(){
        return Environment.getExternalStorageDirectory().toString() + "/audioFiles";
    }

    /**
     * Get the root directory where the audio files are stored.
     * The root is created if it doesn't exist yet.
     * @return root directory as a File object.
     */
    public static File root(){
        if (root_ == null){
            root_ = new File(rootDirName());
            if (!root_.exists()){
                try {
                    root_.mkdir();
                } catch (SecurityException e){
                    e.printStackTrace();
                }
            }
        }
        return root_;
    }

    /**
     * @return List of audio files directly under the root.
     */
    public static List<File> audioFiles(){
    	return getListFiles(root());
    }

    /**
     * Get a list of files in the parent dir.
     * List will be empty if the dir doesn't exist.
     * @param  parentDir Directory to search
     * @return           List of file objects.
     */
    public static List<File> getListFiles(File parentDir) {
        ArrayList inFiles = new ArrayList<File>();

        // listFiles() returns null if dir doesn't exist.
        File[] files = parentDir.listFiles();
        if (files == null){
            return inFiles;
        }

        // Add the files
        for (File file : files) {
            inFiles.add(file);
        }

        return inFiles;
    }
}