package commons.utils;

import java.io.File;
import java.io.IOException;

public class Shell {
    public static boolean execute(String command, String workingDirectoryPath) {
        try {
            File workingDirectory = new File(workingDirectoryPath);
            Process process = Runtime.getRuntime().exec(command, null, workingDirectory);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
