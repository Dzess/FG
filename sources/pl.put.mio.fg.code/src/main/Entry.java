package main;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

class JavaSourceFromString extends SimpleJavaFileObject {
	final String code;

	JavaSourceFromString(String name, String code) {
		super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		this.code = code;
	}

	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return code;
	}

	static Iterable<JavaSourceFromString> getJavaSourceFromString(String code) {
		final JavaSourceFromString jsfs;
		jsfs = new JavaSourceFromString("code", code);
		return new Iterable<JavaSourceFromString>() {
			public Iterator<JavaSourceFromString> iterator() {
				return new Iterator<JavaSourceFromString>() {
					boolean isNext = true;

					public boolean hasNext() {
						return isNext;
					}

					public JavaSourceFromString next() {
						if (!isNext)
							throw new NoSuchElementException();
						isNext = false;
						return jsfs;
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}

public class Entry {

	static private List<MySuperClass> classes;

	static private void printClasses() {
		for (MySuperClass m : classes) {
			m.doSth();
		}
	}

	static private void doCompilation() throws Exception {

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null)
			throw new RuntimeException("No compiler. Your platform provides no compiler");

		String program = "class Test{" + "   public static void main (String [] args){" + "      System.out.println (\"Hello, World\");"
				+ "      System.out.println (args.length);" + "   }" + "}";

		Iterable<? extends JavaFileObject> fileObjects;
		fileObjects = JavaSourceFromString.getJavaSourceFromString(program);
		DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticsCollector, null, null);

		CompilationTask task = compiler.getTask(null, fileManager, diagnosticsCollector, null, null, fileObjects);
		task.call();

		Class<?> clazz = Class.forName("Test");
		Method m = clazz.getMethod("main", new Class[] { String[].class });
		Object[] _args = new Object[] { new String[0] };
		m.invoke(null, _args);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		classes = new LinkedList<MySuperClass>();
		classes.add(new MyImplementation1());

		System.out.println("Starting");
		printClasses();

		ClassPool pool = ClassPool.getDefault();
		CtClass superclass = pool.getCtClass(MySuperClass.class.getCanonicalName());
		String className = "MyImplementation2";
		CtClass myClass = pool.makeClass(className, superclass);
		
		System.out.println("Created my class");
		String methodBody = "public void doSth() { System.out.println(\"Welcome from the \" + this.getClass().getCanonicalName());}";
		
		System.out.println("Defined method body");
		CtMethod m = CtMethod.make(methodBody, myClass);
		myClass.addMethod(m);

		Loader loader = new Loader(pool);
		//Class<?> c = loader.loadClass(className);
		myClass.toClass();
		Class<?> c = Class.forName(className);
		
		System.out.println("Loaded class");
		Object myObj =  c.newInstance();
		
		System.out.println("My object loaded into pool");
		
		MySuperClass obj = (MySuperClass) myObj;

		classes.add(obj);

		System.out.println("Ending");
		printClasses();

	}
}
