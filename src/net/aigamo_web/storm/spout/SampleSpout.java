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
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void nextTuple() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
