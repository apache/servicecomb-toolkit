package org.apache.servicecomb.toolkit.oasv.compatibility;

import static java.util.Collections.emptyList;

import java.util.List;

import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public abstract class CompatibilityCheckParser {

  private CompatibilityCheckParser() {
    // singleton
  }

  public static SwaggerParseResult parseYaml(String yaml) {
    OpenAPIV3Parser parser = new OpenAPIV3Parser();
    return parser.readContents(yaml, null, createParseOptions());
  }

  public static List<String> checkSyntax(String yaml) {
    SwaggerParseResult result = parseYaml(yaml);
    return result.getMessages() == null ? emptyList() : result.getMessages();
  }

  private static ParseOptions createParseOptions() {

    ParseOptions parseOptions = new ParseOptions();
    parseOptions.setResolve(true);
    parseOptions.setResolveCombinators(true);
    parseOptions.setResolveFully(true);
    parseOptions.setFlatten(false);
    return parseOptions;
  }
}
