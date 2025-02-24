package org.locations.optiroute.exceptions;

import org.springframework.http.HttpStatus;

public class ResponseException extends OptiRouteException {
  private final HttpStatus status;
    public ResponseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

  public HttpStatus getStatus() {
    return status;
  }
}
