select DISTINCT TASK_DEF_KEY_,NAME_ from act_hi_taskinst where PROC_INST_ID_=:proceInsId 
and DELETE_REASON_ ='completed'

