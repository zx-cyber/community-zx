create table question
(
  id int auto_increment primary key comment '记录本身的id',
  title varchar (50) comment '问题标题',
  description text comment '问题描述',
  gmt_create BIGINt,
  gmt_modified bigint,
  creator int comment '创建者id',
  comment_count int default 0 comment '评论数',
  view_count int default 0 comment '阅读数',
  like_count int default 0 comment '点赞数',
  tag varchar (256) comment '标签'
);