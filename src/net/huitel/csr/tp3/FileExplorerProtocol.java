package net.huitel.csr.tp3;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author florian
 */
public class FileExplorerProtocol {

    public String currentFolder;

    public static final String[] commandList = {"cd","ls","get"};

    /**
     *
     * @param initialFolder the default folder
     */
    public FileExplorerProtocol(String initialFolder){
        currentFolder = initialFolder;
    }

    /**
     * Processes an input string from the client
     * @param inputLine String sent from the client to server
     * @return The result string containing the server's answer to the client
     */
    public String processInput(String inputLine) {

        String result = "";
        if(inputLine == null){
            //initial talk
            result = "Welcome to the FTP server\n.";
        } else if(inputLine.equals("exit")){
            result = "Bye.";
        } else {
            result = "command received "+inputLine+"\n.";
            String command = "";
            String value = "";
            String delimiter = " ";
            String[] parsedInput = inputLine.split(delimiter, 2);
            if(parsedInput.length == 0){
                result = "Empty command received\n.";
            } else {
                if(parsedInput.length == 1){
                    command = parsedInput[0];
                    value = ".";
                } else {
                    command = parsedInput[0];
                    value = parsedInput[1];
                }
                System.out.println("Command is:"+command);
                if(Arrays.asList(commandList).contains(command)){
                    // known command
                    if(command.equals("cd") && !value.equals(".")){
                        System.out.println("Switching folder");
                        result = switchFolder(value);
                    } else if(command.equals("ls")) {
                        System.out.println("Listing folder");
                        result = listFolder(value);
                    } else if(command.equals("get") && !value.equals(".")) {
                        System.out.println("Getting file");
                        result = getFile(value);
                    }
                    else {
                        // unknown command
                        result = "Unknown command or bad formatting\n.";
                    }
                } else {
                    // unknown command
                    result = "Unknown command or bad formatting\n.";
                }
            }
        }

        return result;
    }


    private String switchFolder(String value) {
        String result = "";
        if (value.equals("."))
        {
            result = "Current folder remains" + currentFolder + "\n.";
        }
        else if(value.equals("..")){
            File f = new File(currentFolder);
            currentFolder = f.getParent();
            result = "Switched current folder to " + currentFolder + "\n.";
        } else {
            String testedPath = currentFolder + "/" + value;
            File fi = new File(testedPath);
            boolean exists = fi.isDirectory() ? true : false;
            if(exists){
                System.out.println("Switched current folder to " + testedPath);
                result = "Switched current folder to " + testedPath + "\n.";
                currentFolder = testedPath;
            } else {
                System.out.println("Unknown folder path");
                result = "Unknown folder path\n.";
            }
        }
        return result;
    }

    private String listFolder(String value) {
        String result = "";
        ArrayList resultDirectories = new ArrayList<String>();
        ArrayList resultFiles = new ArrayList<String>();
        String testedPath = currentFolder + "/" + value;
        File fi = new File(testedPath);
        boolean exists = fi.isDirectory() ? true : false;
        if(exists){
            File listFile[] = fi.listFiles();
            if(listFile == null){
                System.out.println("Folder is empty");
                result = "Folder is empty\n.";
            } else {
                for(int i = 0; i<listFile.length; i++){
                    if(listFile[i].isDirectory()){
                        resultDirectories.add(listFile[i].getName());
                    } else {
                        resultFiles.add(listFile[i].getName());
                    }
                }
                result = "Files in "+value+" directory are:\n";
                for(int i = 0; i<resultFiles.size(); i++) {
                    result+=resultFiles.get(i)+"\n";
                }
                result += "Directories in "+value+" directory are:\n";
                for(int i = 0; i<resultDirectories.size(); i++) {
                    result+=resultDirectories.get(i)+"\n";
                }
                result+=".";
                System.out.println("listing result is:");
                System.out.println(result);
            }
        } else {
            System.out.println("Folder does not exist");
            result = "Folder does not exist\n.";
        }
        return result;
    }

    private String getFile(String value) {
        String result = "";
        String testedPath = currentFolder + "/" + value;
        File fi = new File(testedPath);
        boolean exists = fi.isFile() ? true : false;
        if(exists){
            result = "Sending file...\n.";
        }else{
            result = "File does not exist\n.";
        }
        return result;
    }

}