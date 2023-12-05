package xyz.facta.jtools.programchecker;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

import org.apache.commons.cli.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static Results results = new Results();

    public static void main(String[] args) throws IOException {

        Options options = new Options();

        Option input = new Option("i", "input", true, "path of input source files");
        input.setRequired(true);
        options.addOption(input);

        Option recursiveOption = new Option("r", "recursive", false, "Enable recursive processing");
        options.addOption(recursiveOption);

        Option jsonOption = new Option("json", "json-output", false, "Enable JSON output");
        options.addOption(jsonOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            String inputPath = cmd.getOptionValue("input");
            File inputFile = new File(inputPath);
            boolean isRecursive = cmd.hasOption("r");
            boolean jsonOutput = cmd.hasOption("json");

            if (inputFile.isFile()) {
                processAndAddToList(inputFile);
            } else if (inputFile.isDirectory()) {
                List<File> javaFiles = listJavaFiles(inputFile, isRecursive);
                javaFiles.sort(Comparator.comparing(File::getName));
                for (File javaFile : javaFiles) {
                    processAndAddToList(javaFile);
                }
            } else {
                System.err.println("Invalid input path. Please specify a valid Java file or directory.");
            }
            if (jsonOutput) {
                printListsInJSON();
            } else {
                printListsInPlainText();
            }
        } catch (ParseException e) {
            System.err.println("Error parsing command-line options: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("ProgramChecker", options);
            System.exit(1);
            return;
        }
    }

    private static boolean logFileResult(File file, Level level, ParseResult<CompilationUnit> result) {
        logFilePath(file, level);
        if (result.isSuccessful() && result.getResult().isPresent()) {
            logger.log(level, ANSI_GREEN + "OK" + ANSI_RESET);
            return true;
        } else {
            result.getProblems().forEach(problem -> logger.info(ANSI_RED + problem.getMessage() + ANSI_RESET));
            return false;
        }
    }

    private static void logFilePath(File file, Level level) {
        logger.log(level, file.getAbsolutePath());
    }

    private static void processAndAddToList(File javaFile) throws IOException {
        boolean result = tryParseFile(javaFile);
        if (result) {
            results.getTrueList().add(javaFile.getAbsolutePath());
        } else {
            results.getFalseList().add(javaFile.getAbsolutePath());
        }
    }

    private static boolean tryParseFile(File file) throws IOException {
        JavaParser javaParser = new JavaParser();
        ParseResult<CompilationUnit> result = javaParser.parse(file);
        return logFileResult(file, Level.INFO, result);
    }

    private static List<File> listJavaFiles(File directory, boolean isRecursive) {
        List<File> javaFiles = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                } else if (isRecursive && file.isDirectory()) {
                    javaFiles.addAll(listJavaFiles(file, true));
                }
            }
        }

        return javaFiles;
    }
    private static void printListsInPlainText() {
        System.out.println("True List:");
        for (String path : results.getTrueList()) {
            System.out.println(path);
        }

        System.out.println("False List:");
        for (String path : results.getFalseList()) {
            System.out.println(path);
        }
    }

    private static void printListsInJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            // Create a JSON object with the desired structure
            String resultJson = objectMapper.writeValueAsString(results);
            System.out.println(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Results {
        private final List<String> trueList = new ArrayList<>();
        private final List<String> falseList = new ArrayList<>();

        public List<String> getTrueList() {
            return trueList;
        }

        public List<String> getFalseList() {
            return falseList;
        }
    }

}
