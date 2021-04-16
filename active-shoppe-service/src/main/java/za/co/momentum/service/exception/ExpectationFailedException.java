package za.co.momentum.service.exception;

public class ExpectationFailedException extends RuntimeException {

	public ExpectationFailedException() {
	}

	public ExpectationFailedException(String s) {
		super(s);
	}

	public ExpectationFailedException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public ExpectationFailedException(Throwable throwable) {
		super(throwable);
	}

	public ExpectationFailedException(String s, Throwable throwable, boolean b, boolean b1) {
		super(s, throwable, b, b1);
	}
}
