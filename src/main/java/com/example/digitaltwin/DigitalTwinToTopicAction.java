package com.example.digitaltwin;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.example.digitaltwin.domain.DigitalTwinDomain;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import static com.google.protobuf.Empty.getDefaultInstance;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
// This is the implementation for the Action Service described in your com/example/digitaltwin/digitaltwin_topic.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class DigitalTwinToTopicAction extends AbstractDigitalTwinToTopicAction {

  public DigitalTwinToTopicAction(ActionCreationContext creationContext) {}

  @Override
  public Effect<DigitalTwinTopicApi.AlertTriggeredTopicEvent> alertTriggered(DigitalTwinDomain.AlertTriggered alertTriggered) {
    DigitalTwinTopicApi.AlertTriggeredTopicEvent topicEvent = DigitalTwinTopicApi.AlertTriggeredTopicEvent.newBuilder()
            .setDtId(actionContext().eventSubject().get())
            .setMetricValue(alertTriggered.getMetricValue())
            .build();
    return effects().reply(topicEvent);
  }
  @Override
  public Effect<DigitalTwinTopicApi.AlertCanceledTopicEvent> alertCanceled(DigitalTwinDomain.AlertCanceled alertCanceled) {
    DigitalTwinTopicApi.AlertCanceledTopicEvent topicEvent = DigitalTwinTopicApi.AlertCanceledTopicEvent.newBuilder()
            .setDtId(actionContext().eventSubject().get())
            .build();
    return effects().reply(topicEvent);
  }
  @Override
  public Effect<Empty> ignore(Any any) {
    return effects().reply(getDefaultInstance());
  }
}
