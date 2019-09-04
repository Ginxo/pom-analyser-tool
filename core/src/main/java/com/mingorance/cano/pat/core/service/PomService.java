package com.mingorance.cano.pat.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.java.Log;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

@Log
public class PomService {

    private static PomService instance;
    private final MavenXpp3Reader mavenXpp3Reader;

    private PomService() {
        this.mavenXpp3Reader = new MavenXpp3Reader();
    }

    public static PomService getInstance() {
        if (instance == null) {
            instance = new PomService();
        }
        return instance;
    }

    public Model readPom(final String pomFilePath) {
        return this.readPom(new File(pomFilePath));
    }

    public Model readPom(final File pomFile) {
        return readPom(pomFile, null);
    }

    public List<Model> loadModuleModels(final Model model) {
        return model.getModules()
                .stream()
                .map(module -> this.readPom(new File(String.format("%s/%s/pom.xml", model.getPomFile().getParentFile().getAbsolutePath(), module)), String.format("../%s", model.getPomFile().getName())))
                .collect(Collectors.toList());

    }

    public Model loadParent(final Model model) {
        if (model.getParent() == null) {
            log.info(String.format("%s has not parent", model.getId()));
        }
        try {
            return this.readPom(String.format("%s/%s", model.getPomFile().getParentFile().getAbsolutePath(), model.getParent().getRelativePath()));
        } catch (RuntimeException e) {
            log.warning(String.format("No parent exist for %s", model.getId()));
            return null;
        }
    }

    private Model readPom(final File pomFile, final String relativeParentPath) {
        try {
            final Model model = this.mavenXpp3Reader.read(new FileInputStream(pomFile));
            model.setPomFile(pomFile);
            if (model.getParent() != null && relativeParentPath != null) {
                model.getParent().setRelativePath(relativeParentPath);
            }
            return model;
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(String.format("Error reading pom [%s]", pomFile.getAbsolutePath()), e);
        }
    }


}
