

alter table dispatch_detail
   drop constraint FK_item_id;

alter table dispatch_detail
   drop constraint FK_v_detail;

alter table dispatch_result
   drop constraint FK_sheet_id;

alter table dispatch_result
   drop constraint FK_status_id;

alter table sys_department
   drop constraint FK_area_id;

alter table sys_department
   drop constraint FK_manage_id;

alter table sys_employee
   drop constraint FK_dept_id;

alter table sys_employee
   drop constraint FK_positions_id;

drop table area cascade constraints;

drop table detail_Item cascade constraints;

drop table dispatch_detail cascade constraints;

drop table dispatch_list cascade constraints;

drop table dispatch_result cascade constraints;

drop table dispatch_status cascade constraints;

drop table log_operation cascade constraints;

drop table log_print cascade constraints;

drop table login_log cascade constraints;

drop table login_user cascade constraints;

drop table sys_department cascade constraints;

drop table sys_employee cascade constraints;

drop table sys_positions cascade constraints;


/*==============================================================*/
/* Table: area                                                  */
/*==============================================================*/
create table area  (
   area_id              number                          not null,
   area_name            varchar(50),
   constraint PK_AREA primary key (area_id)
);

/*==============================================================*/
/* Table: detail_Item                                           */
/*==============================================================*/
create table detail_Item  (
   de_id                number                          not null,
   item_name            varchar(20),
   constraint PK_DETAIL_ITEM primary key (de_id)
);

create sequence seq_detail_Item
minvalue 0
start with 0
increment by 1
cache 20
order;


/*==============================================================*/
/* Table: dispatch_detail                                       */
/*==============================================================*/
create table dispatch_detail  (
   ds_id                number                          not null,
   sheet_id             number,
   money                number(6,2),
   cost_explain         varchar(255),
   flag                 number(1),
   item_id              number,
   accessory            blob,
   constraint PK_DIS_DETAIL primary key (ds_id)
);
create sequence seq_dispatch_detail
minvalue 0
start with 0
increment by 1
cache 20
order;

/*==============================================================*/
/* Table: dispatch_list                                         */
/*==============================================================*/
create table dispatch_list  (
   dl_id                number                          not null,
   e_sn                 char(8),
   create_time          date,
   event_explain        varchar(255),
   flag                 number(1),
   constraint PK_DIS_LIST primary key (dl_id)
);
create sequence seq_dispatch_list
minvalue 0
start with 0
increment by 1
cache 20
order;

/*==============================================================*/
/* Table: dispatch_result                                       */
/*==============================================================*/
create table dispatch_result  (
   dr_id                number                          not null,
   sheet_id             number,
   check_next           varchar(20),
   check_time           date,
   check_sn             char(8),
   check_comment        varchar(255),
   check_status         number,
   constraint PK_RESULT primary key (dr_id)
);
create sequence seq_dispatch_result
minvalue 0
start with 0
increment by 1
cache 20
order;

/*==============================================================*/
/* Table: dispatch_status                                       */
/*==============================================================*/
create table dispatch_status  (
   da_id                number                          not null,
   da_status            varchar(6),
   constraint PK_STATUS primary key (da_id)
);
create sequence seq_dispatch_status
minvalue 0
start with 0
increment by 1
cache 20
order;

/*==============================================================*/
/* Table: log_operation                                         */
/*==============================================================*/
create table log_operation  (
   lo_id                number                          not null,
   ip_addr              varchar(20),
   user_id              number,
   method_type          varchar(20),
   method_name          varchar(20),
   class_name           varchar(20),
   describe            varchar(50),
   exception_info       varchar(50),
   operate_date         date,
   expand_time          number(12,0),
   constraint PK_OPERATION primary key (lo_id)
);
create sequence seq_log_operation
minvalue 0
start with 0
increment by 1
cache 20
order;

/*==============================================================*/
/* Table: log_print                                             */
/*==============================================================*/
create table log_print  (
   lp_id                number                          not null,
   print_date           date,
   ip_addr              varchar(20),
   mac_addr             varchar(20),
   user_id              number,
   constraint PK_LOG_PRINT primary key (lp_id)
);
create sequence seq_log_print
minvalue 0
start with 0
increment by 1
cache 20
order;

/*==============================================================*/
/* Table: login_log                                             */
/*==============================================================*/
create table login_log  (
   logid                number                          not null,
   user_id              number,
   user_sn              char(8),
   position             number,
   ip_addr              varchar(20),
   mac_addr             varchar(20),
   log_date             date,
   session_id           varchar(20),
   remark               varchar(50),
   constraint PK_LOGIN primary key (logid)
);
create sequence seq_login_log
minvalue 0
start with 0
increment by 1
cache 20
order;

/*==============================================================*/
/* Table: login_user                                            */
/*==============================================================*/
create table login_user  (
   u_id                number                          not null,
   e_sn                 char(8),
   u_pwd                 varchar(50),
   constraint PK_USER primary key (u_id)
);
create sequence seq_login_user
minvalue 0
start with 0
increment by 1
cache 20
order;

