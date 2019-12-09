package org.apache.servicecomb.toolkit.cli;

public class ValidationFailedException extends RuntimeException {
  public ValidationFailedException(String msg) {
    super(msg);
  }
}
