package net.aigamo_web.strom.test;

import java.util.Map;

import junit.framework.TestCase;
import net.aigamo_web.storm.bolt.PrinterBolt;
import net.aigamo_web.storm.spout.WordCountFromStreamSpout;
import backtype.storm.Config;
import backtype.storm.ILocalCluster;
import backtype.storm.Testing;
import backtype.storm.generated.StormTopology;
import backtype.storm.testing.CompleteTopologyParam;
import backtype.storm.testing.MkClusterParam;
import backtype.storm.testing.TestAggregatesCounter;
import backtype.storm.testing.TestGlobalCount;
import backtype.storm.testing.TestJob;
import backtype.storm.testing.TestWordCounter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Time;

public class CoregoodJobTest extends TestCase {

	public void testWithSimulatedTime() {
		assertFalse(Time.isSimulating());
		/**
		 * <code>Testing.withSimulatedTime</code> create a context in which the
		 * time is simulated.set * you can use <code>Time.isSimulating</code> to
		 * check whether we're simulating the time. use
		 * <code>Time.advanceTime</code> to advance the simulated time.
		 */
		Testing.withSimulatedTime(new Runnable() {
			@Override
			public void run() {
				assertTrue(Time.isSimulating());
			}
		});
		assertFalse(Time.isSimulating());
	}

	public void testStreamApi() throws Exception {
		MkClusterParam mkClusterParam = new MkClusterParam();
		mkClusterParam.setSupervisors(1);
		Config daemonConf = new Config();
		daemonConf.put(Config.STORM_LOCAL_MODE_ZMQ, false);
		daemonConf.put(Config.STORM_LOCAL_DIR, "C:\\storm");
		daemonConf.put(Config.TOPOLOGY_FALL_BACK_ON_JAVA_SERIALIZATION, false);
		daemonConf.setDebug(true);
		mkClusterParam.setDaemonConf(daemonConf);

		/**
		 * This is a combination of <code>Testing.withLocalCluster</code> and
		 * <code>Testing.withSimulatedTime</code>.
		 */
		Testing.withSimulatedTimeLocalCluster(mkClusterParam, new TestJob() {
			@Override
			public void run(ILocalCluster cluster) {
				// build the test topology
				TopologyBuilder builder = new TopologyBuilder();

				builder.setSpout("1", new WordCountFromStreamSpout(), 3);
				builder.setBolt("2", new TestWordCounter(), 4).fieldsGrouping(
						"1", new Fields("word"));
				builder.setBolt("3", new TestGlobalCount()).globalGrouping("1");
				builder.setBolt("4", new TestAggregatesCounter())
						.globalGrouping("2");
				builder.setBolt("5", new PrinterBolt()).globalGrouping("2");
				StormTopology topology = builder.createTopology();

				// complete the topology

				// prepare the mock data
				// MockedSources mockedSources = new MockedSources();
				// mockedSources.addMockData("1", new Values("nathan"),
				// new Values("bob"), new Values("joey"), new Values(
				// "nathan"));
				// mockedSources.addMockData("1",
				// CoregoodjobMockData.getMockData());

				// prepare the config
				Config conf = new Config();
				conf.setNumWorkers(2);

				CompleteTopologyParam completeTopologyParam = new CompleteTopologyParam();
				// completeTopologyParam.setMockedSources(mockedSources);
				completeTopologyParam.setStormConf(conf);
				/**
				 * TODO
				 * 
				 */

				Map result = Testing.completeTopology(cluster, topology,
						completeTopologyParam);

				System.out.println(Testing.readTuples(result, "2"));
				/**
				 * // check whether the result is right
				 * assertTrue(Testing.multiseteq(new Values(new
				 * Values("nathan"), new Values("bob"), new Values("joey"), new
				 * Values( "nathan")), Testing.readTuples(result, "1")));
				 */
			}

		});

	}
}
