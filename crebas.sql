/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/10/21 10:21:02                          */
/*==============================================================*/


drop table if exists department;

drop table if exists role;

drop table if exists student;

drop table if exists subject;

drop table if exists subject_to_tag;

drop table if exists tag;

drop table if exists teacher;

drop table if exists user;

/*==============================================================*/
/* Table: department                                            */
/*==============================================================*/
create table department
(
   id                   bigint not null comment 'id',
   name                 varchar(32) comment '系部名称',
   primary key (id)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   id                   bigint not null comment 'id',
   name                 varchar(32) comment '角色名称',
   primary key (id)
);

/*==============================================================*/
/* Table: student                                               */
/*==============================================================*/
create table student
(
   user_id              bigint comment '用户id',
   class_name           varchar(32) comment '班级',
   name                 varchar(16) comment '姓名',
   department_id        bigint comment '系部id'
);

/*==============================================================*/
/* Table: subject                                               */
/*==============================================================*/
create table subject
(
   id                   bigint not null comment 'id',
   title                varchar(255) comment '选题标题',
   teacherId            bigint comment '创建教师id',
   content              varbinary(255) comment '选题内容',
   source               varchar(32) comment '选题来源',
   difficult            int comment '选题难度(0: 简单, 1: 中等， 2: 困难)',
   create_time          datetime comment '创建时间',
   primary key (id)
);

/*==============================================================*/
/* Table: subject_to_tag                                        */
/*==============================================================*/
create table subject_to_tag
(
   subject_id           bigint comment '课题id',
   tag_id               bigint comment '标签id'
);

/*==============================================================*/
/* Table: tag                                                   */
/*==============================================================*/
create table tag
(
   id                   bigint comment 'id',
   name                 varchar(32) comment '标签名'
);

/*==============================================================*/
/* Table: teacher                                               */
/*==============================================================*/
create table teacher
(
   user_id              bigint comment '用户id',
   department_id        bigint comment '系部id',
   name                 varchar(16) comment '姓名'
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   bigint not null comment 'id',
   username             varchar(32) comment '用户名',
   password             varchar(128) comment '密码',
   salt                 varchar(32) comment '密码盐',
   role_id              bigint comment '角色id',
   primary key (id)
);

