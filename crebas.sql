/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/10/26 14:21:45                          */
/*==============================================================*/


drop table if exists announcement;

drop table if exists chosen_subject;

drop table if exists department;

drop table if exists final_subject;

drop table if exists period;

drop table if exists role;

drop table if exists student;

drop table if exists subject;

drop table if exists subject_to_tag;

drop table if exists tag;

drop table if exists teacher;

drop table if exists user;

drop table if exists template;

/*==============================================================*/
/* Table: announcement                                          */
/*==============================================================*/
create table announcement
(
   id                   bigint not null comment 'id',
   content              varchar(512) comment '内容',
   create_time          datetime comment '创建时间',
   primary key (id)
);

/*==============================================================*/
/* Table: chosen_subject                                        */
/*==============================================================*/
create table chosen_subject
(
   student_id           bigint comment '学生id',
   subject_id           bigint comment '选题id',
   chosen_time          datetime comment '选题时间'
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
/* Table: final_subject                                         */
/*==============================================================*/
create table final_subject
(
   student_id           bigint comment '学生id',
   subject_id           bigint comment '选题id',
   final_chosen_time    datetime comment '最终选中时间'
);

/*==============================================================*/
/* Table: period                                                */
/*==============================================================*/
create table period
(
   id                   bigint comment 'id',
   name                 varchar(64) comment '区间名称',
   start_time           datetime comment '开始时间',
   end_time             datetime comment '结束时间'
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

/*==============================================================*/
/* Table: template                                                  */
/*==============================================================*/
create table template
(
    id          bigint       null comment 'id',
    file_name   varchar(64)  null comment '样板文件名称',
    sha256      varchar(256) null comment '文件的256校验值',
    upload_time datetime     null comment '上传时间',
    is_delete   tinyint(1)   null comment '是否被删除',
    delete_time datetime     null comment '删除时间'
);

