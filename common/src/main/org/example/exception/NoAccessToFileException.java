package main.org.example.exception;

import java.io.File;

public class NoAccessToFileException extends Throwable {
    public File file;
    public NoAccessToFileException(File file){
        this.file = file;
    }

}
