package parser;

/*
 * An abstract method in order to be able to pass methods in as arguments
 * You can only have 1 abstract method - therefore they must all return true/false
 * More info:
 * http://stackoverflow.com/questions/23682243/lambda-can-only-be-used-with-functional-interface
 */

@FunctionalInterface
public interface Interface {
    public boolean interfaceMethod();
}
