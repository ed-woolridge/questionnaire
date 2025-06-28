# 问卷管理系统

## 项目简介

这是一个基于Spring Boot + Vue3的问卷管理系统，主要用于管理地方接待国内游客抽样调查问卷。

## 功能特性

### 用户端功能
- 问卷填写（支持单选、多选、填空、数字等多种题型）
- 手机号验证（暂时注释，后期接入API）
- 数字输入验证
- 问卷提交

### 管理员端功能
- 管理员登录
- 问卷管理（创建、编辑、删除）
- **用户提交问卷管理**（查看、查询、删除）
- 数据导出

## 用户提交问卷管理功能

### 功能概述
管理员可以查看、查询和删除用户提交的问卷，但不能修改用户提交的内容，确保数据的完整性和真实性。

### 主要功能

#### 1. 问卷列表查看
- 分页显示所有用户提交的问卷
- 显示基本信息：ID、问卷ID、状态、提交时间、填写时长、调查员、调查地点等
- 支持多选操作

#### 2. 搜索和筛选
- 按问卷ID筛选
- 按状态筛选（填写中、已完成、已终止）
- 按提交时间范围筛选
- 支持重置搜索条件

#### 3. 统计信息
- 总提交数
- 今日提交数
- 已完成数

#### 4. 详情查看
- 查看问卷的基本信息
- 查看每个问题的详细答案
- 支持不同题型的答案展示

#### 5. 删除功能
- 单个删除：删除指定的问卷记录
- 批量删除：删除多个选中的问卷记录
- 软删除：不会真正删除数据，只是标记为已删除

### 技术实现

#### 后端接口
- `GET /api/admin/answerSheets` - 分页查询用户提交的问卷
- `GET /api/admin/answerSheets/{id}` - 查看问卷详情
- `DELETE /api/admin/answerSheets/{id}` - 删除单个问卷
- `DELETE /api/admin/answerSheets/batch` - 批量删除问卷
- `GET /api/admin/answerSheets/stats` - 获取统计信息

#### 前端页面
- 路径：`/admin/answer-sheets`
- 组件：`AnswerSheetList.vue`
- 功能：完整的CRUD操作界面

### 数据安全
- 管理员只能查看、查询和删除，不能修改用户提交的数据
- 使用软删除机制，保证数据可追溯
- 支持数据导出功能

## 技术栈

### 后端
- Spring Boot 3.x
- MyBatis Plus
- MySQL 8.0
- Swagger 3

### 前端
- Vue 3
- Element Plus
- Vite
- Vue Router

## 快速开始

### 环境要求
- JDK 17+
- Node.js 16+
- MySQL 8.0+

### 后端启动
1. 创建数据库并导入 `src/main/resources/sql/questionnaire_db.sql`
2. 修改 `src/main/resources/application.yml` 中的数据库配置
3. 运行 `QuestionnaireApplication.java`

### 前端启动
1. 进入 `frontend` 目录
2. 安装依赖：`npm install`
3. 启动开发服务器：`npm run dev`

### 管理员登录
- 用户名：admin
- 密码：123456

## 项目结构

```
questionnaire/
├── frontend/                 # 前端项目
│   ├── src/
│   │   ├── views/
│   │   │   ├── User/        # 用户端页面
│   │   │   └── Admin/       # 管理员端页面
│   │   ├── components/      # 公共组件
│   │   ├── api/            # API接口
│   │   └── router/         # 路由配置
│   └── package.json
├── src/main/java/com/kevin/
│   ├── controller/         # 控制器
│   ├── service/           # 服务层
│   ├── entity/            # 实体类
│   ├── mapper/            # 数据访问层
│   └── config/            # 配置类
└── src/main/resources/
    ├── sql/               # 数据库脚本
    └── application.yml    # 配置文件
```

## 开发说明

### 添加新的管理功能
1. 在后端添加相应的Controller、Service、Mapper
2. 在前端添加API接口
3. 创建前端页面组件
4. 配置路由
5. 更新导航菜单

### 数据验证
- 前端使用Element Plus的表单验证
- 后端使用Spring Boot的验证注解
- 数据库层面使用约束和索引

### 错误处理
- 前端统一使用Element Plus的消息提示
- 后端统一返回Result格式
- 支持详细的错误信息记录

## 部署说明

### 生产环境配置
1. 修改数据库连接配置
2. 配置日志级别
3. 设置跨域配置
4. 配置静态资源路径

### 性能优化
- 使用分页查询减少数据传输
- 添加数据库索引优化查询
- 使用缓存减少重复查询
- 前端使用懒加载和虚拟滚动

## 维护说明

### 数据备份
- 定期备份数据库
- 保留用户提交的原始数据
- 记录操作日志

### 系统监控
- 监控系统性能
- 记录错误日志
- 监控数据增长趋势

## 联系方式

如有问题或建议，请联系开发团队。 