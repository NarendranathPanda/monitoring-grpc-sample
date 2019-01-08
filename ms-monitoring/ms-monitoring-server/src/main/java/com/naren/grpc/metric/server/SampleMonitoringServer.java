package com.naren.grpc.metric.server;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import com.naren.grpc.metric.MetricFamily;
import com.naren.grpc.metric.MetricGrpc;
import com.naren.grpc.metric.MetricRequest;
import com.naren.grpc.metric.MetricResponse;
import com.naren.grpc.metric.TestMetricFamily;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class SampleMonitoringServer {
	private static final Logger logger = Logger.getLogger(SampleMonitoringServer.class.getName());

	private Server server;

	private void start() throws IOException {
		/* The port on which the server should run */
		int port = 50051;
		server = ServerBuilder.forPort(port).addService(new MetricImpl()).build().start();
		logger.info("Server started, listening on " + port);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its JVM shutdown
				// hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				SampleMonitoringServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}

	private void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon
	 * threads.
	 */
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	/**
	 * Main launches the server from the command line.
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		final SampleMonitoringServer server = new SampleMonitoringServer();
		server.start();
		server.blockUntilShutdown();
	}

	static class MetricImpl extends MetricGrpc.MetricImplBase {

		@Override
		public void metric(MetricRequest request, StreamObserver<MetricResponse> responseObserver) {
			MetricResponse reply = null;
			try {
				Collection<String> labelValues = new ArrayList<>();
				labelValues.add(request.getName());
				TestMetricFamily.addSample(labelValues, 1);
				MetricFamily metricFamily = TestMetricFamily.getMetricFamily();
				Collection<MetricFamily> metrics = new ArrayList<>();
				metrics.add(metricFamily);
				reply = MetricResponse.newBuilder().addAllMetricFamily(metrics).build();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}

	}

}
