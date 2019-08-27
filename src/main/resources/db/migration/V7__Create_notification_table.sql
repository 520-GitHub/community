create table notification
(
	id bigint auto_increment primary key ,
	notifier bigint not null comment '发送通知的用户',
	receiver bigint not null comment '接收消息用户',
	outerid bigint not null comment '外键id',
	type int not null comment '类型',
	gmt_create bigint not null comment '时间',
	status int default 0 not null comment '未读数'
);

