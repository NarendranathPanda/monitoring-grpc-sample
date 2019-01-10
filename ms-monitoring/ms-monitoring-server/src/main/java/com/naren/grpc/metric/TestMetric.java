package com.naren.grpc.metric;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.naren.grpc.metric.util.EpochTime;
import com.naren.monitoring.MetaData;
import com.naren.monitoring.MetricFamily;
import com.naren.monitoring.MetricType;
import com.naren.monitoring.Sample;

public class TestMetric {

	private static final String NAMESPACE = "naren";
	private static final String SUBSYSTEM = "monitoring";
	private static final String NAME = "request_counter";
	private static final String HELP = "Metrics from test ms request counter.";
	public static final String[] LABELS = { "client","instance" };
	private static final MetaData METADATA = MetaData.newBuilder().setNameSpace(NAMESPACE).setSubSystem(SUBSYSTEM)
			.setName(NAME).setHelp(HELP).setType(MetricType.COUNTER).addAllLabelName(Arrays.asList(LABELS)).build();

	private static Collection<Sample> samples = new ArrayList<>();

	public static MetricFamily getMetricFamily() throws ParseException {
		MetricFamily metricFamily = MetricFamily.newBuilder().setMetaData(METADATA).addAllSample(samples).build();
		return metricFamily;
	}

	public static void addSample(Collection<String> labelValues, double value) throws ParseException {
		samples.add(getSample(labelValues, value));
	}

	private static Sample getSample(Collection<String> labelValues, double value) throws ParseException {
		return Sample.newBuilder().addAllLabelValue(labelValues).setValue(value).setTimestampMs(EpochTime.currentTime())
				.build();
	}
}
