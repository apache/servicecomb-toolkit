package org.apache.servicecomb.toolkit.oasv.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OasObjectPropertyLocationTest {

  @Test
  public void toPathString() {
    assertEquals("$.foo.bar",
        OasObjectPropertyLocation.toPathString(OasObjectPropertyLocation.root().property("foo").property("bar")));

    assertEquals("", OasObjectPropertyLocation.toPathString(null));
  }
}
