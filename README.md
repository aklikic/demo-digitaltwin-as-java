# IoT Digital Twin DEMO - Java - Event Sourced

## Prerequisite
- Java 11<br>
- Apache Maven 3.6 or higher<br>
- Akka Serverless:
    - Register account: [Login, Register, Register via Google](https://console.akkaserverless.com/p/login)
    - `akkasls` tool installed: [Akka Serverless CLI](https://developer.lightbend.com/docs/akka-serverless/akkasls/install-akkasls.html)
    - `akkasls` login
    -  project `demo` created and set for `akkasls`
- Docker 20.10.8 or higher (engine and client)<br>
- Docker Hub account (configured with Docker)<br>
  Access to the `gcr.io/akkaserverless-public` container registry<br>
  cURL<br>
  IDE / editor<br>

## Generate Java project (terminal)

```
mvn archetype:generate \
  -DarchetypeGroupId=com.akkaserverless \
  -DarchetypeArtifactId=akkaserverless-maven-archetype \
  -DarchetypeVersion=0.10.6
```

```
Define value for property 'groupId': com.example
Define value for property 'artifactId': digitaltwin
Define value for property 'version' 1.0-SNAPSHOT: :
Define value for property 'package' com.example: : com.example.digitaltwin
```

## Import in IDE

## Cleanup (IDE)

Delete:<br>
`src/main/proto/com/example/shoppingcart/counter_api.proto`<br>
`src/main/proto/com/example/shoppingcart/domain/counter_domain.proto`

## API descriptor - endpoints (IDE)

Note: For code snippet insertion use command+J (MAC)<br>

1. Create file `digitaltwin_api.proto` in `src/main/proto/com/example/digitaltwin` folder.<br>
2. Edit `src/main/proto/com/example/digitaltwin/digitaltwin_api.proto` in IDE <br>
3. Insert header snippet: `aheader`
4. Insert commands snippet: `acmd`
5. Insert state snippet: `astate`
6. Insert service snippet: `asrv`
7. Add functions to service snippet (place cursor inside brackets `service DigitalTwinService { }`): `afunc`

## API descriptor - domain (IDE)

1. Create file `digitaltwin_domain.proto` in `src/main/proto/com/example/digitaltwin/domain` folder.<br>
2. Edit `src/main/proto/com/example/digitaltwin/domain/digitaltwin_domain.proto` in IDE <br>
3. Insert header snippet: `dheader`
4. Insert events snippet: `devts`
5. Insert state snippet: `dstate`

## API descriptor - codegen annotations (IDE)

1. Edit `src/main/proto/com/example/digitaltwin/digitaltwin_api.proto`
2. Insert codegen annotations (place cursor under `service DigitalTwinService {` ): `acodegen`

## Codegen

1. Code generation (terminal):
```
mvn compile
```
2. Refresh project (IDE)
3. Trigger Maven sync (IDE)


## Business logic implementation (IDE)

1. Edit `src/main/java/com/example/digitaltwin/domain/DigitalTwin` class
2. Delete class body
3. Insert code snippet (delete everything under constructor): `eall`

## Implement unit test
1. Edit `src/test/java/com/example/digitaltwin/domain/DigitalTwinTest` class
2. Delete class body
3. Insert code snippet (delete everything under constructor): `ut`

## Run unit test (terminal)
```
mvn test
```

## Implement integration test (IDE)
1. Edit `src/it/java/com/example/digitaltwin/DigitalTwinIntegrationTest` class
2. Delete everything under the constructor
3. Insert code snippet (delete everything under constructor): `it`

## Run integration test (terminal)
```
mvn -Pit verify
```
## Run locally
??

## Package
1. Edit `pom.xml` and update `my-docker-repo` in `<dockerImage>my-docker-repo/${project.artifactId}</dockerImage>`
2. Execute in terminal:
```
mvn package
```
3. Push docker image to repository:
```
mvn docker:push
```

## Deploy to Akka Serverless
1. Deploy project:
```
akkasls service deploy digitaltwin aklikic/digitaltwin:1.0-SNAPSHOT
```
Note: replace `aklikic` as in Package
2. Expose service:
```
akkasls services expose digitaltwin
```
```
Service 'digitaltwin' was successfully exposed at: shy-frost-2081.us-east1.akkaserverless.app
```
Note: HOSTNAME to use for external access

## Test service in production
1. Create digital twin
```
curl -XPOST -d '{
  "name": "DT1",
  "metric_value_alert_threshold": "10"
}' https://shy-frost-2081.us-east1.akkaserverless.app/digitaltwin/123/create -H "Content-Type: application/json"
```
2. Add OK metric
```
curl -XPOST -d '{
  "metric_value": "5"
}' https://shy-frost-2081.us-east1.akkaserverless.app/digitaltwin/123/add-metric -H "Content-Type: application/json"
```
3. Get cart
```
curl -XGET https://shy-frost-2081.us-east1.akkaserverless.app/digitaltwin/123 -H "Content-Type: application/json"
```
4. Add ALERT metric
```
curl -XPOST -d '{
  "metric_value": "11"
}' https://shy-frost-2081.us-east1.akkaserverless.app/digitaltwin/123/add-metric -H "Content-Type: application/json"
```
5. Get cart
```
curl -XGET https://shy-frost-2081.us-east1.akkaserverless.app/digitaltwin/123 -H "Content-Type: application/json"
```

## Eventing (optional)

1. Create file `digitaltwin_topic.proto` in `src/main/proto/com/example/digitaltwin` folder.<br>
2. Edit `src/main/proto/com/example/digitaltwin/digitaltwin_topic.proto` in IDE <br>
3. Insert header snippet: `theader`
4. Insert events snippet: `tevts`
5. Insert service snippet: `tsrv`

8. Code generation (terminal):
```
mvn compile
```
9. Refresh project (IDE)
10. Trigger Maven sync (IDE)
11. Edit `src/main/java/com/example/digitaltwin/DigitalTwinToTopicAction` class
12. Delete class body
13. Insert code snippet (delete everything under constructor): `tall`


## Copy-paste list
```
mvn archetype:generate \
  -DarchetypeGroupId=com.akkaserverless \
  -DarchetypeArtifactId=akkaserverless-maven-archetype \
  -DarchetypeVersion=0.10.6
```
```
com.example
```
```
digitaltwin
```
```
com.example.digitaltwin
```
```
digitaltwin_api.proto
```
```
digitaltwin_domain.proto
```
```
mvn compile
```
```
mvn test
```
```
mvn -Pit verify
```
```
mvn package
```
```
mvn docker:push
```
```
akkasls service deploy digitaltwin aklikic/digitaltwin:1.0-SNAPSHOT
```
```
curl -XPOST -d '{
  "name": "DT1",
  "metric_value_alert_threshold": "10"
}' https://shy-frost-2081.us-east1.akkaserverless.app/digitaltwin/1234/create -H "Content-Type: application/json"
```
```
curl -XPOST -d '{
  "metric_value": "5"
}' https://shy-frost-2081.us-east1.akkaserverless.app/digitaltwin/1234/add-metric -H "Content-Type: application/json"
```
```
curl -XGET https://shy-frost-2081.us-east1.akkaserverless.app/digitaltwin/1234 -H "Content-Type: application/json"
```
```
curl -XPOST -d '{
  "metric_value": "11"
}' https://shy-frost-2081.us-east1.akkaserverless.app/digitaltwin/1234/add-metric -H "Content-Type: application/json"
```
```
digitaltwin_topic.proto
```