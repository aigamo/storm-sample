package net.aigamo_web.storm.topology;

import net.aigamo_web.storm.bolt.BlogEntryNoBolt;
import net.aigamo_web.storm.bolt.OperationBolt;
import net.aigamo_web.storm.bolt.PrinterBolt;
import net.aigamo_web.storm.spout.GoodjobStreamSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class GoodjobTopology {

	public static void main(String args[]) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("1", new GoodjobStreamSpout(), 3);
		builder.setBolt("2", new BlogEntryNoBolt()).fieldsGrouping("1",
				new Fields("goodjobStream"));

		builder.setBolt("3", new OperationBolt(), 4).fieldsGrouping("2",
				new Fields("blogEntryNo", "AnalyticObject"));

		builder.setBolt("4", new PrinterBolt(), 1).globalGrouping("3");

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

			Thread.sleep(60000);

			cluster.shutdown();
		}
	}

}
