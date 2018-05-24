package net.michir.hellokafkaconnector;

import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by michir on 24/05/2018.
 */
public class FolderSinkTask extends SinkTask {

    private static final String FILE_FORMAT_FROMDATE = "yyyy-MM-dd";

    private Path folder;
    private SimpleDateFormat dateFormat;
    private Path path;
    private Calendar instance;

    @Override
    public String version() {
        return AppInfoParser.getVersion();
    }

    @Override
    public void start(Map<String, String> props) {
        System.out.println(">>>>>> start task");
        // start service
        folder = Paths.get(props.get(FolderSinkConnector.FOLDER_CONFIG));
        dateFormat = new SimpleDateFormat(FILE_FORMAT_FROMDATE);
    }

    @Override
    public void put(Collection<SinkRecord> records) {
        initPath();
        records.stream()
                .forEach(r -> {
                    try {
                        Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)
                                .write((r.value().toString()+"\n").getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void stop() {
        System.out.println("stopping ...");
    }

    private void initPath() {
        if (path == null || instance == null || today()) {
            instance = Calendar.getInstance();
            path = Paths.get(folder.toString(), dateFormat.format(instance.getTime()));
            System.out.println(">>>> write to "+path);
        }
    }

    private boolean today() {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR) == instance.get(Calendar.DAY_OF_YEAR);
    }
}
