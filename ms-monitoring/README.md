

# Prometheus Metric :
  ```
# HELP naren_monitoring_request_counter Metrics from test ms request counter.
# TYPE naren_monitoring_request_counter counter
naren_monitoring_request_counter{client="Test Client -1546918817344",} 1.0 1546918818551
```

# GRPC Server outputs :
  ```
metricFamily {
  metaData {
    nameSpace: "naren"
    subSystem: "monitoring"
    name: "request_counter"
    help: "Metrics from test ms request counter."
  }
  sample {
    labelNames: "client"
    labelValues: "Test Client -1546918817344"
    value: 1.0
    timestamp_ms: 1546918818551
  }
}
```

