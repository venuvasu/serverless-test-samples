package com.amazon.aws.sample.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.StartExecutionRequest;
import software.amazon.awssdk.services.sfn.model.StartExecutionResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class SqsLambdaStepFunctionHandler implements RequestHandler<SQSEvent, StartExecutionResponse> {
  private static final SfnClient client = SfnClient.create();
  private static final Logger logger = Logger.getLogger(SqsLambdaStepFunctionHandler.class.getName());

  /**
   * @param
   * @return: Reads properties from config.
   */
  public String getProperties() {
    Properties prop = null;
    try (InputStream input = SqsLambdaStepFunctionHandler.class.getClassLoader().getResourceAsStream("config.properties")) {
      prop = new Properties();
      if (input == null) {
        logger.severe("Sorry, unable to find config.properties");
        return "";
      }
      //load a properties file from class path, inside static method
      prop.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return prop.getProperty("cloud.aws.statemachine.arn");
  }

  /**
   * @param event, context
   * @return: Starts step function
   */
  public StartExecutionResponse handleRequest(SQSEvent event, Context context) {
    StartExecutionRequest request = null;
    for (SQSEvent.SQSMessage msg : event.getRecords()) {
      if (msg != null) {
        System.out.println(new String(msg.getBody()));
        request = StartExecutionRequest.builder()
          .stateMachineArn(getProperties())
          .input(msg.getBody())
          .build();
      }
    }
    StartExecutionResponse result = client.startExecution(request);
    return result;
  }
}
