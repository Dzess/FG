package functiongenerator.core.gp;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import ec.gp.GPNode;

/**
 * Provides the abstraction used for structuring responsibility of producing the
 * {@linkplain GPNode} class. Exactly products the
 * {@code  List<Class<? extends GPNode>> }. Because some
 * {@linkplain IOperationProvider} could potentially create more than one
 * operator.
 * 
 * <p>
 * This abstraction has to be introduced, because some of the classes extending
 * the {@linkplain GPNode} are created at runtime and are parameterized.
 * </p>
 * <p>
 * The class, because of the parameterization, has to provide some interface to
 * tell the GUI how the parameters should be specified. For current
 * implementation scope the simple {@linkplain SortedMap} of type of parameter
 * and its name it is perfectly sufficient. The default vales are also presented
 * by {@linkplain SortedMap}. The values to be set are passed using ordinary
 * {@linkplain Map}.
 * </p>
 * 
 * <p>
 * If other extension of variety of operations is needed it might be reasonable
 * to introduce another type of abstraction instead of those
 * {@linkplain SortedMap} and {@linkplain Map} ways of GUI information exchange.
 * </p>
 * 
 * 
 * @author Piotr Jessa
 * 
 */
public interface IOperationProvider {

	/**
	 * <i>Creates</i> the class and makes it loadable to the JVM class pool. If
	 * class is already available then its {@linkplain Class} is returned.
	 * 
	 * @return list representation of classes which extends the
	 *         {@linkplain GPNode}.
	 * @throws ClassNotFoundException
	 *             when the loading of the class is not successful.
	 * 
	 * @throws IllegalArgumentException
	 *             when not all the parameters has been successfully set.
	 */
	public List<Class<? extends GPNode>> getOperations() throws ClassNotFoundException, IllegalArgumentException;

	/**
	 * Gets the Human Readable name of the operation.
	 */
	public String getName();

	/**
	 * Returns the additional information about the operation. What the
	 * operation will be doing when it will act as {@linkplain GPNode}
	 */
	public String getComment();

	/**
	 * Returns the information if this operation provider should be enabled by
	 * default.
	 */
	public boolean isEnableByDefault();

	/**
	 * Gets the {@linkplain SortedMap} with the key being name of the parameter
	 * and value being the type of the element. The {@linkplain SortedMap} is
	 * used because of the current implementation of GUI which uses position of
	 * the elements to manage values.
	 */
	public SortedMap<String, Class<?>> getParameters();

	/**
	 * Parameterizes the object of {@linkplain IOperationProvider} with the same
	 * map concept, but values here are exact <i>values</i> of parameters.
	 */
	public void setParameters(Map<String, Object> params);

	/**
	 * Gets the default values for each of the parameter. The
	 * {@linkplain SortedMap} returned here should be equal to the
	 * {@linkplain SortedMap} of method {@code getParameters()} in the field of
	 * available keys.
	 */
	public SortedMap<String, String> getParametersDefault();

}
