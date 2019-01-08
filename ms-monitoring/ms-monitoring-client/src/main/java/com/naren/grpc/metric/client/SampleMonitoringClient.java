package com.naren.grpc.metric.client;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.naren.grpc.metric.util.TextFormat;
import com.naren.monitoring.MetricRequest;
import com.naren.monitoring.MetricResponse;
import com.naren.monitoring.MetricServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

/**
 * A simple client that requests a greeting from the
 * {@link SampleMonitoringServer}.
 */
public class SampleMonitoringClient {
	private static final Logger logger = Logger.getLogger(SampleMonitoringClient.class.getName());

	private final ManagedChannel channel;
	private final MetricServiceGrpc.MetricServiceBlockingStub blockingStub;

	/** Construct client connecting to MetricWorld server at {@code host:port}. */
	public SampleMonitoringClient(String host, int port) {
		this(ManagedChannelBuilder.forAddress(host, port)
				// Channels are secure by default (via SSL/TLS). For the example we disable TLS
				// to avoid
				// needing certificates.
				.usePlaintext().build());
	}

	/**
	 * Construct client for accessing MetricWorld server using the existing channel.
	 */
	SampleMonitoringClient(ManagedChannel channel) {
		this.channel = channel;
		blockingStub = MetricServiceGrpc.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	/** Say Metric to server. */
	public void metric(String name) {
		logger.info("Will try to greet " + name + " ...");
		MetricRequest request = MetricRequest.newBuilder().setName(name).build();
		MetricResponse collector;
		try {
			collector = blockingStub.metric(request);
		} catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
			return;
		}
		Writer writer = new StringWriter();
		try {
			TextFormat.write004(writer, collector);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Prometheus Metric : \n" + writer.toString() + "\n");
		logger.info("\nGRPC Server outputs : \n" + collector.toString());
	}

	/**
	 * Greet server. If provided, the first element of {@code args} is the name to
	 * use in the greeting.
	 */
	public static void main(String[] args) throws Exception {
		SampleMonitoringClient client = new SampleMonitoringClient("localhost", 50051);
		try {
			/* Access a service running on the local machine on port 50051 */
			String user = "Test Client ";
			if (args.length > 0) {
				user = args[0]; /* Use the arg as the name to greet if provided */
			}
			client.metric(user + "-" + System.currentTimeMillis());
		} finally {
			client.shutdown();
		}
	}
}
