
alter table student_collect_notes add index key_sid_pcid(student_id,package_class_id) USING BTREE;

alter table student_collect_notes drop note_id;

alter table student_collect_notes drop index key_sid_nid;

alter table teacher_class add column del_flag bit default 0;

alter table package_info add column teacher_class_id bigint unsigned default null comment '教师班级关系id';

alter table teacher_class drop index ukey_tid_cid;

alter table teacher_class add index key_tid_cid(teacher_id,class_id) USING BTREE;