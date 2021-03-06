insert into awifi_np_biz.np_biz_menu(id,name,url,parent_id,target_id,order_no,service_code)
values (77,'充值结算','#/statshow/statistics_charge_settlement',-1,'right',7,'S_STAT');
insert into awifi_np_biz.np_biz_menu(id,name,url,parent_id,target_id,order_no,service_code)
values (78,'充值记录','#/statshow/statistics_timebuy_pay',-1,'right',7,'S_STAT');
insert into awifi_np_biz.np_biz_menu(id,name,url,parent_id,target_id,order_no,service_code)
values (79,'赔付记录','#/statshow/statistics_timebuy_payment',-1,'right',7,'S_STAT');
insert into awifi_np_biz.np_biz_menu(id,name,url,parent_id,target_id,order_no,service_code)
values (80,'免费礼包领取','#/statshow/statistics_timebuy_freepkg',-1,'right',7,'S_STAT');
insert into awifi_np_biz.np_biz_menu(id,name,url,parent_id,target_id,order_no,service_code)
values (81,'CRM白名单','#/statshow/statistics_timebuy_vip',-1,'right',7,'S_STAT');


insert into awifi_np_biz.np_biz_role_menu(role_id,menu_id) values (1,77);
insert into awifi_np_biz.np_biz_role_menu(role_id,menu_id) values (1,78);
insert into awifi_np_biz.np_biz_role_menu(role_id,menu_id) values (1,79);
insert into awifi_np_biz.np_biz_role_menu(role_id,menu_id) values (1,80);
insert into awifi_np_biz.np_biz_role_menu(role_id,menu_id) values (1,81);


insert into awifi_np_biz.np_biz_template(id,code,name,src,content,service_code)
values(150,'statistics_charge_settlement','充值结算','1','<div class="table-wrap"><app></app></div> <script src="/static/superadmin_v1/js/statistics_charge_settlement.js"></script>','S_STAT');
insert into awifi_np_biz.np_biz_template(id,code,name,src,content,service_code)
values(151,'statistics_timebuy_pay','充值记录','1','<div class="table-wrap"><app></app></div> <script src="/static/superadmin_v1/js/statistics_timebuy_pay.js"></script>','S_STAT');
insert into awifi_np_biz.np_biz_template(id,code,name,src,content,service_code)
values(152,'statistics_timebuy_payment','赔付记录','1','<div class="table-wrap"><app></app></div> <script src="/static/superadmin_v1/js/statistics_timebuy_payment.js"></script>','S_STAT');
insert into awifi_np_biz.np_biz_template(id,code,name,src,content,service_code)
values(153,'statistics_timebuy_freepkg','免费礼包记录','1','<div class="table-wrap"><app></app></div> <script src="/static/superadmin_v1/js/statistics_timebuy_freepkg.js"></script>','S_STAT');
insert into awifi_np_biz.np_biz_template(id,code,name,src,content,service_code)
values(154,'statistics_timebuy_vip','CRM白名单','1','<div class="table-wrap"><app></app></div> <script src="/static/superadmin_v1/js/statistics_timebuy_vip.js"></script>','S_STAT');


insert into awifi_np_biz.np_biz_suit_template(suit_code,template_id) value ('superadmin_v1',150);
insert into awifi_np_biz.np_biz_suit_template(suit_code,template_id) value ('superadmin_v1',151);
insert into awifi_np_biz.np_biz_suit_template(suit_code,template_id) value ('superadmin_v1',152);
insert into awifi_np_biz.np_biz_suit_template(suit_code,template_id) value ('superadmin_v1',153);
insert into awifi_np_biz.np_biz_suit_template(suit_code,template_id) value ('superadmin_v1',154);


insert into awifi_np_admin.np_template(id,template_name,template_code,suit_code,service_code,create_date,update_date)
values(10186,'统计结算','statistics_charge_settlement','superadmin_v1','S_STAT',now(),now());
insert into awifi_np_admin.np_template(id,template_name,template_code,suit_code,service_code,create_date,update_date)
values(10187,'充值记录','statistics_timebuy_pay','superadmin_v1','S_STAT',now(),now());
insert into awifi_np_admin.np_template(id,template_name,template_code,suit_code,service_code,create_date,update_date)
values(10188,'赔付记录','statistics_timebuy_payment','superadmin_v1','S_STAT',now(),now());
insert into awifi_np_admin.np_template(id,template_name,template_code,suit_code,service_code,create_date,update_date)
values(10189,'免费礼包领取记录','statistics_timebuy_freepkg','superadmin_v1','S_STAT',now(),now());
insert into awifi_np_admin.np_template(id,template_name,template_code,suit_code,service_code,create_date,update_date)
values(10190,'CRM白名单','statistics_timebuy_vip','superadmin_v1','S_STAT',now(),now());


