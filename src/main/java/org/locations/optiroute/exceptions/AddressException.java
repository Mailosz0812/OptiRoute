package org.locations.optiroute.exceptions;

public class AddressException extends OptiRouteException{
  private String name;
    public AddressException(String message,String name) {
        super(message);
        this.name = name;
    }

  public String getName() {
    return name;
  }
}
