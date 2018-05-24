# Description
A kafka connect *sink* example that reads data from a topic and 
write each received *String* message into a new line under folder, a file per day.

The output file has the format _year{4}-month{2}-dayOfMonth{2}_.

The connector configuration uses a _StringConverter_ as the
  sink messages (key and values) are expected to be of _String_
  format generated using _kafka-console-producer_.
  

## Configuration example
 
 ```
 name=folder-sink
 connector.class=net.michir.hellokafkaconnector.FolderSinkConnector
 tasks.max=1
 folder=/tmp/
 topics=connect-test
 key.converter=org.apache.kafka.connect.storage.StringConverter
 value.converter=org.apache.kafka.connect.storage.StringConverter
 #value.converter=org.apache.kafka.connect.converters.ByteArrayConverter
 schemas.enable=true
 ```
 
## Compile and install
1. Run the maven command to compile/package
```sh
    mvn clean install
```

It will generate a regular jar file.
 
## Testing (in standalone mode)
_Get into kafka installation folder._

1. Copy the generated artifact into the kafka plugins directory

2. Copy the above configuration into _plugins/folder-sink-connector.properties_

3. Declare the following env variable before starting
```sh
    CLASSPATH=<kafka_home>/plugins/*
```

2. Start the connector 
```
    ./bin/connect-standalone.sh config/connect-standalone.properties plugins/folder-sink-connector.properties
```

3. Send message to topic using the kafka producer utility

```sh
    ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topicnnect-test
```

4. Tail the file
```sh
    tail -f /tmp/2018-*
```
