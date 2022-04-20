package com.example.digitaltwin.domain;

import com.example.digitaltwin.DigitalTwinApi;
import com.google.protobuf.Empty;
import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;
import kalix.javasdk.eventsourcedentity.EventSourcedEntity.Effect;
import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
// This is the implementation for the Event Sourced Entity Service described in your com/example/digitaltwin/digitaltwin_api.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class DigitalTwin extends AbstractDigitalTwin {

  @SuppressWarnings("unused")
  private final String entityId;

  public DigitalTwin(EventSourcedEntityContext context) {
    this.entityId = context.entityId();
  }

  @Override
  public DigitalTwinDomain.DigitalTwinDomainState emptyState() {
    return DigitalTwinDomain.DigitalTwinDomainState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> create(DigitalTwinDomain.DigitalTwinDomainState currentState, DigitalTwinApi.CreateCommand createCommand) {
    DigitalTwinDomain.Created event = DigitalTwinDomain.Created.newBuilder()
            .setName(createCommand.getName())
            .setMetricValueAlertThreshold(createCommand.getMetricValueAlertThreshold())
            .build();

    return effects()
            .emitEvent(event)
            .thenReply(newState -> Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> addMetric(DigitalTwinDomain.DigitalTwinDomainState currentState, DigitalTwinApi.AddMetricCommand addMetricCommand) {
    if(currentState.equals(DigitalTwinDomain.DigitalTwinDomainState.getDefaultInstance())){
      return effects().error("Not found");
    }
    if(!currentState.getMetricAlertActive() && addMetricCommand.getMetricValue() >= currentState.getMetricValueAlertThreshold()) {

      DigitalTwinDomain.AlertTriggered event = DigitalTwinDomain.AlertTriggered.newBuilder()
              .setMetricValue(addMetricCommand.getMetricValue())
              .build();
      return effects()
              .emitEvent(event)
              .thenReply(newState -> Empty.getDefaultInstance());

    } else if(currentState.getMetricAlertActive() && addMetricCommand.getMetricValue() < currentState.getMetricValueAlertThreshold()) {

      DigitalTwinDomain.AlertCanceled event = DigitalTwinDomain.AlertCanceled.newBuilder().build();
      return effects()
              .emitEvent(event)
              .thenReply(newState -> Empty.getDefaultInstance());

    } else {
      return effects().reply(Empty.getDefaultInstance());
    }



  }

  @Override
  public Effect<DigitalTwinApi.DigitalTwinState> getDigitalTwinState(DigitalTwinDomain.DigitalTwinDomainState currentState, DigitalTwinApi.GetDigitalTwinStateCommand getDigitalTwinStateCommand) {
    if(currentState.equals(DigitalTwinDomain.DigitalTwinDomainState.getDefaultInstance())){
      return effects().error("Not found");
    }
    return effects().reply(map(currentState));
  }

  private DigitalTwinApi.DigitalTwinState map (DigitalTwinDomain.DigitalTwinDomainState domainState){
    DigitalTwinApi.DigitalTwinState apiState = DigitalTwinApi.DigitalTwinState.newBuilder()
            .setMetricAlertActive(domainState.getMetricAlertActive())
            .build();
    return apiState;
  }

  @Override
  public DigitalTwinDomain.DigitalTwinDomainState created(DigitalTwinDomain.DigitalTwinDomainState currentState, DigitalTwinDomain.Created created) {
    return currentState.toBuilder()
            .setName(created.getName())
            .setMetricValueAlertThreshold(created.getMetricValueAlertThreshold())
            .build();
  }
  @Override
  public DigitalTwinDomain.DigitalTwinDomainState alertTriggered(DigitalTwinDomain.DigitalTwinDomainState currentState, DigitalTwinDomain.AlertTriggered alarmTriggered) {
    return currentState.toBuilder()
            .setMetricAlertActive(true)
            .setMetricValueForAlert(alarmTriggered.getMetricValue())
            .build();
  }
  @Override
  public DigitalTwinDomain.DigitalTwinDomainState alertCanceled(DigitalTwinDomain.DigitalTwinDomainState currentState, DigitalTwinDomain.AlertCanceled alarmCanceled) {
    return currentState.toBuilder()
            .setMetricAlertActive(false)
            .setMetricValueForAlert(0)
            .build();
  }

}
