package net.aigamo_web.storm.spout;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;

public class SampleSpout extends BaseRichSpout {

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {

	}

	@Override
	public void nextTuple() {

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}
