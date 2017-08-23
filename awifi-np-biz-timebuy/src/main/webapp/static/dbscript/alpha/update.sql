-- alpha环境，awifi_np_biz库，短信网关-发送短信url
update awifi_np_biz.np_biz_system_config set param_value="http://192.168.41.53/sms-service/sms/send" where param_key="sms.gateway.sendmsg.url";

-- 网络放通日志表，token长度修改
alter table awifiopms.wifi_record modify token varchar(50) comment 'token';

