package org.apache.servicecomb.toolkit.cli;

public class CommandFailedException extends RuntimeException {
  public CommandFailedException(String msg) {
    super(msg);
  }
}
