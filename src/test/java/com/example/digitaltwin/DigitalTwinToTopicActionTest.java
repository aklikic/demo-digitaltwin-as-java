package com.example.digitaltwin;

import akka.stream.javadsl.Source;
import com.akkaserverless.javasdk.testkit.ActionResult;
import com.example.digitaltwin.DigitalTwinToTopicAction;
import com.example.digitaltwin.DigitalTwinToTopicActionTestKit;
import com.example.digitaltwin.DigitalTwinTopicApi;
import com.example.digitaltwin.domain.DigitalTwinDomain;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class DigitalTwinToTopicActionTest {

  @Test
  public void exampleTest() {
    DigitalTwinToTopicActionTestKit testKit = DigitalTwinToTopicActionTestKit.of(DigitalTwinToTopicAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void alertTriggeredTest() {
    DigitalTwinToTopicActionTestKit testKit = DigitalTwinToTopicActionTestKit.of(DigitalTwinToTopicAction::new);
    // ActionResult<DigitalTwinTopicApi.AlertTriggeredTopicEvent> result = testKit.alertTriggered(DigitalTwinDomain.AlertTriggered.newBuilder()...build());
  }

  @Test
  public void alertCanceledTest() {
    DigitalTwinToTopicActionTestKit testKit = DigitalTwinToTopicActionTestKit.of(DigitalTwinToTopicAction::new);
    // ActionResult<DigitalTwinTopicApi.AlertCanceledTopicEvent> result = testKit.alertCanceled(DigitalTwinDomain.AlertCanceled.newBuilder()...build());
  }

  @Test
  public void ignoreTest() {
    DigitalTwinToTopicActionTestKit testKit = DigitalTwinToTopicActionTestKit.of(DigitalTwinToTopicAction::new);
    // ActionResult<Empty> result = testKit.ignore(Any.newBuilder()...build());
  }

}
