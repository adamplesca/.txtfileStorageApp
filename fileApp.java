import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class fileApp {
    public static void main(String[] args) {
        while (true) {
            String[] options = {
                "Create Folder",
                "Create File",
                "Write File",
                "View File",
                "Delete File",
                "Delete Folder",
                "View Folder",
                "Exit"
            };

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Navigation",
                    "Text File System Storage",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (options[choice].equals("Exit")) {
                JOptionPane.showMessageDialog(null, "Exiting the application. Goodbye!");
                break;
            }

            switch (options[choice]) {
                case "Create Folder" -> {
                    String folderName = JOptionPane.showInputDialog(null, "Enter the name of the folder:");
                    createFolder(folderName);
                }
                case "Create File" -> {
                    String folderName = JOptionPane.showInputDialog(null, "Enter the folder name (leave empty for current directory):");
                    String fileName = JOptionPane.showInputDialog(null, "Enter the name of your file:");
                    createFile(folderName, fileName);
                }
                case "Write File" -> {
                    String folderName = JOptionPane.showInputDialog(null, "Enter the folder name (leave empty for current directory):");
                    String fileName = JOptionPane.showInputDialog(null, "Enter the name of the file you want to write to:");
                    writeFile(folderName, fileName);
                }
                case "View File" -> {
                    String folderName = JOptionPane.showInputDialog(null, "Enter the folder name (leave empty for current directory):");
                    String fileName = JOptionPane.showInputDialog(null, "Enter the name of the file you want to view:");
                    viewFile(folderName, fileName);
                }
                case "Delete File" -> {
                    String folderName = JOptionPane.showInputDialog(null, "Enter the folder name (leave empty for current directory):");
                    String fileName = JOptionPane.showInputDialog(null, "Enter the name of the file you want to delete:");
                    deleteFile(folderName, fileName);
                }
                case "Delete Folder" -> {
                    String folderName = JOptionPane.showInputDialog(null, "Enter the name of the folder to delete:");
                    deleteFolder(folderName);
                }
                case "View Folder" -> {
                    String folderName = JOptionPane.showInputDialog(null, "Enter the name of the folder you want to view:");
                    viewFolder(folderName);
                }
            }
        }
    }

    private static void createFolder(String folderName) {
        File folder = new File(folderName);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                JOptionPane.showMessageDialog(null, "Folder created: " + folderName);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create folder.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Folder already exists.");
        }
    }

    private static void createFile(String folderName, String fileName) {
        try {
            File myObj;
            if (folderName.isEmpty()) {
                myObj = new File(fileName + ".txt");
            } else {
                myObj = new File(folderName + "/" + fileName + ".txt");
            }
            if (myObj.createNewFile()) {
                JOptionPane.showMessageDialog(null, "File created: " + myObj.getName());
            } else {
                JOptionPane.showMessageDialog(null, "File already exists.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred.");
            e.printStackTrace();
        }
    }

    private static void writeFile(String folderName, String fileName) {
        try {
            FileWriter writer;
            if (folderName.isEmpty()) {
                writer = new FileWriter(fileName + ".txt", true); // Append mode
            } else {
                writer = new FileWriter(folderName + "/" + fileName + ".txt", true); // Append mode
            }
            String content = JOptionPane.showInputDialog(null, "Enter the content you want to write to the file:");
            if (content != null) {
                writer.write(content + "\n");
                writer.close();
                JOptionPane.showMessageDialog(null, "Successfully wrote to the file.");
            } else {
                JOptionPane.showMessageDialog(null, "No content provided. Returning to menu.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred.");
            e.printStackTrace();
        }
    }

    private static void viewFile(String folderName, String fileName) {
        try {
            File myObj;
            if (folderName.isEmpty()) {
                myObj = new File(fileName + ".txt");
            } else {
                myObj = new File(folderName + "/" + fileName + ".txt");
            }
            if (myObj.exists()) {
                Scanner reader = new Scanner(myObj);
                StringBuilder fileContent = new StringBuilder();
                while (reader.hasNextLine()) {
                    fileContent.append(reader.nextLine()).append("\n");
                }
                reader.close();
                JOptionPane.showMessageDialog(null, fileContent.toString());
            } else {
                JOptionPane.showMessageDialog(null, "File does not exist.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred.");
            e.printStackTrace();
        }
    }

    private static void deleteFile(String folderName, String fileName) {
        File myObj;
        if (folderName.isEmpty()) {
            myObj = new File(fileName + ".txt");
        } else {
            myObj = new File(folderName + "/" + fileName + ".txt");
        }
        if (myObj.delete()) {
            JOptionPane.showMessageDialog(null, "Deleted the file: " + myObj.getName());
        } else {
            JOptionPane.showMessageDialog(null, "Failed to delete the file. It may not exist.");
        }
    }

    private static void deleteFolder(String folderName) {
        File folder = new File(folderName);
        if (folder.exists()) {
            // Recursively delete all files and subdirectories
            deleteRecursively(folder);
            JOptionPane.showMessageDialog(null, "Deleted the folder: " + folderName);
        } else {
            JOptionPane.showMessageDialog(null, "Folder does not exist.");
        }
    }

    private static void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteRecursively(f);
                }
            }
        }
        file.delete();
    }

    private static void viewFolder(String folderName) {
        File folder = new File(folderName);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null && files.length > 0) {
                StringBuilder folderContents = new StringBuilder();
                for (File file : files) {
                    folderContents.append(file.getName()).append(file.isDirectory() ? " (Folder)" : " (File)").append("\n");
                }
                JOptionPane.showMessageDialog(null, "Folder contents:\n" + folderContents.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Folder is empty or an error occurred.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Folder does not exist.");
        }
    }
}