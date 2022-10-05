package io.github.andrijat98.printingmachineinfoapp.exceptions;

public class MachineNotFoundException extends RuntimeException{
	public MachineNotFoundException(String message) {
		super(message);
	}
}
