package org.apache.servicecomb.toolkit.oasv.compliance;

import static java.util.Collections.emptyList;

import java.util.List;

import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public abstract class ComplianceCheckParser {

  private ComplianceCheckParser() {
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
    parseOptions.setResolveCombinators(false);
    parseOptions.setResolveFully(false);
    parseOptions.setFlatten(false);
    return parseOptions;
  }
}
