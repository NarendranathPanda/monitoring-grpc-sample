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
  }
  sample {
    labelValuePair {
      labelName: "client"
      labelValue: "Test Client -1546928851415"
    }
    value: 1.0
    timestamp_ms: 1546928852733
  }
}

```
## Prometheus Metric Text :
```
# HELP naren_monitoring_request_counter Metrics from test ms request counter.
# TYPE naren_monitoring_request_counter counter
naren_monitoring_request_counter{client="Test Client -1546928851415",} 1.0 1546928852733

```