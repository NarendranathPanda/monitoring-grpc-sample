package com.naren.grpc.metric.util;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.naren.monitoring.MetaData;
import com.naren.monitoring.MetricFamily;
import com.naren.monitoring.MetricResponse;
import com.naren.monitoring.MetricType;
import com.naren.monitoring.Sample;

public class TextFormat {
	/**
	 * Content-type for text version 0.0.4.
	 */
	public final static String CONTENT_TYPE_004 = "text/plain; version=0.0.4; charset=utf-8";

	/**
	 * Write out the text version 0.0.4 of the given MetricFamilySamples.
	 */
	public static void write004(Writer writer, MetricResponse response) throws IOException {
		for (MetricFamily metric : response.getMetricFamilyList()) {
			writer.write("# HELP ");
			MetaData metaData = metric.getMetaData();
			String fullName = getFullName(metaData.getNameSpace(), metaData.getSubSystem(), metaData.getName());
			writer.write(fullName);
			writer.write(' ');
			writeEscapedHelp(writer, metaData.getHelp());
			writer.write('\n');
			writer.write("# TYPE ");
			writer.write(fullName);
			writer.write(' ');
			writer.write(typeString(metaData.getType()));
			writer.write('\n');
			writeSamples(writer, fullName, metric.getSampleList(), metaData.getLabelNameList());
		}

	}

	private static void writeSamples(Writer writer, String namesapce, List<Sample> samples, List<String> labelNames)
			throws IOException {
		for (Sample sample : samples) {
			writeSample(writer, namesapce, sample, labelNames);
		}
	}

	private static void writeSample(Writer writer, String name, Sample sample, List<String> labelNames)
			throws IOException {
		writer.write(name);
		List<String> labelValueList = sample.getLabelValueList();
		if (labelNames.size() > 0) {
			if (labelValueList == null || labelNames.size() != labelValueList.size()) {
				throw new InvalidLableValuePairException();
			}
			writer.write('{');
			for (int i = 0; i < labelNames.size(); ++i) {
				writer.write(labelNames.get(i));
				writer.write("=\"");
				writeEscapedLabelValue(writer, labelValueList.get(i));
				writer.write("\",");
			}
			writer.write('}');
		}
		writer.write(' ');
		writer.write(doubleToGoString(sample.getValue()));
		writer.write(' ');
		writer.write(longToGoString(sample.getTimestampMs()));
		writer.write('\n');
	}

	private static void writeEscapedHelp(Writer writer, String s) throws IOException {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\\':
				writer.append("\\\\");
				break;
			case '\n':
				writer.append("\\n");
				break;
			default:
				writer.append(c);
			}
		}
	}

	private static void writeEscapedLabelValue(Writer writer, String s) throws IOException {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\\':
				writer.append("\\\\");
				break;
			case '\"':
				writer.append("\\\"");
				break;
			case '\n':
				writer.append("\\n");
				break;
			default:
				writer.append(c);
			}
		}
	}

	private static String typeString(MetricType t) {
		switch (t) {
		case GAUGE:
			return "gauge";
		case COUNTER:
			return "counter";
		case SUMMARY:
			return "summary";
		case HISTOGRAM:
			return "histogram";
		default:
			return "untyped";
		}
	}

	public static String doubleToGoString(double d) {
		if (d == Double.POSITIVE_INFINITY) {
			return "+Inf";
		}
		if (d == Double.NEGATIVE_INFINITY) {
			return "-Inf";
		}
		if (Double.isNaN(d)) {
			return "NaN";
		}
		return Double.toString(d);
	}

	public static String longToGoString(long l) {
		return Long.toString(l);
	}

	public static String getFullName(String namespace, String subSystem, String name) {
		StringBuffer sb = new StringBuffer(namespace);
		if (isNotEmpty(subSystem)) {
			sb.append("_" + subSystem);
		}
		if (isNotEmpty(name)) {
			sb.append("_" + name);
		}
		return sb.toString();
	}

	public static boolean isNotEmpty(String value) {
		return value != null && !"".equals(value);
	}

}
