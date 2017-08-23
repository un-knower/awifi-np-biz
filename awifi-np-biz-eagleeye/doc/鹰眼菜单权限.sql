-- 接口注册 鹰眼服务
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_EAGLEEYE", "显示接口", "/eagleeyesrv/view/{templatecode}:GET", "/eagleeyesrv/view/{templatecode}", "GET", "1", "1");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_EAGLEEYE", "菜单接口", "/eagleeyesrv/menus:GET", "/eagleeye/menus", "GET", "1", "1");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_EAGLEEYE", "权限接口", "/eagleeyesrv/permission/check:GET", "/eagleeyesrv/permission/check", "GET", "1", "1");
insert into awifi_np_admin.np_interface(service_code,interface_name,interface_code,interface_url,interface_method,ifcheck,status)
values ("S_EAGLEEYE", "安全接口", "/eagleeyesrv/security/check:GET", "/eagleeyesrv/security/check", "GET", "1", "1");

-- 权限（接口）表 鹰眼服务
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(110000,'S_EAGLEEYE','显示接口','/eagleeyesrv/view/{templatecode}:GET',110000);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(110010,'S_EAGLEEYE','菜单接口','/eagleeyesrv/menus:GET',110010);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(110020,'S_EAGLEEYE','权限接口','/eagleeyesrv/permission/check:GET',100020);
insert into awifi_np_biz.np_biz_permission(id,service_code,name,code,order_no) values(110030,'S_EAGLEEYE','安全接口','/eagleeyesrv/security/check:GET',100030);


-- 权限（接口）表 鹰眼服务
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,110000);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,110010);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,110020);
insert into awifi_np_biz.np_biz_role_permission(role_id,permission_id) values(1,110030);





