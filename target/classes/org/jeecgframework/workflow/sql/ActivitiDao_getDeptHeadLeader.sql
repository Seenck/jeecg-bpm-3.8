select username from  t_s_base_user u ,t_s_user_org uo 
where u.id=uo.user_id and uo.org_id in(
	select ID from t_s_depart where id in (
select parentdepartid from t_s_depart where id in (
select org_id from t_s_user_org where user_id=(select ID from t_s_base_user where username=:username)))
)