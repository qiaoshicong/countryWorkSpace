
/* find(newMenuId)  */ select max(`menu_id`)+1  as value from  `sys_menu`;
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="新闻发布者信息";
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = 0 ;
/* execute(!moduleId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, 0, '新闻发布者信息', 'MenuOutlined', '','', 0,0, ${sort_number}, '');
/* execute(!moduleId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="新闻发布者信息";

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  ="发布者信息管理";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value  from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '发布者信息管理', 'MenuOutlined', '/cms/publisher/publisher',
'/cms/publisher/publisher',0, 0,${sort_number}, 'cms:publisher:Publisher:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="发布者信息管理";

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "查询发布者信息";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value  from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '查询发布者信息', '', '',
'',1,0, ${sort_number}, 'cms:publisher:Publisher:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "添加发布者信息";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '添加发布者信息', '', '', '', 1, 0,${sort_number}, 'cms:publisher:Publisher:add');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "修改发布者信息";
/* find(newMenuId)  */ select max(`menu_id`) +1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '修改发布者信息', '', '',
'', 1,0, ${sort_number}, 'cms:publisher:Publisher:update');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "删除发布者信息";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  =  ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '删除发布者信息', '', '',
'', 1, 0,${sort_number}, 'cms:publisher:Publisher:remove');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});




                                        