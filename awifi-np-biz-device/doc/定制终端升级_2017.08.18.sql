-- 新增菜单及权限
insert into awifi_np_biz.`np_biz_template`(id,code,name,src,content, service_code) values (139,'device_upgrade_region_list', '定制终端区域升级', '1', '<div class=\"table-wrap\"><app></app></div><script src=\"/static/superadmin_v1/js/device_upgrade_region_list.js\"></script>', 'S_MERDEV');
insert into awifi_np_biz.`np_biz_template`(id,code,name,src,content, service_code) values (140,'device_upgrade_alone_list', '定制终端个性升级', '1', '<div class=\"table-wrap\"><app></app></div><script src=\"/static/superadmin_v1/js/device_upgrade_alone_list.js\"></script>', 'S_MERDEV');

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
('147', 'device_upg_alone_patches_list', '定制终端-管理升级包', '', '<div class=\"table-wrap\"><app></app></div><script src=\"/static/superadmin_v1/js/device_upg_alone_patches_list.js\"></script>', NULL, '2017-07-31 16:42:23', '2017-07-31 17:01:25', 'S_MERDEV'
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
('148', 'device_upg_alone_addtasks_list', '定制终端-添加任务', '', '<div class=\"table-wrap\"><app></app></div><script src=\"/static/superadmin_v1/js/device_upg_alone_addtasks_list.js\"></script>', NULL, '2017-07-31 16:43:45', '2017-08-02 09:55:15', 'S_MERDEV'
);



insert into awifi_np_biz.np_biz_menu(id,name,url,parent_id,target_id,order_no,service_code) values (63,'定制终端区域升级','#/merdevshow/device_upgrade_region_list',-1,'right',90,'S_MERDEV');
insert into awifi_np_biz.np_biz_menu(id,name,url,parent_id,target_id,order_no,service_code) values (64,'定制终端个性升级','#/merdevshow/device_upgrade_alone_list',-1,'right',91,'S_MERDEV');


insert into awifi_np_admin.np_template(id,template_name,template_code,suit_code,service_code) values(10176,'定制终端区域升级','device_upgrade_region_list','superadmin_v1','S_MERDEV');
insert into awifi_np_admin.np_template(id,template_name,template_code,suit_code,service_code) values(10177,'定制终端个性升级','device_upgrade_alone_list','superadmin_v1','S_MERDEV');
insert into awifi_np_admin.np_template(id,template_name,template_code,suit_code,service_code) values(10178,'定制终端个性升级包管理','device_upg_alone_patches_list','superadmin_v1','S_MERDEV');
insert into awifi_np_admin.np_template(id,template_name,template_code,suit_code,service_code) values(10179,'定制终端个性升级添加任务','device_upg_alone_addtasks_list','superadmin_v1','S_MERDEV');


insert into awifi_np_biz.np_biz_suit_template(suit_code,template_id) values ('superadmin_v1',139);
insert into awifi_np_biz.np_biz_suit_template(suit_code,template_id) values ('superadmin_v1',140);


insert into awifi_np_biz.np_biz_role_menu(role_id,menu_id) values (1,63);
insert into awifi_np_biz.np_biz_role_menu(role_id,menu_id) values (1,64);



-- 新增接口及权限
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-获取区域默认升级包列表", "/devsrv/device/upgrade/region/list:GET", "/devsrv/device/upgrade/region/list", "GET", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-新增终端地区升级", "/devsrv/device/upgrade/region/add:POST", "/devsrv/device/upgrade/region/add", "POST", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-查看升级情况", "/devsrv/device/upgrade/region/{id}:GET", "/devsrv/device/upgrade/region/{id}", "GET", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-删除终端地区升级", "/devsrv/device/upgrade/region/{id}:DELETE", "/devsrv/device/upgrade/region/{id}", "DELETE", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-判断是否已经有启用的升级包", "/devsrv/device/upgrade/region/exist/started/{id}:GET", "/devsrv/device/upgrade/region/exist/started/{id}", "GET", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-启用升级包", "/devsrv/device/upgrade/region/{id}:PUT", "/devsrv/device/upgrade/region/{id}", "PUT", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-获取个性化升级任务列表", "/devsrv/device/upgrade/alone/tasks:GET", "/devsrv/device/upgrade/alone/tasks", "GET", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-根据id查询升级包", "/devsrv/device/upgrade/alone/patch/{id}:GET", "/devsrv/device/upgrade/alone/patch/{id}", "GET", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-个性化设备查询", "/devsrv/device/upgrade/alone/devices:GET", "/devsrv/device/upgrade/alone/devices", "GET", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-添加个性化升级包", "/devsrv/device/upgrade/alone/addPatch:POST", "/devsrv/device/upgrade/alone/addPatch", "POST", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-获取个性化升级包列表", "/devsrv/device/upgrade/alone/patches:GET", "/devsrv/device/upgrade/alone/patches", "GET", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-删除个性化升级包", "/devsrv/device/upgrade/alone/deletePatch/{id}:DELETE", "/devsrv/device/upgrade/alone/deletePatch", "DELETE", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-添加个性化升级任务", "/devsrv/device/upgrade/alone/addTask:POST", "/devsrv/device/upgrade/alone/addTask", "POST", "0", "0");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_DEV", "定制终端-删除个性化升级任务", "/devsrv/device/upgrade/alone/deleteTask:DELETE", "/devsrv/device/upgrade/alone/deleteTask", "DELETE", "0", "0");


insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41710,"/devsrv/device/upgrade/region/list:GET","定制终端-获取区域默认升级包列表",41710,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41720,"/devsrv/device/upgrade/region/add:POST","定制终端-新增终端地区升级",41720,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41730,"/devsrv/device/upgrade/region/{id}:GET","定制终端-查看升级情况",41730,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41740,"/devsrv/device/upgrade/region/{id}:DELETE","定制终端-删除终端地区升级",41740,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41750,"/devsrv/device/upgrade/region/exist/started/{id}:GET","定制终端-判断是否已经有启用的升级包",41750,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41760,"/devsrv/device/upgrade/region/{id}:PUT","定制终端-启用升级包",41760,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41770,"/devsrv/device/upgrade/alone/tasks:GET","定制终端-获取个性化升级任务列表",41770,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41780,"/devsrv/device/upgrade/alone/patch/{id}:GET","定制终端-根据id查询个性化升级包",41780,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41790,"/devsrv/device/upgrade/alone/devices:GET","定制终端-个性化设备查询",41790,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41800,"/devsrv/device/upgrade/alone/addPatch:POST","定制终端-添加个性化升级包",41800,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41810,"/devsrv/device/upgrade/alone/patches:GET","定制终端-获取个性化升级包列表",41810,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41820,"/devsrv/device/upgrade/alone/deletePatch/{id}:DELETE","定制终端-删除个性化升级包",41820,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41830,"/devsrv/device/upgrade/alone/addTask:POST","定制终端-添加个性化升级任务",41830,"S_DEV");
insert into awifi_np_biz.np_biz_permission(id,code,name,order_no,service_code) values(41840,"/devsrv/device/upgrade/alone/deleteTask:DELETE","定制终端-删除个性化升级任务",41840,"S_DEV");


insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41710);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41710);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41720);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41720);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41730);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41730);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41740);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41740);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41750);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41750);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41760);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41760);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41770);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41770);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41780);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41780);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41790);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41790);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41800);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41800);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41810);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41810);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41820);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41820);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41830);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41830);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,41840);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(2,41840);



-- 新增配置项,调用数据中心接口
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-新增终端地区升级","dbc_addupgraderegion_url","http://192.168.41.48:28830/device-manage/upgrade/region");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-更新终端地区升级","dbc_updateupgraderegion_url","http://192.168.41.48:28830/device-manage/upgrade/region");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-删除终端地区升级","dbc_deleteupgraderegion_url","http://192.168.41.48:28830/device-manage/upgrade/region");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-查询终端地区升级List列表","dbc_querylistupgraderegion_url","http://192.168.41.48:28830/device-manage/upgrade/region/list");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-查询终端地区升级总数","dbc_querycountupgraderegion_url","http://192.168.41.48:28830/device-manage/upgrade/region/count");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-启用终端地区升级","dbc_startupgraderegion_url","http://192.168.41.48:28830/device-manage/upgrade/region/start");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-根据Id查询终端地区升级","dbc_querybyidupgraderegion_url","http://192.168.41.48:28830/device-manage/upgrade/region");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-根据Id查询任务个性化升级","dbc_UpgradeAloneQueryTaskById_url","http://192.168.41.48:28830/device-manage/upgrade/alone");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-根据Id查询升级包个性化升级","dbc_UpgradeAloneQueryById_url","http://192.168.41.48:28830/device-manage/upgrade/alone");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-添加升级包个性化升级","dbc_UpgradeAloneAdd_url","http://192.168.41.48:28830/device-manage/upgrade/alone");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-查询升级包列表个性化升级","dbc_UpgradeAloneQueryListByParam_url","http://192.168.41.48:28830/device-manage/upgrade/alone/list");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-统计升级包个性化升级","dbc_UpgradeAloneQueryCountByParam_url","http://192.168.41.48:28830/device-manage/upgrade/alone/count");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-删除升级包个性化升级","dbc_UpgradeAloneDelete_url","http://192.168.41.48:28830/device-manage/upgrade/alone");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-添加任务个性化升级","dbc_UpgradeAloneAddTask_url","http://192.168.41.48:28830/device-manage/upgrade/alone/task");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-查询任务列表个性化升级","dbc_UpgradeAloneQueryTaskListByParam_url","http://192.168.41.48:28830/device-manage/upgrade/alone/devicelist");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-统计任务列表个性化升级","dbc_UpgradeAloneQueryTaskCountByParam_url","http://192.168.41.48:28830/device-manage/upgrade/alone/devicecount");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-删除任务个性化升级","dbc_UpgradeAloneDeleteTask_url","http://192.168.41.48:28830/device-manage/upgrade/alone/task");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-设备列表查询个性化升级","dbc_UpgradeAloneQueryDeviceListByParam_url","http://192.168.41.48:28830/device-manage/upgrade/alone/devicelist");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values
("数据中心-设备列表统计个性化升级","dbc_UpgradeAloneQueryDeviceCountByParam_url","http://192.168.41.48:28830/device-manage/upgrade/alone/devicecount");


insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values ("定制终端升级包-文件夹路径","region_resources_folder_path","/service/upload/");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values ("定制终端个性化升级包-上传路径","patch_upload_dir","upload/");
insert into awifi_np_biz.np_biz_system_config(alias_name,param_key,param_value) values ("定制终端个性化升级包-访问url","patch_upload_url","upload/");


