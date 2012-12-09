package net.aigamo_web.storm.topology;

import net.aigamo_web.storm.bolt.PrinterBolt;
import net.aigamo_web.storm.spout.WordCountFromStreamSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.testing.TestAggregatesCounter;
import backtype.storm.testing.TestGlobalCount;
import backtype.storm.testing.TestWordCounter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class GoodjobCountTestTopology {

	public static void main(String args[]) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("1", new WordCountFromStreamSpout(), 3);
		builder.setBolt("2", new TestWordCounter(), 4).fieldsGrouping("1",
				new Fields("word"));
		builder.setBolt("3", new TestGlobalCount()).globalGrouping("1");
		builder.setBolt("4", new TestAggregatesCounter()).globalGrouping("2");
		builder.setBolt("5", new PrinterBolt()).globalGrouping("2");
		StormTopology topology = builder.createTopology();

		Config conf = new Config();
		conf.setDebug(true);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(3);
			
			StormSubmitter.submitTopology(args[0], conf, topology);
		} else {
			conf.setMaxTaskParallelism(3);

			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("word-count", conf, topology);

			Thread.sleep(30000);

			cluster.shutdown();
		}
	}

}
