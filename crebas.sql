/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/10/14 9:53:49                           */
/*==============================================================*/


drop table if exists department;

drop table if exists role;

drop table if exists student;

drop table if exists teacher;

drop table if exists user;

drop table if exists templet;

/*==============================================================*/
/* Table: template                                               */
/*==============================================================*/
create table template
(
   id                   bigint comment 'id',
   file_name            varchar(64) comment '样板文件名称',
   sha256               varchar(256) comment '文件的256校验值',
   upload_time          datetime comment '上传时间',
   is_delete            bool comment '是否被删除',
   delete_time          datetime comment '删除时间'
);

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

