---user
alter session set "_ORACLE_SCRIPT"=true;  
create user egoist identified by egoist;
grant unlimited tablespace to egoist;
grant resource, connect, dba to egoist;