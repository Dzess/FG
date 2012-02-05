package functiongenerator.ui.charting.data;

import org.jfree.data.category.CategoryDataset;

/**
 * Creates the data set for charting.
 * 
 * @author Piotr Jessa
 * 
 */
public interface IDataSetProvider {

	public abstract CategoryDataset getDataSet();

}