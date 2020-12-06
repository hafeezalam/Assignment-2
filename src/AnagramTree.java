import java.util.*;
import java.io.*;

public class AnagramTree
{
    private BST<String> tree;
    
    
    public static Scanner getFileScanner(String filename)
    {
        Scanner myFile;
        try { myFile = new Scanner(new FileReader(filename)); }
        catch (Exception e)
        {
            System.out.println("File not found: " + filename);
            return null;
        }
        return myFile;
    }
}