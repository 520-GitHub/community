create table question
(
    id bigint auto_increment primary key ,
    title varchar(50) comment '问题标题',
    description text comment '问题内容',
    gmt_create bigint comment '创建时间',
    gmt_modified bigint comment '更新时间',
    creator bigint comment '发布人id',
    comment_count int default 0 comment '更新时间',
    view_count int default 0 comment '阅读数',
    like_count int default 0 comment '点赞数',
    tag varchar(256) comment '问题标签'
);