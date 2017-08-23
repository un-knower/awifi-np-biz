-- alpha环境参数
insert into np_biz_system_config (alias_name, param_key, param_value) values ("数据中心-新增用户","dbc_adduser_url","http://192.168.41.44:28870/user-base/user/");
insert into np_biz_system_config (alias_name, param_key, param_value) values ("数据中心-更新用户","dbc_updateuser_url","http://192.168.41.44:28870/user-base/user/");
insert into np_biz_system_config (alias_name, param_key, param_value) values ("数据中心-主键查询用户","dbc_getuser_url","http://192.168.41.44:28870/user-base/user/");
insert into np_biz_system_config (alias_name, param_key, param_value) values ("数据中心-根据条件查询认证用户","dbc_queryAuthUserByParam_url","http://192.168.41.44:28870/user-base/userauth/");
insert into np_biz_system_config (alias_name, param_key, param_value) values ("数据中心-新增认证用户","dbc_addAuthUser_url","http://192.168.41.44:28870/user-base/userauth/");
insert into np_biz_system_config (alias_name, param_key, param_value) values ("数据中心-根据条件查询用户列表条数","dbc_getusercountbyparam_url","http://192.168.41.44:28870/user-base/user/count/");

-- beta环境参数
insert into np_biz_system_config (alias_name, param_key, param_value) values ("图片资源服务器前缀","media_image_domain","http://beta-i.51awifi.com/mersrv/");
insert into np_biz_system_config (alias_name, param_key, param_value) values ("微站域名","mws_domain","http://beta-m.51awifi.com");
insert into np_biz_system_config (alias_name, param_key, param_value) values ("接入认证-踢下线接口","auth_kickuser_url","http://192.168.212.95:8015/auth-service/auth/usermanage/kickuser");


--系统参数配置
insert into np_biz_system_config (alias_name, param_key, param_value) values ("时长认证图片上传目录","upload_dir","timebuysrv/upload/");
insert into np_biz_system_config (alias_name, param_key, param_value) values ("时长认证图片读取url","upload_url","timebuysrv/upload/");



