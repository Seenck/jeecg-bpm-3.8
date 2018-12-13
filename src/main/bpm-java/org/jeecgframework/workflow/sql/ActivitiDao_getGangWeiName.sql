select departname from t_s_depart where id in (
select org_id from t_s_user_org where user_id=(select id from t_s_base_user where username=:username)
)