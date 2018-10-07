package ch.ebu.maven.plugin;

// Notice: original plugin de.wintercloud:jinja-maven-plugin

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.hubspot.jinjava.Jinjava;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Goal which renders jinja files
 */
@Mojo(name = "render")
public class CompileJinjaMojo extends AbstractMojo {
    /**
     * Location of the files.
     */

    // variables
    @Parameter(property = "variablesfile", required = true)
    private File variablesfile;

    // spring template/output
    @Parameter(property = "springtemplatefile", required = true)
    private File springtemplatefile;

    @Parameter(property = "springoutputfile", required = true)
    private File springoutputfile;

    // k8 template/output
    @Parameter(property = "k8templatefile", required = true)
    private File k8templatefile;

    @Parameter(property = "k8outputfile", required = true)
    private File k8outputfile;

    public void execute() throws MojoExecutionException {
        // load parameters
        Map<String, Object> context = loadParameter(variablesfile);
        // render spring
        render(springtemplatefile, springoutputfile, context);
        // render k8
        render(k8templatefile, k8outputfile, context);
    }

    /**
     * Common Parameters loader
     *
     * @return
     * @throws MojoExecutionException
     */
    private Map<String, Object> loadParameter(File variableFile) throws MojoExecutionException {
        // Load the parameters
        try {
            return new Yaml().load(FileUtils.readFileToString(variableFile, (Charset) null));
        } catch (IOException e) {
            // Print error and exit with -1
            throw new MojoExecutionException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * Templace Rendering
     *
     * @param templateFile
     * @param outputFile
     * @throws MojoExecutionException
     */
    private void render(File templateFile, File outputFile, Map<String, Object> context) throws MojoExecutionException {
        try {
            // Load template
            Jinjava jinjava = new Jinjava();
            String template = FileUtils.readFileToString(templateFile, (Charset) null);

            // Render and save
            String rendered = jinjava.render(template, context);
            FileUtils.writeStringToFile(outputFile, rendered, (Charset) null);

        } catch (IOException e) {
            // Print error and exit with -1
            throw new MojoExecutionException(e.getLocalizedMessage(), e);
        }
    }
}