/*==============================================================*/
/* Table: sys_department                                        */
/*==============================================================*/
create table sys_department  (
   d_id                 number                          not null,
   d_name               varchar(45),
   area_id              number,
   manage_sn            char(8),
   constraint PK_DEPT primary key (d_id)
);
create sequence seq_sys_department
minvalue 0
start with 0
increment by 1
cache 20
order;


/*==============================================================*/
/* Table: sys_employee                                          */
/*==============================================================*/
create table sys_employee  (
   e_id                 number                          not null,
   e_sn                 char(8),
   e_name               varchar(15),
   e_identity           char(20),
   sex                  number(1,0),
   birthday             date,
   address              varchar(100),
   tel                  varchar(15),
   department_id        number,
   p_id                 number,
   status               number(1,0),
   in_time              date,
   out_time             date,
   constraint PK_EMPLOYEE primary key (e_id),
   constraint AK_E_SN unique (e_sn)
);
create sequence seq_sys_employee
minvalue 0
start with 0
increment by 1
cache 20
order;


/*==============================================================*/
/* Table: sys_positions                                         */
/*==============================================================*/
create table sys_positions  (
   p_id                 number                          not null,
   p_name_cn            varchar(45),
   p_name_en            varchar(45),
   constraint PK_P_id primary key (p_id)
);
create sequence seq_sys_positions
minvalue 0
start with 0
increment by 1  
cache 20
order;

alter table dispatch_detail
   add constraint FK_item_id foreign key (item_id)
      references detail_Item (de_id);

alter table dispatch_detail
   add constraint FK_v_detail foreign key (sheet_id)
      references dispatch_list (dl_id);

alter table dispatch_result
   add constraint FK_sheet_id foreign key (sheet_id)
      references dispatch_list (dl_id);

alter table dispatch_result
   add constraint FK_status_id foreign key (check_status)
      references dispatch_status (da_id);

alter table sys_department
   add constraint FK_area_id foreign key (area_id)
      references area (area_id);

alter table sys_department
   add constraint FK_manage_sn foreign key (manage_sn)
      references sys_employee (e_sn);

alter table sys_employee
   add constraint FK_dept_id foreign key (department_id)
      references sys_department (d_id);

alter table sys_employee
   add constraint FK_positions_id foreign key (p_id)
      references sys_positions (p_id);

alter table login_user
   add constraint FK_u_sn foreign key (e_sn)
      references sys_employee (e_sn);
alter table dispatch_list
   add constraint FK_dis_sn foreign key (e_sn)
      references sys_employee (e_sn);
        
insert into sys_positions (p_id, p_name_cn, p_name_en) values (seq_sys_positions.nextval, '总经理', 'GMANAGER');
insert into sys_positions (p_id, p_name_cn, p_name_en) values (seq_sys_positions.nextval, '部门经理', 'DMANAGER');
insert into sys_positions (p_id, p_name_cn, p_name_en) values (seq_sys_positions.nextval, '雇员', 'EMPLOYEE');

insert into area (area_id, area_name) values (1, '华中');

insert into sys_department (d_id, d_name, area_id, manage_sn) values (seq_sys_department.nextval, '销售部', 1, null);
insert into sys_department (d_id, d_name, area_id, manage_sn) values (seq_sys_department.nextval, '采购部', 1, null);
insert into sys_department (d_id, d_name, area_id, manage_sn) values (seq_sys_department.nextval, '财务部', 1, null);

insert into sys_employee 
(e_id, e_sn, e_name, e_identity, sex, birthday, address, tel, department_id, p_id, status, in_time, out_time) 
values 
(seq_sys_employee.nextval, '10000', 'hz', '3607**************', 1, sysdate, '江西省赣州', '13263092439', 1, 3, 0, sysdate, null);

insert into detail_Item (de_id, item_name) values (seq_detail_Item.Nextval, '交通费');
insert into detail_Item (de_id, item_name) values (seq_detail_Item.Nextval, '住宿费');
insert into detail_Item (de_id, item_name) values (seq_detail_Item.Nextval, '住宿费');
insert into detail_Item (de_id, item_name) values (seq_detail_Item.Nextval, '通讯费');

insert into dispatch_status (da_id, da_status) values (seq_dispatch_status.nextval, '草稿');
insert into dispatch_status (da_id, da_status) values (seq_dispatch_status.nextval, '待审批');
insert into dispatch_status (da_id, da_status) values (seq_dispatch_status.nextval, '已审批');
insert into dispatch_status (da_id, da_status) values (seq_dispatch_status.nextval, '已终止');
insert into dispatch_status (da_id, da_status) values (seq_dispatch_status.nextval, '已打回');
insert into dispatch_status (da_id, da_status) values (seq_dispatch_status.nextval, '待付款');
insert into dispatch_status (da_id, da_status) values (seq_dispatch_status.nextval, '已付款');


