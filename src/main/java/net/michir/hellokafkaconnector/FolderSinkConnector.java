package net.michir.hellokafkaconnector;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michir on 24/05/2018.
 */
public class FolderSinkConnector extends SinkConnector {

    public static final String FOLDER_CONFIG = "folder";

    private Map<String, String> props;

    @Override
    public String version() {
        return AppInfoParser.getVersion();
    }

    @Override
    public void start(Map<String, String> props) {
        System.out.println(">>>>>> starting connector");
        this.props = props;
    }

    @Override
    public Class<? extends Task> taskClass() {
        return FolderSinkTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        System.out.println(">>>>>> connector tasks config");
        ArrayList<Map<String, String>> configs = new ArrayList<>();
        for (int i = 0; i < maxTasks; i++) {

            Map<String, String> config = new HashMap<>();
            config.put(FOLDER_CONFIG, props.get(FOLDER_CONFIG));

            configs.add(config);
        }
        return configs;
    }

    @Override
    public ConfigDef config() {
        return new ConfigDef()
                .define(FOLDER_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "folder name");
    }

    @Override
    public void stop() {
    }
}
