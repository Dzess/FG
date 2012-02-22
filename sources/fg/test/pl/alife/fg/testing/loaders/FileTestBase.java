package pl.alife.fg.testing.loaders;

import java.io.File;

/**
 * Helper base class for accessing file system in favor of integration level
 * testing.
 * 
 * @author Piotr Jessa
 * 
 */
public class FileTestBase {

    protected File file;

    public FileTestBase() {
        super();
    }

    private String getLocation(String fileName) {
        return "resources" + File.separator + fileName;
    }

    protected File getFile(String fileName) {
        return new File(getLocation(fileName));
    }

}