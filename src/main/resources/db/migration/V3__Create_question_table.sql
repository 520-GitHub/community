CREATE TABLE QUESTION
(
	ID bigint AUTO_INCREMENT PRIMARY KEY ,
	TITLE VARCHAR(50),
	DESCRIPTION TEXT,
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	CREATOR bigint,
	COMMENT_COUNT INT DEFAULT 0,
	VIEW_COUNT INT DEFAULT 0,
	LIKE_COUNT INT DEFAULT 0,
	TAG VARCHAR(256)
);
comment on column question.TITLE is '问题标题';
comment on column question.DESCRIPTION is '问题内容';
comment on column question.GMT_CREATE is '创建时间';
comment on column question.GMT_MODIFIED is '更新时间';
comment on column question.CREATOR is '发布人id';
comment on column question.COMMENT_COUNT is '更新时间';
comment on column question.VIEW_COUNT is '阅读数';
comment on column question.LIKE_COUNT is '点赞数';
comment on column question.DESCRIPTION is '问题内容';
comment on column question.TAG is '问题标签';