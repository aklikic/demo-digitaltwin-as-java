package com.example.digitaltwin.domain;

import com.example.digitaltwin.DigitalTwinApi;
import com.google.protobuf.Empty;
import kalix.javasdk.eventsourcedentity.EventSourcedEntity;
import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;
import kalix.javasdk.testkit.EventSourcedResult;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class DigitalTwinTest {

  @Test
  public void happyPath() {
    String dtId =  java.util.UUID.randomUUID().toString();
    String name = "name";
    long metricValueAlertThreshold = 10l;
    long metricValueOk = 5;
    long metricValueAlert = 11;
    DigitalTwinTestKit testKit = DigitalTwinTestKit.of(dtId, DigitalTwin::new);

    DigitalTwinApi.CreateCommand create = DigitalTwinApi.CreateCommand.newBuilder()
            .setDtId(dtId)
            .setMetricValueAlertThreshold(metricValueAlertThreshold)
            .setName(name)
            .build();

    EventSourcedResult<Empty> result = testKit.create(create);
    assertEquals(true, result.didEmitEvents());
    DigitalTwinDomain.Created createdEvent = result.getNextEventOfType(DigitalTwinDomain.Created.class);
    assertEquals(name, createdEvent.getName());

    DigitalTwinApi.AddMetricCommand addOk = DigitalTwinApi.AddMetricCommand.newBuilder()
            .setDtId(dtId)
            .setMetricValue(metricValueOk)
            .build();
    result = testKit.addMetric(addOk);
    assertEquals(false, result.didEmitEvents());

    DigitalTwinApi.AddMetricCommand addAlert = DigitalTwinApi.AddMetricCommand.newBuilder()
            .setDtId(dtId)
            .setMetricValue(metricValueAlert)
            .build();
    result = testKit.addMetric(addAlert);
    assertEquals(true, result.didEmitEvents());
    DigitalTwinDomain.AlertTriggered alertTriggeredEvent = result.getNextEventOfType(DigitalTwinDomain.AlertTriggered.class);
    assertEquals(metricValueAlert,alertTriggeredEvent.getMetricValue());

    DigitalTwinApi.GetDigitalTwinStateCommand get = DigitalTwinApi.GetDigitalTwinStateCommand.newBuilder().setDtId(dtId).build();
    EventSourcedResult<DigitalTwinApi.DigitalTwinState> getResult = testKit.getDigitalTwinState(get);
    assertEquals(true, getResult.getReply().getMetricAlertActive());

    result = testKit.addMetric(addOk);
    assertEquals(true, result.didEmitEvents());
    DigitalTwinDomain.AlertCanceled alertCanceledEvent = result.getNextEventOfType(DigitalTwinDomain.AlertCanceled.class);

    get = DigitalTwinApi.GetDigitalTwinStateCommand.newBuilder().setDtId(dtId).build();
    getResult = testKit.getDigitalTwinState(get);
    assertEquals(false, getResult.getReply().getMetricAlertActive());

  }
}
