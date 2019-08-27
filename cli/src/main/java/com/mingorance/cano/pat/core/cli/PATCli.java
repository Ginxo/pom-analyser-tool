package com.mingorance.cano.pat.core.cli;

import com.mingorance.cano.pat.core.tool.RepeatedDefinitionDependencyTool;
import com.mingorance.cano.pat.core.tool.RepeatedDependencyTool;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.annotation.Arg;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

public class PATCli {

    private static class Option {

        @Arg(dest = "file")
        public String pomFile;

        @Arg(dest = "d")
        public Boolean d;

        @Arg(dest = "r")
        public Boolean r;
    }

    public static void main(String[] args) {
        treatAnalyseProgram(args);
    }

    private static ArgumentParser generateAnalyseArgumentParser() {
        ArgumentParser parser = ArgumentParsers.newFor("analyse").build()
                .description("Analyse whether a dependency has been declared and defined in the project structure");
        parser.addArgument("--file")
                .metavar("pom.xml")
                .type(String.class)
                .nargs("?")
                .setDefault("pom.xml")
                .required(false)
                .help("the pom.xml file path");
        parser.addArgument("-d").action(Arguments.storeTrue()).help("Analyse the project dependencies");
        parser.addArgument("-r").action(Arguments.storeTrue()).help("Analyse the dependency repetitions");

        return parser;
    }

    private static void treatAnalyseProgram(String[] args) {
        final ArgumentParser analyseParser = generateAnalyseArgumentParser();
        try {
            Boolean run = false;
            final Option opt = new Option();
            analyseParser.parseArgs(args, opt);
            if (opt.d) {
                run = true;
                System.out.println(DependencyMessageTreaterUtil.treatPatDependencyList(RepeatedDefinitionDependencyTool.getInstance().run(opt.pomFile)));
            }

            if (opt.r) {
                run = true;
                System.out.println(RepeatedDependencyTool.getInstance().run(opt.pomFile));
            }

            if (!run) {
                analyseParser.printHelp();
            }
        } catch (ArgumentParserException e) {
            analyseParser.handleError(e);
        }
    }
}
