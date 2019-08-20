package org.apache.servicecomb.toolkit.goodbye;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodbyeController {

  @GetMapping("/goodbye/{name}")
  public String sayGoodbye(@PathVariable("name") String name) {
    return "Goodbye," + name;
  }
}