-- 园区统计 权限
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(70090,'S_TIMEBUY','付费清单查询','/timebuysrv/statistics/consume:GET',70090);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(70100,'S_TIMEBUY','赔付清单报表查询','/timebuysrv/statistics/compenstate:GET',70100);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(70110,'S_TIMEBUY','体验包领取记录查询','/timebuysrv/statistics/free:GET',70110);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(70120,'S_TIMEBUY','付费清单导出','/timebuysrv/statistics/consume/xls:GET',70120);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(70130,'S_TIMEBUY','赔付记录导出','/timebuysrv/statistics/compenstate/xls:GET',70130);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(70140,'S_TIMEBUY','体验包领取记录导出','/timebuysrv/statistics/free/xls:GET',70140);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(70150,'S_TIMEBUY','vip用户查询','/timebuysrv/vip/search:GET',70150);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(70160,'S_TIMEBUY','vip用户记录导出','/timebuysrv/vip/xls:GET',70160);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(70170,'S_TIMEBUY','充值结算记录导出','/timebuysrv/stat/location/multiple/xls:GET',70170);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(70180,'S_TIMEBUY','园区报表统计接口','/timebuysrv/stat/location/multiple:GET',70180);

-- 园区统计 role权限
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,70090);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,70100);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,70110);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,70120);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,70130);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,70140);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,70150);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,70160);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,70170);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,70180);

-- 商户数据每日统计表
drop table if exists awifiopms.`center_pub_merchant_statistics`;
create table awifiopms.`center_pub_merchant_statistics` (
  `id` bigint(12) not null auto_increment,
  `merchant_id` bigint(12) default null comment '商户编号',
  `merchant_num` int(11) default null comment '园区数量',
  `device_num` int(11) default null comment '设备总数',
  `total_paid` decimal(12,2) default null comment '付费总金额',
  `total_users` int(12) default null comment '使用总人数',
  `vip_users` int(11) default null comment '白名单人数',
  `pay_users` int(20) default null comment '付费人数',
  `pkg_days` int(12) default null comment '包天数量',
  `pkg_months` int(12) default null comment '包月数量',
  `pkg_years` int(12) default null comment '包年数量',
  `create_date` datetime default null on update current_timestamp comment '创建时间',
  `stat_date` int(11) default null,
  primary key (`id`),
  unique key `merchant_id` (`merchant_id`,`stat_date`),
  unique key `merchant_id_2` (`merchant_id`,`stat_date`)
) engine=innodb auto_increment=870 default charset=utf8 comment='商户数据每日统计表';



