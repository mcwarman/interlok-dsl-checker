package com.adaptris.utils;

import com.adaptris.core.CoreException;
import com.adaptris.core.config.PreProcessingXStreamMarshaller;
import com.adaptris.util.KeyValuePair;
import com.adaptris.util.KeyValuePairSet;
import org.apache.commons.cli.*;

import java.io.File;

/**
 * @author mwarman
 */
public class DSLChecker {

  private Options options;

  private String adapterPath;
  private String preproccesors;
  private String[] variablesPaths = new String[]{};

  private static final String HELP_ARG = "help";
  private static final String PREPROCESSORS_ARG = "preprocessors";
  private static final String ADAPTER_ARG = "adapter";
  private static final String VARIABLES_ARG = "variables";

  private static final String DEFAULT_PROJECT = "project";

  DSLChecker(){
    options = new Options();
    options.addOption("h",HELP_ARG, false, "Displays this.." );
    options.addRequiredOption("a", ADAPTER_ARG, true, "(required) The adapter xml");
    options.addOption("p", PREPROCESSORS_ARG, true, "The preprocessors to execute");
    Option option = new Option("v", VARIABLES_ARG, true, "The variables (can be added multiple times)");
    option.setArgs(Option.UNLIMITED_VALUES);
    option.setRequired(false);
    options.addOption(option);
  }

  public static void main(String[] args) throws Exception {
    DSLChecker DSLChecker = new DSLChecker();
    DSLChecker.check(args);
  }

  void check(String[] args) throws CoreException {
    arguments(args);
    check(adapterPath, preproccesors, variablesPaths);
  }

  void arguments(String[] args){
    CommandLineParser parser = new DefaultParser();
    try {
      CommandLine line = parser.parse(options, args);
      if(line.hasOption(HELP_ARG)){
        usage();
      }
      adapterPath = line.getOptionValue(ADAPTER_ARG);
      if (line.hasOption(VARIABLES_ARG)) {
        variablesPaths = line.getOptionValues(VARIABLES_ARG);
      }
      if (line.hasOption(PREPROCESSORS_ARG)) {
        preproccesors = line.getOptionValue(PREPROCESSORS_ARG);
      }
    } catch (ParseException e) {
      System.err.println("Parsing failed.  Reason: " + e.getMessage());
      usage();
    }
  }

  void check(String filePath, String preprocessors,  String... variablesPaths) throws CoreException {
    PreProcessingXStreamMarshaller marshaller = new PreProcessingXStreamMarshaller();
    marshaller.setPreProcessors(preprocessors);
    KeyValuePairSet valuePairs = new KeyValuePairSet();
    valuePairs.addKeyValuePair(new KeyValuePair("variable-substitution.impl", "STRICT_WITH_LOGGING"));
    int i = 0;
    for (String variable : variablesPaths){
      valuePairs.addKeyValuePair(new KeyValuePair("variable-substitution.properties.url." + ++i , variable));
    }
    marshaller.setPreProcessorConfig(valuePairs);
    System.out.println(filePath);
    marshaller.unmarshal(new File(filePath));
    System.out.println("Config check only; terminating");
  }



  void usage(){
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp( "interlok-dsl-checker", options );
    System.exit(1);
  }


}
