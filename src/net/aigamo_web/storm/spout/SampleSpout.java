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
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void nextTuple() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}