INSERT INTO `awifi_np_admin`.`np_interface` (`id`, `service_code`, `interface_name`, `interface_code`, `interface_url`, `interface_method`, `ifcheck`, `remark`, `create_date`, `update_date`, `create_user_id`, `update_user_id`, `status`) VALUES ('135584', 'S_TIMEBUY', '付费清单查询', '/timebuysrv/statistics/consume:GET', '/timebuysrv/statistics/consume', 'GET', '1', NULL,NOW(), NOW(), '32', '32', '1');
INSERT INTO `awifi_np_admin`.`np_interface` (`id`, `service_code`, `interface_name`, `interface_code`, `interface_url`, `interface_method`, `ifcheck`, `remark`, `create_date`, `update_date`, `create_user_id`, `update_user_id`, `status`) VALUES ('135585', 'S_TIMEBUY', '赔付清单报表查询', '/timebuysrv/statistics/compenstate:GET', '/timebuysrv/statistics/compenstate', 'GET', '1', NULL, NOW(), NOW(), '32', '32', '1');
INSERT INTO `awifi_np_admin`.`np_interface` (`id`, `service_code`, `interface_name`, `interface_code`, `interface_url`, `interface_method`, `ifcheck`, `remark`, `create_date`, `update_date`, `create_user_id`, `update_user_id`, `status`) VALUES ('135586', 'S_TIMEBUY', '体验包领取记录查询', '/timebuysrv/statistics/free:GET', '/timebuysrv/statistics/free', 'GET', '1', NULL, NOW(), NOW(), '32', '32', '1');
INSERT INTO `awifi_np_admin`.`np_interface` (`id`, `service_code`, `interface_name`, `interface_code`, `interface_url`, `interface_method`, `ifcheck`, `remark`, `create_date`, `update_date`, `create_user_id`, `update_user_id`, `status`) VALUES ('135587', 'S_TIMEBUY', '付费清单导出', '/timebuysrv/statistics/consume/xls:GET', '/timebuysrv/statistics/consume/xls', 'GET', '1', NULL, NOW(), NOW(), '32', '32', '1');
INSERT INTO `awifi_np_admin`.`np_interface` (`id`, `service_code`, `interface_name`, `interface_code`, `interface_url`, `interface_method`, `ifcheck`, `remark`, `create_date`, `update_date`, `create_user_id`, `update_user_id`, `status`) VALUES ('135588', 'S_TIMEBUY', '赔付记录导出', '/timebuysrv/statistics/compenstate/xls:GET', '/timebuysrv/statistics/compenstate/xls', 'GET', '1', NULL, NOW(), NOW(), '32', '32', '1');
INSERT INTO `awifi_np_admin`.`np_interface` (`id`, `service_code`, `interface_name`, `interface_code`, `interface_url`, `interface_method`, `ifcheck`, `remark`, `create_date`, `update_date`, `create_user_id`, `update_user_id`, `status`) VALUES ('135589', 'S_TIMEBUY', '体验包领取记录导出', '/timebuysrv/statistics/free/xls:GET', '/timebuysrv/statistics/free/xls', 'GET', '1', NULL, NOW(), NOW(), '32', '32', '1');
INSERT INTO `awifi_np_admin`.`np_interface` (`id`, `service_code`, `interface_name`, `interface_code`, `interface_url`, `interface_method`, `ifcheck`, `remark`, `create_date`, `update_date`, `create_user_id`, `update_user_id`, `status`) VALUES ('135590', 'S_TIMEBUY', 'vip用户查询', '/timebuysrv/vip/search:GET', '/timebuysrv/vip/search', 'GET', '1', NULL, NOW(), NOW(), '32', '32', '1');
INSERT INTO `awifi_np_admin`.`np_interface` (`id`, `service_code`, `interface_name`, `interface_code`, `interface_url`, `interface_method`, `ifcheck`, `remark`, `create_date`, `update_date`, `create_user_id`, `update_user_id`, `status`) VALUES ('135591', 'S_TIMEBUY', 'vip用户记录导出', '/timebuysrv/vip/xls:GET', '/timebuysrv/vip/xls', 'GET', '1', NULL, NOW(), NOW(), '32', '32', '1');
INSERT INTO `awifi_np_admin`.`np_interface` (`id`, `service_code`, `interface_name`, `interface_code`, `interface_url`, `interface_method`, `ifcheck`, `remark`, `create_date`, `update_date`, `create_user_id`, `update_user_id`, `status`) VALUES ('135592', 'S_TIMEBUY', '充值结算查询', '/timebuysrv/stat/location/multiple:GET', '/timebuysrv/stat/location/multiple', 'GET', '1', NULL, NOW(), NOW(), '32', '32', '1');
INSERT INTO `awifi_np_admin`.`np_interface` (`id`, `service_code`, `interface_name`, `interface_code`, `interface_url`, `interface_method`, `ifcheck`, `remark`, `create_date`, `update_date`, `create_user_id`, `update_user_id`, `status`) VALUES ('135593', 'S_TIMEBUY', '充值结算记录导出', '/timebuysrv/stat/location/multiple/xls:GET', '/timebuysrv/stat/location/multiple/xls', 'GET', '1', NULL, NOW(), NOW(), '32', '32', '1');


ALTER TABLE `awifiopms`.`merchant_extends` 
ADD COLUMN `buyout` INT(11) NULL AFTER `mark2`;

-- 2017-8-18 09:51:06
ALTER TABLE `awifiopms`.`center_pub_merchant_user_consume` 
ADD COLUMN `status` INT NULL AFTER `agent`;



