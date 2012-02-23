package pl.alife.fg.core.gp;

import java.util.List;

/**
 * Creates the available {@linkplain IOperationProvider} objects
 * 
 * @author Piotr Jessa
 * 
 */
public interface IOperationProviderFactory {

    /**
     * Gets all the possible operation providers.
     * 
     * @return
     */
    public List<IOperationProvider> getAvaliable();
}
