# jinja-maven-plugin

This project combines [jinjava](https://github.com/HubSpot/jinjava) and [snakeyaml](https://bitbucket.org/asomov/snakeyaml) to compile jinja template files with variable defintions in a yaml file from a maven project.

## Usage

Notice: Original plugin de.wintercloud:jinja-maven-plugin

In your project file do:

```
...
  <build>
    <plugins>
      <plugin>
        <groupId>ch.ebu</groupId>
        <artifactId>jinja-maven-plugin</artifactId>
        <version>1.0.1-SNAPSHOT</version>
        <configuration>
          <outputFile>path-to-outout.txt</outputFile>
          <templateFile>path-to-template.jinja</templateFile>
          <varFile>path-to-variables.yaml</varFile>
        </configuration>
      </plugin>
    </plugins>
  </build>
...
```

and then execute the goal using

```
mvn de.wintercloud:jinja-maven-plugin:1.0:renderjinja
```

or make it part of your compile phase:

```
  <build>
    <plugins>
      <plugin>
        <groupId>ch.ebu</groupId>
        <artifactId>jinja-maven-plugin</artifactId>
        <version>1.0.1-SNAPSHOT</version>
        <executions>
          <execution>
            <phase>compile</phase>
            <goals>
              <goal>renderjinja</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <outputFile>path-to-out.txt</outputFile>
          <templateFile>path-to-template.jinja</templateFile>
          <varFile>path-to-variables.yaml</varFile>
        </configuration>
      </plugin>
    </plugins>
  </build>
```

## deploy

GPG
mvn clean deploy -Darguments=-Dgpg.passphrase="SEE KEEPASS" -Dpgp.skip-true

    <!-- GPG plugin -->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-gpg-plugin</artifactId>
        <version>${plugin-gpg.version}</version>
        <executions>
            <execution>
                <id>sign-artifacts</id>
                <phase>verify</phase>
                <goals>
                    <goal>sign</goal>
                </goals>
            </execution>
        </executions>
    </plugin>

Nexus

    <!-- sonatype repo -->
    <plugin>
        <groupId>org.sonatype.plugins</groupId>
        <artifactId>nexus-staging-maven-plugin</artifactId>
        <version>${plugin-nexus-staging.version}</version>
        <extensions>true</extensions>
        <configuration>
            <serverId>ossrh</serverId>
            <nexusUrl>https://oss.sonatype.org/</nexusUrl>
            <!-- Set this to true and the release will automatically proceed and sync to Central Repository will follow  -->
            <autoReleaseAfterClose>false</autoReleaseAfterClose>
        </configuration>
    </plugin>