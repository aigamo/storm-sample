package net.aigamo_web.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class CauchbaseBolt extends BaseBasicBolt {

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		System.out.println("test");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
