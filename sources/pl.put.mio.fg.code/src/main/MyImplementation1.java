package main;

public class MyImplementation1 extends MySuperClass {

	@Override
	public void doSth() {
		System.out.println("Welcome from the " + this.getClass().getCanonicalName());
	}

}