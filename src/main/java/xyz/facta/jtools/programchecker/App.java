package xyz.facta.jtools.programchecker;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    public static void main(String[] args) throws IOException {

        Options options = new Options();

        Option input = new Option("i", "input", true, "path of input source files");
        input.setRequired(true);
        options.addOption(input);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            String inputPath = cmd.getOptionValue("input");

            File inputFile = new File(inputPath);

            if (inputFile.isFile()) {
                printFilePath(inputFile);
                tryParseFile(inputFile);
            } else if (inputFile.isDirectory()) {
                List<File> javaFiles = listJavaFiles(inputFile);
                for (File javaFile : javaFiles) {
                    printFilePath(javaFile);
                    tryParseFile(javaFile);
                }
            } else {
                System.err.println("Invalid input path. Please specify a valid Java file or directory.");
            }
        } catch (ParseException e) {
            System.err.println("Error parsing command-line options: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("ProgramChecker", options);
            System.exit(1);
            return;
        }
    }
    private static void printFilePath(File file) {
        System.out.println(file.getAbsolutePath());
    }
    private static void tryParseFile(File file) throws IOException {
        JavaParser javaParser = new JavaParser();
        ParseResult<CompilationUnit> result = javaParser.parse(file);

        if (result.isSuccessful() && result.getResult().isPresent()) {
            System.out.println(ANSI_GREEN + "OK" + ANSI_RESET);
        } else {
            result.getProblems().forEach(problem -> System.out.println(ANSI_RED + problem.getMessage() + ANSI_RESET));
        }
    }


    private static List<File> listJavaFiles(File directory) {
        List<File> javaFiles = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                }
            }
        }

        return javaFiles;
    }
}
