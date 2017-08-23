INSERT INTO `np_service` (`id`,`service_code`,`service_key`,`service_name`,`service_host`,`create_user_id`,`create_username`,`check_auth`,`service_port`,`publish_status`,`version`,`is_menu`,`has_submenu`,`menu_url`,`menu_name`,`target_id`,`template_crud`,`check_safe`,`role_menu_api`,`role_permission_api`,`menu_tree_api`,`remark`,`create_date`) VALUES (11,'S_EAGLEEYE','ZPOEEeGBc4Nwpi3i6FpnOwxd81pQ3k3V','鹰眼服务','http://alpha-np.51awifi.com',32,'superadmin','/eagleeyesrv/permission/check','80',1,'v1.0','1','1','#/eagleeyesrv/list','鹰眼日志','right','/eagleeyesrv/am/template','/eagleeyesrv/security/check','/eagleeyesrv/am/role/{roleid}/menus','/eagleeyesrv/am/role/{roleid}/permissions','/eagleeyesrv/am/menus',NULL,'2017-07-26 09:31:35');

INSERT INTO `awifi_np_admin`.`np_platform_service`(`app_id`,`service_code`,`is_owner`,`list_order`)VALUES('NP_BIZ', 'S_EAGLEEYE', '1', '180');

INSERT INTO `awifi_np_admin`.`np_template`
(`id`,
`template_name`,
`template_code`,
`suit_code`,
`service_code`,
`url`,
`create_date`,
`create_user_id`,
`update_date`,
`update_user_id`,
`remark`)
VALUES
('10191', 'traceid查询', 'eagleeye_trace_list', 'superadmin_v1', 'S_EAGLEEYE', NULL, '2017-08-04 10:32:15', NULL, '2017-08-04 10:32:19', NULL, NULL
);

INSERT INTO `awifi_np_admin`.`np_template`
(`id`,
`template_name`,
`template_code`,
`suit_code`,
`service_code`,
`url`,
`create_date`,
`create_user_id`,
`update_date`,
`update_user_id`,
`remark`)
VALUES
('10192', '调用链监控', 'eagleeye_trace_view', 'superadmin_v1', 'S_EAGLEEYE', NULL, '2017-08-04 10:32:15', NULL, '2017-08-04 10:32:19', NULL, NULL
);

INSERT INTO `awifi_np_admin`.`np_platform_service`
(`app_id`,
`service_code`,
`is_owner`,
`list_order`)
VALUES
('NP_BIZ', 'S_EAGLEEYE', '1', '180');


INSERT INTO `awifi_np_biz`.`np_biz_menu`
(`id`,
`name`,
`url`,
`parent_id`,
`target_id`,
`order_no`,
`create_date`,
`update_date`,
`service_code`)
VALUES
('82', 'traceid查询', '#/eagleeye/trace/list', '-1', 'right', '7', '2017-08-04 10:29:41', '2017-08-15 10:06:43', 'S_EAGLEEYE'
);

INSERT INTO `awifi_np_biz`.`np_biz_menu`
(`id`,
`name`,
`url`,
`parent_id`,
`target_id`,
`order_no`,
`create_date`,
`update_date`,
`service_code`)
VALUES
('83', '调用链监控', '#/eagleeye/trace/detail', '-1', 'right', '7', '2017-08-04 10:29:41', '2017-08-15 10:06:43', 'S_EAGLEEYE'
);

INSERT INTO `awifi_np_biz`.`np_biz_role_menu`
(`role_id`,
`menu_id`)
VALUES
('1', '82');

INSERT INTO `awifi_np_biz`.`np_biz_role_menu`
(`role_id`,
`menu_id`)
VALUES
('1', '83');


INSERT INTO `awifi_np_biz`.`np_biz_suit_template`
(`suit_code`,
`template_id`)
VALUES
('superadmin_v1', '155');

INSERT INTO `awifi_np_biz`.`np_biz_suit_template`
(`suit_code`,
`template_id`)
VALUES
('superadmin_v1', '156');



INSERT INTO `awifi_np_biz`.`np_biz_template`
(`id`,
`code`,
`name`,
`src`,
`content`,
`remark`,
`create_date`,
`update_date`,
`service_code`)
VALUES
('155', 'eagleeye_trace_list', 'trace查询', '1', '<div class=\"table-wrap\"><app></app></div> <script src=\"/static/superadmin_v1/js/eagle_trace_list.js\"></script>', NULL, '2017-08-10 10:49:22', '2017-08-15 10:06:12', 'S_EAGLEEYE'
);

INSERT INTO `awifi_np_biz`.`np_biz_template`
(`id`,
`code`,
`name`,
`src`,
`content`,
`remark`,
`create_date`,
`update_date`,
`service_code`)
VALUES
('156', 'eagleeye_trace_detail', '调用链监控', '1', '<div class=\"table-wrap\"><app></app></div> <script src=\"/static/superadmin_v1/js/eagle_trace_view.js\"></script>', NULL, '2017-08-10 10:49:22', '2017-08-15 10:06:12', 'S_EAGLEEYE'
);
