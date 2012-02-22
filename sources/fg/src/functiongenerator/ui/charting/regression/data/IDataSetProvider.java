package functiongenerator.ui.charting.regression.data;

import org.jfree.data.general.AbstractDataset;

import ec.gp.GPIndividual;

/**
 * Creates the data set for charting.
 * 
 * @author Piotr Jessa
 * 
 */
public interface IDataSetProvider {

	/**
	 * Create the data set from the passed best individual
	 */
	public abstract AbstractDataset getDataSet(GPIndividual individual);

}