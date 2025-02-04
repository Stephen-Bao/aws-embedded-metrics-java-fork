package agent;

import software.amazon.cloudwatchlogs.emf.config.EnvironmentConfigurationProvider;
import software.amazon.cloudwatchlogs.emf.environment.DefaultEnvironment;
import software.amazon.cloudwatchlogs.emf.environment.Environment;
import software.amazon.cloudwatchlogs.emf.logger.MetricsLogger;
import software.amazon.cloudwatchlogs.emf.model.DimensionSet;
import software.amazon.cloudwatchlogs.emf.model.Unit;

import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) {
        DefaultEnvironment environment = new DefaultEnvironment(EnvironmentConfigurationProvider.getConfig());
        emitMetric(environment);
        emitMetric(environment);
        emitMetric(environment);
        environment.getSink().shutdown().orTimeout(360_000L, TimeUnit.MILLISECONDS);
    }

    private static void emitMetric(Environment environment) {
        MetricsLogger logger = new MetricsLogger(environment);
        logger.setDimensions(DimensionSet.of("Operation", "Agent"));
        logger.putMetric("ExampleMetric", 100, Unit.MILLISECONDS);
        logger.putProperty("RequestId", "422b1569-16f6-4a03-b8f0-fe3fd9b100f8");
        logger.flush();
    }
}
