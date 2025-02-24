package org.locations.optiroute.exceptions;

public class AddressNotFoundException extends AddressException {
    public AddressNotFoundException(String message,String name) {
        super(message,name);
    }
}
