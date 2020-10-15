/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/10/14 9:53:49                           */
/*==============================================================*/


drop table if exists department;

drop table if exists role;

drop table if exists student;

drop table if exists teacher;

drop table if exists user;

/*==============================================================*/
/* Table: department                                            */
/*==============================================================*/
create table department
(
   id                   bigint not null comment 'id',
   name                 varchar(32) comment 'ϵ������',
   primary key (id)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   id                   bigint not null comment 'id',
   name                 varchar(32) comment '��ɫ����',
   primary key (id)
);

/*==============================================================*/
/* Table: student                                               */
/*==============================================================*/
create table student
(
   user_id              bigint comment '�û�id',
   class_name           varchar(32) comment '�༶',
   name                 varchar(16) comment '����',
   department_id        bigint comment 'ϵ��id'
);

/*==============================================================*/
/* Table: teacher                                               */
/*==============================================================*/
create table teacher
(
   user_id              bigint comment '�û�id',
   department_id        bigint comment 'ϵ��id',
   name                 varchar(16) comment '����'
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   bigint not null comment 'id',
   username             varchar(32) comment '�û���',
   password             varchar(128) comment '����',
   salt                 varchar(32) comment '������',
   role_id              bigint comment '��ɫid',
   primary key (id)
);

