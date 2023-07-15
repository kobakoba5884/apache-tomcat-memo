package hello.world.utils.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryUtil {
    public static Path ensureDirectoryExists(Path directoryPath) throws IOException{
        if(!Files.exists(directoryPath)){
            Files.createDirectories(directoryPath);
        }

        return directoryPath;
    }
}
