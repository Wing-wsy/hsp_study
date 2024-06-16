
-- 表 broker_message.broker_message 结构
CREATE TABLE IF NOT EXISTS `broker_message` (
  `message_id` varchar(128) NOT NULL,
  `message` varchar(4000) comment '全量消息',
  `try_count` int(4) DEFAULT 0 comment '最大努力尝试次数',
  `status` varchar(10) DEFAULT '' comment '0 待确认 1 投递成功已确认 2 投递失败',
  `next_retry` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' comment '下一次尝试的时间',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' comment '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' comment '更新时间',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;