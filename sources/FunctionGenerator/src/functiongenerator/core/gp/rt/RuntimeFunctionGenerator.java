package functiongenerator.core.gp.rt;

import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.core.gp.functions.real.Zero;

/**
 * Base class for generating function for symbolic regression during runtime.
 * Depends heavily on Javaassist bytecode generation features.
 * 
 * <p>
 * Follows method template pattern from GOF.
 * </p>
 * <p>
 * This class <b>is not</b> thread safe. Due to JVM {@linkplain ClassLoader}
 * capabilities.
 * </p>
 * 
 * @author Piotr Jessa
 * 
 */
public abstract class RuntimeFunctionGenerator {

	static private final Log logger = LogFactory.getLog(RuntimeFunctionGenerator.class);
	static private int counter = 0;

	static private final String TO_STRING_TEMPLATE = "public String toString() { return %value; }";

	static private String getPackageName(Class<?> cls) {
		return cls.getPackage().getName();
	}

	/**
	 * Generates the binary class and loads it into JVM standard class loader
	 * namespace. Though the exact type of the generated operation is dependent
	 * on the subclasses.
	 * 
	 * <p>
	 * Because multiple classes can be generated the typical names are appended
	 * with number stored in a static field. Static because the nature of the
	 * JVM namespace is also static.
	 * </p>
	 * 
	 * @return the canonical name of the generated and loaded class.
	 */
	public String generateClassAndLoad() throws IllegalArgumentException {

		logger.debug("Reading the JVM Class Pool");

		ClassPool pool = ClassPool.getDefault();

		logger.debug("Geting all the imports");
		pool.importPackage(getPackageName(EvolutionState.class));
		pool.importPackage(getPackageName(GPData.class));
		pool.importPackage(getPackageName(ADFStack.class));
		pool.importPackage(getPackageName(GPIndividual.class));
		pool.importPackage(getPackageName(Problem.class));

		logger.debug("Getting super class");
		Class<?> superClassJVM = this.getSuperClassForOperation();
		CtClass superclass;
		try {
			superclass = pool.getCtClass(superClassJVM.getCanonicalName());
		} catch (NotFoundException e) {
			throw new IllegalArgumentException("The super class for runtime function generatin not found", e);
		}

		logger.debug("Getting name of created class");
		StringBuilder builder = new StringBuilder();

		// get the package where functions should be created
		String packageName = Zero.class.getPackage().getName();
		builder.append(packageName);
		builder.append(".");
		builder.append(this.getClassName());
		// allow multiple classes with nearly the same logic
		builder.append("_" + counter);
		String className = builder.toString();

		CtClass myClass = pool.makeClass(className, superclass);
		logger.debug("Created '" + className + "' within the pool");

		logger.debug("Getting the 'eval' method source code");
		String methodBody = this.getEvalSourceCode();

		logger.debug("Getting the required classed for this source code");
		List<Class<?>> usedClasses = this.getUsedClasses();
		for (Class<?> cls : usedClasses) {
			pool.importPackage(getPackageName(cls));
		}

		logger.debug("Getting the 'toString' method source code");
		String toStringValue = this.getToStringReturnedValue();
		toStringValue = "\"" + toStringValue + "\"";
		String toStrMethodBody = TO_STRING_TEMPLATE.replace("%value", toStringValue);

		CtMethod m;
		CtMethod toStrM;
		Class<?> c = null;
		try {

			toStrM = CtMethod.make(toStrMethodBody, myClass);
			myClass.addMethod(toStrM);

			// adds the body of the overriding method
			m = CtMethod.make(methodBody, myClass);
			myClass.addMethod(m);

			// this line enforces loading this class into the current JVM
			// namespace
			c = myClass.toClass();
		} catch (CannotCompileException e) {
			throw new IllegalArgumentException("The provided function cannot be compiled", e);
		}

		counter += 1;
		return c.getCanonicalName();
	}

	/**
	 * Precise what kind of classes your code will be using in the body of the
	 * method.
	 */
	protected abstract List<Class<?>> getUsedClasses();

	/**
	 * What is the superclass of your ECJ operation implementation
	 */
	protected abstract Class<?> getSuperClassForOperation();

	/**
	 * The body of the GPNode.eval() method should be presented here
	 */
	protected abstract String getEvalSourceCode();

	/**
	 * The value that should be placed as literal. Must be evaluated at the run
	 * time at last.
	 */
	protected abstract String getToStringReturnedValue();

	/**
	 * The short name of what your class will be named
	 */
	protected abstract String getClassName();
}
