# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table activity (
  id                        bigint not null,
  name                      varchar(255),
  begin_date                bigint,
  begin_time                bigint,
  end_date                  bigint,
  end_time                  bigint,
  cost                      float,
  number_of_children        integer,
  playground_id             bigint,
  constraint pk_activity primary key (id))
;

create table address (
  id                        bigint not null,
  street                    varchar(255),
  number                    varchar(255),
  zip_code                  varchar(255),
  city                      varchar(255),
  constraint pk_address primary key (id))
;

create table basic_user (
  utype                     varchar(31) not null,
  id                        varchar(255) not null,
  password                  varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  gender                    integer,
  phone                     varchar(255),
  email                     varchar(255),
  date_of_birth             bigint,
  language                  integer,
  active                    boolean,
  address_id                bigint,
  has_followed_course       boolean,
  account_number            varchar(255),
  administration            boolean,
  playground_id             bigint,
  phone_work                varchar(255),
  phone_alt                 varchar(255),
  receive_mail              boolean,
  photographable            boolean,
  remarks                   varchar(255),
  doctor                    varchar(255),
  not_payed                 float,
  on_playground             boolean,
  number_of_sessions        integer,
  card_id                   bigint,
  constraint ck_basic_user_gender check (gender in (0,1)),
  constraint ck_basic_user_language check (language in (0)),
  constraint pk_basic_user primary key (id))
;

create table child_day (
  id                        bigint not null,
  date                      bigint,
  amount_payed              float,
  child_id                  varchar(255),
  constraint pk_child_day primary key (id))
;

create table child_session_card (
  id                        bigint not null,
  child_id                  varchar(255),
  left_over                 integer,
  constraint pk_child_session_card primary key (id))
;

create table formula (
  id                        bigint not null,
  name                      varchar(255),
  cost                      float,
  session_card_compensation integer,
  playground_id             bigint,
  constraint pk_formula primary key (id))
;

create table formula_day (
  id                        bigint not null,
  date                      bigint,
  formula_id                bigint,
  playground_day_id         bigint,
  constraint pk_formula_day primary key (id))
;

create table playground (
  id                        bigint not null,
  name                      varchar(255),
  phone                     varchar(255),
  email                     varchar(255),
  website                   varchar(255),
  address_id                bigint,
  constraint pk_playground primary key (id))
;

create table playground_day (
  id                        bigint not null,
  date                      bigint,
  money_income              float,
  playground_id             bigint,
  constraint pk_playground_day primary key (id))
;

create table role (
  id                        bigint not null,
  name                      varchar(255),
  begin_age                 bigint,
  end_age                   bigint,
  playground_id             bigint,
  constraint pk_role primary key (id))
;

create table session_card (
  id                        bigint not null,
  number_of_sessions        integer,
  cost                      float,
  active                    boolean,
  playground_id             bigint,
  constraint pk_session_card primary key (id))
;


create table activity_role (
  activity_id                    bigint not null,
  role_id                        bigint not null,
  constraint pk_activity_role primary key (activity_id, role_id))
;

create table basic_user_activity (
  basic_user_id                  varchar(255) not null,
  activity_id                    bigint not null,
  constraint pk_basic_user_activity primary key (basic_user_id, activity_id))
;

create table basic_user_playground_day (
  basic_user_id                  varchar(255) not null,
  playground_day_id              bigint not null,
  constraint pk_basic_user_playground_day primary key (basic_user_id, playground_day_id))
;

create table basic_user_formula_day (
  basic_user_id                  varchar(255) not null,
  formula_day_id                 bigint not null,
  constraint pk_basic_user_formula_day primary key (basic_user_id, formula_day_id))
;

create table child_day_formula (
  child_day_id                   bigint not null,
  formula_id                     bigint not null,
  constraint pk_child_day_formula primary key (child_day_id, formula_id))
;
create sequence activity_seq;

create sequence address_seq;

create sequence basic_user_seq;

create sequence child_day_seq;

create sequence child_session_card_seq;

create sequence formula_seq;

create sequence formula_day_seq;

create sequence playground_seq;

create sequence playground_day_seq;

create sequence role_seq;

create sequence session_card_seq;

