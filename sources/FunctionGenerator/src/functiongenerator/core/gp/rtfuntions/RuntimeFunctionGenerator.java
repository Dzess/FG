package functiongenerator.core.gp.rtfuntions;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

		logger.debug("Getting the 'toString' method source code");
		String toStrMethodBody = this.getToStringSourceCode();

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

	protected abstract Class<?> getSuperClassForOperation();

	protected abstract String getEvalSourceCode();

	protected abstract String getToStringSourceCode();

	protected abstract String getClassName();
}
