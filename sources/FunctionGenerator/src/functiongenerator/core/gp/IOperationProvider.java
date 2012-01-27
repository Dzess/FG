package functiongenerator.core.gp;

import java.util.Map;

import ec.gp.GPNode;

/**
 * Provides the operation used for structuring responsibility of producing the
 * {@linkplain GPNode} class. Exactly products the
 * {@code  Class<? extends GPNode> }
 * 
 * <p>
 * This abstraction has to be introduced, because some of the classes extending
 * the {@linkplain GPNode} are created at runtime and are parameterized.
 * </p>
 * <p>
 * The class, because of the parameterization, has to provide some interface to
 * tell the GUI how the parameters should be specified. For current
 * implementation scope the simple {@linkplain Map} of type of parameter and its
 * name it is perfectly sufficient. If other extension of variety of operations
 * is needed it might be reasonable to introduce another type of abstraction
 * instead of {@linkplain Map}
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
	 * @return : representation of class which extends the {@linkplain GPNode}.
	 */
	public Class<? extends GPNode> getOperation();

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
	 * Returns the information if this opetation provider should be enabled by
	 * default.
	 */
	public boolean isEnableByDefault();

	/**
	 * Gets the {@linkplain Map} with the key being name of the parameter and
	 * value being the type of the element. *
	 */
	public Map<String, Class<?>> getParameters();

	/**
	 * Parameterizes the object of {@linkplain IOperationProvider} with the same
	 * map concept, but values here are exact <i>values</i> of parameters.
	 */
	public void setParameters(Map<String, Object> params);
}
