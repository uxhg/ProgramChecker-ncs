package xyz.facta.jtools.programchecker;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

import org.apache.commons.cli.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws IOException {


        Options options = new Options();

        Option input = new Option("i", "input", true, "path of input source files");
        input.setRequired(true);
        options.addOption(input);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("ProgramChecker", options);
            System.exit(1);
            return;
        }


        String inputFilePath = cmd.getOptionValue("input");
        Path filePath = Paths.get(inputFilePath);
        tryParseFile(filePath);
    }

    private static void tryParseFile(Path path) throws IOException {
        JavaParser javaParser = new JavaParser();
        ParseResult<CompilationUnit> result = javaParser.parse(path);

        if (result.isSuccessful() && result.getResult().isPresent()) {
            System.out.println("OK");
        } else {
            result.getProblems().forEach(problem -> System.out.println(problem.getMessage()));
        }
    }
}
