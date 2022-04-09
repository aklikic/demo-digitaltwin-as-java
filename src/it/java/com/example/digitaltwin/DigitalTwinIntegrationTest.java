package com.example.digitaltwin;

import com.akkaserverless.javasdk.testkit.junit.AkkaServerlessTestKitResource;
import com.example.digitaltwin.domain.DigitalTwinDomain;
import com.google.protobuf.Empty;
import org.junit.ClassRule;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.*;
import static org.junit.Assert.assertEquals;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

// Example of an integration test calling our service via the Akka Serverless proxy
// Run all test classes ending with "IntegrationTest" using `mvn verify -Pit`
public class DigitalTwinIntegrationTest {

  /**
   * The test kit starts both the service container and the Akka Serverless proxy.
   */
  @ClassRule
  public static final AkkaServerlessTestKitResource testKit =
    new AkkaServerlessTestKitResource(Main.createAkkaServerless());

  /**
   * Use the generated gRPC client to call the service through the Akka Serverless proxy.
   */
  private final DigitalTwinService client;

  public DigitalTwinIntegrationTest() {
    client = testKit.getGrpcClient(DigitalTwinService.class);
  }

  @Test
  public void happyPath() throws Exception {
    String dtId =  java.util.UUID.randomUUID().toString();
    String name = "name";
    long metricValueAlertThreshold = 10l;
    long metricValueOk = 5;
    long metricValueAlert = 11;

    DigitalTwinApi.CreateCommand create = DigitalTwinApi.CreateCommand.newBuilder()
            .setDtId(dtId)
            .setMetricValueAlertThreshold(metricValueAlertThreshold)
            .setName(name)
            .build();
    client.create(create).toCompletableFuture().get();

    DigitalTwinApi.GetDigitalTwinStateCommand get = DigitalTwinApi.GetDigitalTwinStateCommand.newBuilder().setDtId(dtId).build();
    DigitalTwinApi.DigitalTwinState state = client.getDigitalTwinState(get).toCompletableFuture().get();
    assertEquals(false, state.getMetricAlertActive());

    DigitalTwinApi.AddMetricCommand addOk = DigitalTwinApi.AddMetricCommand.newBuilder()
            .setDtId(dtId)
            .setMetricValue(metricValueOk)
            .build();
    client.addMetric(addOk);

    state = client.getDigitalTwinState(get).toCompletableFuture().get();
    assertEquals(false, state.getMetricAlertActive());

    DigitalTwinApi.AddMetricCommand addAlert = DigitalTwinApi.AddMetricCommand.newBuilder()
            .setDtId(dtId)
            .setMetricValue(metricValueAlert)
            .build();
    client.addMetric(addAlert);

    state = client.getDigitalTwinState(get).toCompletableFuture().get();
    assertEquals(true, state.getMetricAlertActive());
  }


}
