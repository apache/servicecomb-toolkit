package docgen;

import io.swagger.models.Swagger;
import io.swagger.parser.Swagger20Parser;
import org.apache.commons.io.FileUtils;
import org.apache.servicecomb.toolkit.docgen.DocGeneratorManager;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DocGeneratorTest {


    @Test
    public void contractToAsciidoc() throws IOException {

        Swagger20Parser swagger20Parser = new Swagger20Parser();
        InputStream in = DocGeneratorTest.class.getClassLoader().getResourceAsStream("HelloEndPoint.yaml");

        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1024];
        int len = -1;
        while ((len = in.read(bytes)) != -1){
            sb.append(new String(bytes,0,len));
        }

        Swagger swagger = swagger20Parser.parse(sb.toString());
        Path tempDir = Files.createTempDirectory(null);
        Path outputPath = Paths.get(tempDir.toFile().getAbsolutePath()
                + File.separator + "asciidoc.html");

        DocGeneratorManager.generate(swagger,outputPath.toFile().getCanonicalPath() , "asciidoc-html");

        Assert.assertTrue(Files.exists(outputPath));
        FileUtils.deleteDirectory(tempDir.toFile());
    }

    @Test
    public void contractTransferToSwaggerUI() throws IOException {

        Swagger20Parser swagger20Parser = new Swagger20Parser();

        InputStream in = DocGeneratorTest.class.getClassLoader().getResourceAsStream("HelloEndPoint.yaml");

        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1024];
        int len = -1;
        while ((len = in.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len));
        }

        Swagger swagger = swagger20Parser.parse(sb.toString());

        Path tempDir = Files.createTempDirectory(null);
        Path outputPath = Paths.get(tempDir.toFile().getAbsolutePath()
                + File.separator + "swagger-ui.html");
        DocGeneratorManager.generate(swagger,outputPath.toFile().getCanonicalPath() , "swagger-ui");

        Assert.assertTrue(Files.exists(outputPath));
        FileUtils.deleteDirectory(tempDir.toFile());
    }

    @Test
    public void contractTransferToOther() throws IOException {

        Swagger20Parser swagger20Parser = new Swagger20Parser();

        InputStream in = DocGeneratorTest.class.getClassLoader().getResourceAsStream("HelloEndPoint.yaml");

        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1024];
        int len = -1;
        while ((len = in.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len));
        }

        Swagger swagger = swagger20Parser.parse(sb.toString());

        Path tempDir = Files.createTempDirectory(null);
        Path outputPath = Paths.get(tempDir.toFile().getAbsolutePath()
                + File.separator + "swagger-ui.html");

        DocGeneratorManager.generate(swagger, outputPath.toFile().getCanonicalPath(), "other");

        Assert.assertFalse(Files.exists(outputPath));

        FileUtils.deleteDirectory(tempDir.toFile());
    }

}
