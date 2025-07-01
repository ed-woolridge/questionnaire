
CREATE DATABASE  `questionnaire`;
use questionnaire;
-- ----------------------------
-- Chat2DB export data , export time: 2025-07-01 10:26:19
-- ----------------------------
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for table admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码（加密存储）',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '管理员状态：ACTIVE(正常)、INACTIVE(禁用)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` (`id`,`username`,`password`,`real_name`,`phone`,`email`,`status`,`create_time`,`update_time`,`last_login_time`,`is_deleted`)  VALUES ('1','admin','e10adc3949ba59abbe56e057f20f883e','系统管理员','13800138000','admin@example.com','ACTIVE','2025-06-25 08:24:59','2025-06-25 08:28:19','2025-07-01 10:00:44',0);
-- ----------------------------
-- Table structure for table answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `answer_sheet_id` bigint NOT NULL COMMENT '答卷ID',
  `question_id` bigint NOT NULL COMMENT '问题ID',
  `option_id` bigint DEFAULT NULL COMMENT '选项ID（选择题）',
  `text_answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '文本答案（填空题）',
  `number_answer` decimal(10,2) DEFAULT NULL COMMENT '数字答案',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_answer_sheet_id` (`answer_sheet_id`),
  KEY `idx_question_id` (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='答案表';

-- ----------------------------
-- Records of answer
-- ----------------------------
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('1','1','1',NULL,'加利福尼亚',NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('2','1','2','1',NULL,NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('3','1','3','4',NULL,NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('4','1','4','9',NULL,NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('5','1','5','12',NULL,NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('6','1','7','22',NULL,NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('7','1','10','27',NULL,NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('8','1','10','28',NULL,NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('9','1','10','29',NULL,NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('10','1','11','36',NULL,NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('11','1','13','40',NULL,NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('12','1','13','41',NULL,NULL,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('13','1','14',NULL,NULL,5.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('14','1','15',NULL,NULL,3.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('15','1','16',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('16','1','17',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('17','1','18',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('18','1','19',NULL,NULL,11.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('19','1','20',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('20','1','21',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('21','1','22',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('22','1','23',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('23','1','24',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('24','1','25',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('25','1','26',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('26','1','27',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('27','1','28',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('28','1','29',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('29','1','30',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('30','1','31',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('31','1','32',NULL,NULL,1.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('32','1','33',NULL,NULL,3.00,0);
INSERT INTO `answer` (`id`,`answer_sheet_id`,`question_id`,`option_id`,`text_answer`,`number_answer`,`is_deleted`)  VALUES ('33','1','34',NULL,'[黄山市, 宣城市, 广州市]',NULL,0);
-- ----------------------------
-- Table structure for table answer_sheet
-- ----------------------------
DROP TABLE IF EXISTS `answer_sheet`;
CREATE TABLE `answer_sheet` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `questionnaire_id` bigint NOT NULL COMMENT '问卷ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'IN_PROGRESS' COMMENT '答卷状态：IN_PROGRESS(填写中)、COMPLETED(已完成)、TERMINATED(已终止)',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始填写时间',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `duration` int DEFAULT NULL COMMENT '填写时长（分钟）',
  `surveyor_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查员姓名',
  `surveyor_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查员电话',
  `survey_province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查地点省份',
  `survey_city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查地点城市',
  `survey_district` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查地点县区',
  `survey_location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查地点具体位置',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_questionnaire_id` (`questionnaire_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='答卷表';

