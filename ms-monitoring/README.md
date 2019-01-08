
<table><tr><td>
Prometheus Metric : 
# HELP naren_monitoring_request_counter Metrics from test ms request counter.
# TYPE naren_monitoring_request_counter counter
naren_monitoring_request_counter{client="Test Client -1546918817344",} 1.0 1546918818551
</td></tr>
  <tr><td>
GRPC Server outputs : 
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
  sample {
    labelNames: "client"
    labelValues: "Test Client -1546918887588"
    value: 1.0
    timestamp_ms: 1546918888666
  }
  sample {
    labelNames: "client"
    labelValues: "Test Client -1546918948537"
    value: 1.0
    timestamp_ms: 1546918949609
  }
  sample {
    labelNames: "client"
    labelValues: "Test Client -1546919021241"
    value: 1.0
    timestamp_ms: 1546919022276
  }
}
</td></tr>
  </table>
