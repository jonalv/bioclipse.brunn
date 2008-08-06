package net.bioclipse.expression;

public class SuppressedVariableException extends CalculatorException {

    private static final long serialVersionUID = 1L;

    public SuppressedVariableException(String message) {
        super( message );
    }

    public SuppressedVariableException(String message, Throwable cause) {
        super( message, cause );
    }

    public SuppressedVariableException(Throwable cause) {
        super( cause );
    }
}
