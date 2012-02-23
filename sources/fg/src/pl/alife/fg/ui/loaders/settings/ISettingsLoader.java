package pl.alife.fg.ui.loaders.settings;

import java.io.File;
import java.io.IOException;

import pl.alife.fg.core.Settings;


/**
 * Specifies the interface for saving as the setting of current preferences.
 * 
 * Uses the {@linkplain Settings} objects for the data state preserving, because
 * they are UI agnostic.
 * 
 * @author Piotr Jessa
 */
public interface ISettingsLoader {

    /**
     * Creates the {@linkplain Settings} instance basing on the data read.
     * 
     * @param file
     *            : file in which the engine state is preserved
     * @return New instance of settings with all valid data set.
     * @throws IOException
     */
    public Settings loadFromFile(File file) throws IOException;

    /**
     * Saves the engine into file.
     * 
     * @param file
     *            : where to be saved
     * @param settings
     *            : the model of the experiment
     * @throws IOException
     */
    public void saveToFile(File file, Settings settings) throws IOException;
}