alter table activity add constraint fk_activity_playground_1 foreign key (playground_id) references playground (id);
create index ix_activity_playground_1 on activity (playground_id);
alter table basic_user add constraint fk_basic_user_address_2 foreign key (address_id) references address (id);
create index ix_basic_user_address_2 on basic_user (address_id);
alter table basic_user add constraint fk_basic_user_playground_3 foreign key (playground_id) references playground (id);
create index ix_basic_user_playground_3 on basic_user (playground_id);
alter table basic_user add constraint fk_basic_user_address_4 foreign key (address_id) references address (id);
create index ix_basic_user_address_4 on basic_user (address_id);
alter table basic_user add constraint fk_basic_user_playground_5 foreign key (playground_id) references playground (id);
create index ix_basic_user_playground_5 on basic_user (playground_id);
alter table basic_user add constraint fk_basic_user_card_6 foreign key (card_id) references child_session_card (id);
create index ix_basic_user_card_6 on basic_user (card_id);
alter table basic_user add constraint fk_basic_user_playground_7 foreign key (playground_id) references playground (id);
create index ix_basic_user_playground_7 on basic_user (playground_id);
alter table child_day add constraint fk_child_day_child_8 foreign key (child_id) references basic_user (id);
create index ix_child_day_child_8 on child_day (child_id);
alter table child_session_card add constraint fk_child_session_card_child_9 foreign key (child_id) references basic_user (id);
create index ix_child_session_card_child_9 on child_session_card (child_id);
alter table formula add constraint fk_formula_playground_10 foreign key (playground_id) references playground (id);
create index ix_formula_playground_10 on formula (playground_id);
alter table formula_day add constraint fk_formula_day_formula_11 foreign key (formula_id) references formula (id);
create index ix_formula_day_formula_11 on formula_day (formula_id);
alter table formula_day add constraint fk_formula_day_playgroundDay_12 foreign key (playground_day_id) references playground_day (id);
create index ix_formula_day_playgroundDay_12 on formula_day (playground_day_id);
alter table playground add constraint fk_playground_address_13 foreign key (address_id) references address (id);
create index ix_playground_address_13 on playground (address_id);
alter table playground_day add constraint fk_playground_day_playground_14 foreign key (playground_id) references playground (id);
create index ix_playground_day_playground_14 on playground_day (playground_id);
alter table role add constraint fk_role_playground_15 foreign key (playground_id) references playground (id);
create index ix_role_playground_15 on role (playground_id);
alter table session_card add constraint fk_session_card_playground_16 foreign key (playground_id) references playground (id);
create index ix_session_card_playground_16 on session_card (playground_id);



alter table activity_role add constraint fk_activity_role_activity_01 foreign key (activity_id) references activity (id);

alter table activity_role add constraint fk_activity_role_role_02 foreign key (role_id) references role (id);

alter table basic_user_activity add constraint fk_basic_user_activity_basic__01 foreign key (basic_user_id) references basic_user (id);

alter table basic_user_activity add constraint fk_basic_user_activity_activi_02 foreign key (activity_id) references activity (id);

alter table basic_user_playground_day add constraint fk_basic_user_playground_day__01 foreign key (basic_user_id) references basic_user (id);

alter table basic_user_playground_day add constraint fk_basic_user_playground_day__02 foreign key (playground_day_id) references playground_day (id);

alter table basic_user_formula_day add constraint fk_basic_user_formula_day_bas_01 foreign key (basic_user_id) references basic_user (id);

alter table basic_user_formula_day add constraint fk_basic_user_formula_day_for_02 foreign key (formula_day_id) references formula_day (id);

alter table child_day_formula add constraint fk_child_day_formula_child_da_01 foreign key (child_day_id) references child_day (id);

alter table child_day_formula add constraint fk_child_day_formula_formula_02 foreign key (formula_id) references formula (id);

# --- !Downs

drop table if exists activity cascade;

drop table if exists basic_user_activity cascade;

drop table if exists activity_role cascade;

drop table if exists address cascade;

drop table if exists basic_user cascade;

drop table if exists child_day cascade;

drop table if exists child_day_formula cascade;

drop table if exists child_session_card cascade;

drop table if exists formula cascade;

drop table if exists formula_day cascade;

drop table if exists basic_user_formula_day cascade;

drop table if exists playground cascade;

drop table if exists playground_day cascade;

drop table if exists basic_user_playground_day cascade;

drop table if exists role cascade;

drop table if exists session_card cascade;

drop sequence if exists activity_seq;

drop sequence if exists address_seq;

drop sequence if exists basic_user_seq;

drop sequence if exists child_day_seq;

drop sequence if exists child_session_card_seq;

drop sequence if exists formula_seq;

drop sequence if exists formula_day_seq;

drop sequence if exists playground_seq;

drop sequence if exists playground_day_seq;

drop sequence if exists role_seq;

drop sequence if exists session_card_seq;

