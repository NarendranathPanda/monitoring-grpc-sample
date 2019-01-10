# monitoring-grpc-sample

## GRPC Server output : 
```
GRPC Server outputs : 
metricFamily {
  metaData {
    nameSpace: "naren"
    subSystem: "monitoring"
    name: "request_counter"
    help: "Metrics from test ms request counter."
    labelName: "client"
    labelName: "instance"
  }
  sample {
    labelValue: "Test Client -1547113985733"
    labelValue: "localhost"
    value: 1.0
    timestamp_ms: 1547113987093
  }
}


```
## Prometheus Metric Text :
```
# HELP naren_monitoring_request_counter Metrics from test ms request counter.
# TYPE naren_monitoring_request_counter counter
naren_monitoring_request_counter{client="Test Client -1547113985733",instance="localhost",} 1.0 1547113987093

```