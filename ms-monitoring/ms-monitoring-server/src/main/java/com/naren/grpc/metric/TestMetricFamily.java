package com.naren.grpc.metric;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import com.naren.grpc.metric.util.EpochTime;

public class TestMetricFamily {

	private static final String NAMESPACE = "naren";
	private static final String SUBSYSTEM = "monitoring";
	private static final String NAME = "request_counter";
	private static final String HELP = "Metrics from test ms request counter.";
	private static final MetaData METADATA = MetaData.newBuilder().setNameSpace(NAMESPACE).setSubSystem(SUBSYSTEM)
			.setName(NAME).setHelp(HELP).setType(Type.COUNTER).build();

	private static Collection<Sample> samples = new ArrayList<>();

	private static Collection<String> labelNames = new ArrayList<>();

	public static MetricFamily getMetricFamily() throws ParseException {
		MetricFamily metricFamily = MetricFamily.newBuilder().setMetaData(METADATA).addAllSample(samples).build();
		return metricFamily;
	}

	static {
		labelNames.add("client");
	}

	public static void addSample(Collection<String> labelValues, double value) throws ParseException {
		samples.add(getSample(labelValues, value));
	}

	private static Sample getSample(Collection<String> labelValues, double value) throws ParseException {
		return Sample.newBuilder().addAllLabelNames(labelNames).addAllLabelValues(labelValues).setValue(value)
				.setTimestampMs(EpochTime.currentTime()).build();
	}
}
