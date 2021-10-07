package com.badlogic.drop.ch06.rhythmtapper;

import com.badlogic.gdx.files.FileHandle;

import javax.swing.*;
import java.io.File;

public class FileUtils {
    private static boolean finished;
    private static FileHandle fileHandle;
    private static int openDialog = 1;
    private static int saveDialog = 2;

    public static FileHandle showOpenDialog(){
        return showDialog(openDialog);
    }

    public static FileHandle showSaveDialog(){
        return showDialog(saveDialog);
    }

    private static FileHandle showDialog(int dialogType){
        int fileChooseResult;
//        new JFXPanel();
//
//        finished = false;
//        Platform.runLater(
//                () ->{
//                    FileChooser fileChooser = new FileChooser();
//                    File file;
//
//                    if(dialogType == openDialog){
//                        file = fileChooser.showOpenDialog(null);
//                    } else {
//                        file  = fileChooser.showSaveDialog(null);
//                    }
//
//                    if(file != null)
//                        fileHandle = new FileHandle(file);
//                    else
//                        fileHandle = null;
//                }
//        );
//
//        while(!finished){
//            // waiting for FileChooser to close.
//        }

        JFileChooser fileChooser = new JFileChooser();
        File file = null;
        if(dialogType == openDialog){
            if( fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                file = fileChooser.getSelectedFile();
            }
        } else {
            if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                file = fileChooser.getSelectedFile();
            }
        }

        if (file !=null){
            fileHandle = new FileHandle(file);
        } else {
            fileHandle = null;
        }

        return fileHandle;
    }

}