-- ----------------------------
-- Records of answer_sheet
-- ----------------------------
INSERT INTO `answer_sheet` (`id`,`questionnaire_id`,`user_id`,`status`,`start_time`,`submit_time`,`duration`,`surveyor_name`,`surveyor_phone`,`survey_province`,`survey_city`,`survey_district`,`survey_location`,`is_deleted`)  VALUES ('1','1',NULL,'IN_PROGRESS','2025-07-01 09:34:14','2025-07-01 09:34:14',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
-- ----------------------------
-- Table structure for table question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `questionnaire_id` bigint NOT NULL COMMENT '问卷ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '问题内容',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '问题类型：SINGLE_CHOICE(单选)、MULTIPLE_CHOICE(多选)、TEXT(填空)、NUMBER(数字)、SCREENING(筛选题)',
  `sort_order` int DEFAULT '0' COMMENT '排序号',
  `is_required` tinyint DEFAULT '1' COMMENT '是否必填：0-否，1-是',
  `is_screening` tinyint DEFAULT '0' COMMENT '是否为筛选题：0-否，1-是',
  `screening_condition` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '筛选题终止条件（JSON格式）',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '问题说明',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_questionnaire_id` (`questionnaire_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问题表';

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('1','1','1. 您目前的常住地（省/市/县/区/旗）','TEXT','4',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('2','1','2. 您的性别是','SINGLE_CHOICE','5',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('3','1','3. 您的年龄','SINGLE_CHOICE','6',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('4','1','4. 您日常居住生活的地方','SINGLE_CHOICE','7',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('5','1','5. 您本次出游，主要目的是','SINGLE_CHOICE','8',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('6','1','5.1 其他目的说明','TEXT','9',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('7','1','6. 您本次出游，是否住宿过夜','SINGLE_CHOICE','10',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('8','1','6.1 住宿夜数','NUMBER','11',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('9','1','6.2 主要住宿类型','SINGLE_CHOICE','12',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('10','1','7. 本次出游在我市游览的景区类型','MULTIPLE_CHOICE','13',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('11','1','8. 来我市游览的方式','SINGLE_CHOICE','14',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('12','1','8.1 其他方式说明','TEXT','15',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('13','1','9. 交通出行方式','MULTIPLE_CHOICE','16',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('14','1','10. 计划在本市游览天数','NUMBER','17',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('15','1','11. 今天是来本市的第几天','NUMBER','18',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('16','1','12. 交给旅行社或单位支付的费用','NUMBER','19',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('17','1','13. 交通费','NUMBER','20',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('18','1','13.1 飞机费用','NUMBER','21',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('19','1','13.2 火车费用','NUMBER','22',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('20','1','13.3 长途汽车费用','NUMBER','23',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('21','1','13.4 轮船费用','NUMBER','24',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('22','1','13.5 自驾/租车费用','NUMBER','25',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('23','1','14. 其他费用','NUMBER','26',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('24','1','15. 餐饮费用','NUMBER','27',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('25','1','16. 住宿费用','NUMBER','28',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('26','1','17. 门票费用','NUMBER','29',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('27','1','18. 购物费用','NUMBER','30',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('28','1','18.1 文创产品费用','NUMBER','31',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('29','1','19. 文化艺术/娱乐费用','NUMBER','32',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('30','1','20. 医疗康养费用','NUMBER','33',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('31','1','21. 市内交通费用','NUMBER','34',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('32','1','22. 其他费用','NUMBER','35',0,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('33','1','23. 本次出游在本省共游览了几个市','NUMBER','36',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('34','1','24. 游览城市名称（多个用逗号分隔）','TEXT','37',1,0,NULL,NULL,0);
-- ----------------------------
-- Table structure for table question_option
-- ----------------------------
DROP TABLE IF EXISTS `question_option`;
CREATE TABLE `question_option` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `question_id` bigint NOT NULL COMMENT '问题ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '选项内容',
  `show_input` tinyint DEFAULT NULL COMMENT '选项弹出是否显示（0:false，1:true）',
  `sort_order` int DEFAULT '0' COMMENT '排序号',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_question_id` (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问题选项表';

-- ----------------------------
-- Records of question_option
-- ----------------------------
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('1','1','A.男',NULL,'1',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('2','1','B.女',NULL,'2',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('3','2','A.未满18岁',NULL,'1',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('4','2','B.18-24岁',NULL,'2',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('5','2','C.25-35岁',NULL,'3',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('6','2','D.36-45岁',NULL,'4',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('7','2','E.46-60岁',NULL,'5',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('8','2','F.60岁以上',NULL,'6',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('9','3','A.城镇',NULL,'1',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('10','3','B.乡村',NULL,'2',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('11','5','A.观光/休闲',NULL,'1',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('12','5','B.探亲访友',NULL,'2',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('13','5','C.购物',NULL,'3',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('14','5','D.文化艺术欣赏/娱乐',NULL,'4',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('15','5','E.会议/培训/展览',NULL,'5',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('16','5','F.商务活动',NULL,'6',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('17','5','G.研学/体育/科技交流',NULL,'7',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('18','5','H.医疗康养',NULL,'8',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('19','5','I.宗教活动',NULL,'9',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('20','5','J.其他____（请注明）',1,'10',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('21','7','A.是',NULL,'1',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('22','7','B.否（选"否"免填本题后续内容）',NULL,'2',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('23','9','A.住在酒店设施',NULL,'1',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('24','9','B.住在宾馆、民宿客栈、家庭旅馆、疗养院、游船等经营性住宿设施',NULL,'2',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('25','9','C.住在亲友家、自有休闲度假居所（房屋）',NULL,'3',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('26','9','D.住在自驾房车、露营地等其他住宿设施',NULL,'4',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('27','10','A.A级旅游景区（文化和旅游部门评定的5A级、4A级、3A级、2A级等旅游景区）',NULL,'1',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('28','10','B.文化场馆（博物馆、图书馆、美术馆、影院剧院等文化场馆）',NULL,'2',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('29','10','C.音乐节、演唱会、展览及节庆活动等相关区域',NULL,'3',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('30','10','D.城市公园、游乐园（主题公园）、绿道等休闲娱乐类区域',NULL,'4',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('31','10','E.商场、步行街、集市等购物消费类区域',NULL,'5',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('32','10','F.革命活动地、战役发生地、纪念馆、爱国主义教育示范基地等红色旅游类区域',NULL,'6',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('33','10','G.传统村落、农（牧、渔）家乐、农业田园、牧场、渔场等乡村旅游区域',NULL,'7',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('34','10','H.以上各类型景区（景点）都未游览',NULL,'8',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('35','11','A.单位（安排）组织',NULL,'1',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('36','11','B.个人、家庭或亲朋结伴自由出行',NULL,'2',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('37','11','C.通过旅行社组团出行',NULL,'3',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('38','11','D.其他____（请注明）',NULL,'4',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('39','13','A.乘坐（飞机、火车、轮船、汽车等）公共交通工具出行',NULL,'1',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('40','13','B.自驾车出行',NULL,'2',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('41','13','C.租车自驾出行',NULL,'3',0);
INSERT INTO `question_option` (`id`,`question_id`,`content`,`show_input`,`sort_order`,`is_deleted`)  VALUES ('42','13','D.骑行、徒步等其他出行方式',NULL,'4',0);
-- ----------------------------
-- Table structure for table questionnaire
-- ----------------------------
DROP TABLE IF EXISTS `questionnaire`;
CREATE TABLE `questionnaire` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '问卷标题',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '问卷描述',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'TOURISM_SURVEY' COMMENT '问卷类型：TOURISM_SURVEY(旅游调查问卷)',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'DRAFT' COMMENT '问卷状态：DRAFT(草稿)、PUBLISHED(已发布)、CLOSED(已关闭)',
  `valid_start_time` datetime DEFAULT NULL COMMENT '有效期开始时间',
  `valid_end_time` datetime DEFAULT NULL COMMENT '有效期结束时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问卷表';

-- ----------------------------
-- Records of questionnaire
-- ----------------------------
INSERT INTO `questionnaire` (`id`,`title`,`description`,`type`,`status`,`valid_start_time`,`valid_end_time`,`create_time`,`update_time`,`create_by`,`is_deleted`)  VALUES ('1','地方接待国内游客抽样调查问卷','为了不断提高我们的旅游接待水平，使您得到质价相符的服务，请您帮助填写这份调查问卷。','TOURISM_SURVEY','PUBLISHED','2025-06-25 08:24:59','2026-06-25 08:24:59','2025-06-25 08:24:59','2025-06-25 08:24:59','1',0);
INSERT INTO `questionnaire` (`id`,`title`,`description`,`type`,`status`,`valid_start_time`,`valid_end_time`,`create_time`,`update_time`,`create_by`,`is_deleted`)  VALUES ('2','地方接待国内游客抽样调查问卷','为了不断提高我们的旅游接待水平，使您得到质价相符的服务，请您帮助填写这份调查问卷。','TOURISM_SURVEY','PUBLISHED','2025-06-25 08:28:19','2026-06-25 08:28:19','2025-06-25 08:28:19','2025-06-25 08:28:19','1',0);
-- ----------------------------
-- Table structure for table sms_code
-- ----------------------------
DROP TABLE IF EXISTS `sms_code`;
CREATE TABLE `sms_code` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码类型：LOGIN(登录)、REGISTER(注册)、QUESTIONNAIRE(问卷验证)',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'UNUSED' COMMENT '验证码状态：UNUSED(未使用)、USED(已使用)、EXPIRED(已过期)',
  `send_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_phone_type` (`phone`,`type`),
  KEY `idx_expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信验证码表';

-- ----------------------------
-- Records of sms_code
-- ----------------------------
-- ----------------------------
-- Table structure for table user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'USER' COMMENT '用户角色：ADMIN(管理员)、USER(普通用户)',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'UNVERIFIED' COMMENT '用户状态：UNVERIFIED(未认证)、VERIFIED(已认证)、BLOCKED(已封禁)',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '常驻地省份',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '常驻地城市',
  `district` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '常驻地县区',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
