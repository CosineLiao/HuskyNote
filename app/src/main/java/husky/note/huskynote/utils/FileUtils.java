package husky.note.huskynote.utils;


import java.io.File;

public class FileUtils
{
    public static boolean isImageFile(File file) {
        return file != null && file.isFile() && file.getName().matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)");
    }
}
