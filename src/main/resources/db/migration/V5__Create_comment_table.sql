create table comment
(
	id bigint auto_increment primary key ,
	parent_id bigint not null comment '父类ID',
	type int not null comment '父类类型',
	commentator bigint not null comment '评论人id',
	gmt_create bigint not null comment '创建时间',
	gmt_modified bigint not null comment '更新时间',
	like_count INTEGER default 0 comment '点赞数',
	comment_count INTEGER default 0 comment '评论回复数'
);

