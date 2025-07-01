package com.kevin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.common.Result;
import com.kevin.entity.Admin;
import com.kevin.entity.Answer;
import com.kevin.entity.AnswerSheet;
import com.kevin.entity.Question;
import com.kevin.entity.QuestionOption;
import com.kevin.service.AdminService;
import com.kevin.service.AnswerService;
import com.kevin.service.AnswerSheetService;
import com.kevin.service.QuestionService;
import com.kevin.service.QuestionOptionService;
import com.kevin.vo.LoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/api/admin")
@Tag(name = "管理员管理", description = "管理员登录、信息管理等相关接口")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AnswerSheetService answerSheetService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionOptionService questionOptionService;

    /**
     * 管理员登录
     */
    @PostMapping("/login")
    @Operation(summary = "管理员登录", description = "管理员通过用户名和密码登录系统")
    public Result login(@RequestBody LoginVo loginVo) {
        try {
            Admin admin = adminService.login(loginVo.getUsername(), loginVo.getPassword());
            if (admin != null) {
                return Result.success(admin);
            } else {
                return Result.error("用户名或密码错误");
            }
        } catch (Exception e) {
            return Result.error("登录失败：" + e.getMessage());
        }
    }

    /**
     * 获取管理员信息
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "获取管理员信息", description = "根据管理员ID获取管理员详细信息")
    public Result getAdminInfo(
            @Parameter(description = "管理员ID", required = true) @PathVariable Long id) {
        try {
            Admin admin = adminService.getById(id);
            if (admin != null) {
                return Result.success(admin);
            } else {
                return Result.error("管理员不存在");
            }
        } catch (Exception e) {
            return Result.error("获取信息失败：" + e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("/changePassword")
    @Operation(summary = "修改密码", description = "管理员修改登录密码")
    public Result changePassword(
            @Parameter(description = "管理员ID", required = true) @RequestParam Long adminId,
            @Parameter(description = "原密码", required = true) @RequestParam String oldPassword,
            @Parameter(description = "新密码", required = true) @RequestParam String newPassword) {
        try {
            boolean success = adminService.changePassword(adminId, oldPassword, newPassword);
            if (success) {
                return Result.success("密码修改成功");
            } else {
                return Result.error("原密码错误");
            }
        } catch (Exception e) {
            return Result.error("密码修改失败：" + e.getMessage());
        }
    }

    /**
     * 分页查询用户提交的问卷
     */
    @GetMapping("/answerSheets")
    @Operation(summary = "查询用户提交的问卷", description = "分页查询用户提交的问卷列表")
    public Result getAnswerSheets(
            @Parameter(description = "页码", required = true) @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小", required = true) @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "问卷ID") @RequestParam(required = false) Long questionnaireId,
            @Parameter(description = "状态") @RequestParam(required = false) String status,
            @Parameter(description = "开始时间") @RequestParam(required = false) String startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) String endTime) {
        try {
            Page<AnswerSheet> pageParam = new Page<>(page, size);
            QueryWrapper<AnswerSheet> queryWrapper = getAnswerSheetQueryWrapper(questionnaireId, status, startTime);
            if (endTime != null && !endTime.isEmpty()) {
                queryWrapper.le("submit_time", endTime);
            }
            
            queryWrapper.orderByDesc("submit_time");
            
            Page<AnswerSheet> result = answerSheetService.page(pageParam, queryWrapper);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("查询失败：" + e.getMessage());
        }
    }

    private static QueryWrapper<AnswerSheet> getAnswerSheetQueryWrapper(Long questionnaireId, String status, String startTime) {
        QueryWrapper<AnswerSheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0);

        if (questionnaireId != null) {
            queryWrapper.eq("questionnaire_id", questionnaireId);
        }
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq("status", status);
        }
        if (startTime != null && !startTime.isEmpty()) {
            queryWrapper.ge("submit_time", startTime);
        }
        return queryWrapper;
    }

    /**
     * 查看问卷详情
     */
    @GetMapping("/answerSheets/{id}")
    @Operation(summary = "查看问卷详情", description = "查看指定问卷的详细答案")
    public Result getAnswerSheetDetail(
            @Parameter(description = "答卷ID", required = true) @PathVariable Long id) {
        try {
            // 获取答卷信息
            AnswerSheet answerSheet = answerSheetService.getById(id);
            if (answerSheet == null) {
                return Result.error("答卷不存在");
            }
            // 获取答案列表
            QueryWrapper<Answer> answerQuery = new QueryWrapper<>();
            answerQuery.eq("answer_sheet_id", id);
            List<Answer> answers = answerService.list(answerQuery);

            // 合并同一题目的所有答案
            Map<Long, List<Answer>> answerMap = answers.stream()
                .collect(Collectors.groupingBy(Answer::getQuestionId));

            List<Map<String, Object>> answerDetails = new ArrayList<>();
            for (Map.Entry<Long, List<Answer>> entry : answerMap.entrySet()) {
                Map<String, Object> detail = new HashMap<>();
                Question question = questionService.getById(entry.getKey());
                // 只显示原始题干
                detail.put("questionTitle", question != null ? question.getContent() : "");

                // 合并多选题答案
                String answerContent = entry.getValue().stream()
                    .map(ans -> {
                        if (ans.getOptionId() != null) {
                            QuestionOption option = questionOptionService.getById(ans.getOptionId());
                            return option != null ? option.getContent() : "";
                        } else if (ans.getTextAnswer() != null) {
                            return ans.getTextAnswer();
                        } else if (ans.getNumberAnswer() != null) {
                            return ans.getNumberAnswer().toString();
                        }
                        return "";
                    })
                    .filter(s -> s != null && !s.isEmpty())
                    .collect(Collectors.joining("，")); // 用顿号分隔
                detail.put("answerContent", answerContent);
                answerDetails.add(detail);
            }
            Map<String, Object> result = new HashMap<>();
            result.put("answerSheet", answerSheet);
            result.put("answers", answerDetails);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取详情失败：" + e.getMessage());
        }
    }

    /**
     * 删除问卷
     */
    @DeleteMapping("/answerSheets/{id}")
    @Operation(summary = "删除问卷", description = "删除指定的问卷（软删除）")
    public Result deleteAnswerSheet(
            @Parameter(description = "答卷ID", required = true) @PathVariable Long id) {
        try {
            AnswerSheet answerSheet = answerSheetService.getById(id);
            if (answerSheet == null) {
                return Result.error("答卷不存在");
            }
            
            // 软删除答卷
            answerSheet.setIsDeleted(1);
            answerSheetService.updateById(answerSheet);
            
            // 软删除相关答案
            QueryWrapper<Answer> answerQuery = new QueryWrapper<>();
            answerQuery.eq("answer_sheet_id", id);
            List<Answer> answers = answerService.list(answerQuery);
            
            for (Answer answer : answers) {
                answer.setIsDeleted(1);
                answerService.updateById(answer);
            }
            
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除问卷
     */
    @DeleteMapping("/answerSheets/batch")
    @Operation(summary = "批量删除问卷", description = "批量删除指定的问卷")
    public Result batchDeleteAnswerSheets(
            @Parameter(description = "答卷ID列表", required = true) @RequestBody List<Long> ids) {
        try {
            for (Long id : ids) {
                AnswerSheet answerSheet = answerSheetService.getById(id);
                if (answerSheet != null) {
                    // 软删除答卷
                    answerSheet.setIsDeleted(1);
                    answerSheetService.updateById(answerSheet);
                    
                    // 软删除相关答案
                    QueryWrapper<Answer> answerQuery = new QueryWrapper<>();
                    answerQuery.eq("answer_sheet_id", id);
                    List<Answer> answers = answerService.list(answerQuery);
                    
                    for (Answer answer : answers) {
                        answer.setIsDeleted(1);
                        answerService.updateById(answer);
                    }
                }
            }
            
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.error("批量删除失败：" + e.getMessage());
        }
    }

    /**
     * 获取问卷统计信息
     */
    @GetMapping("/answerSheets/stats")
    @Operation(summary = "获取问卷统计信息", description = "获取问卷提交的统计信息")
    public Result getAnswerSheetStats(
            @Parameter(description = "问卷ID") @RequestParam(required = false) Long questionnaireId) {
        try {
            QueryWrapper<AnswerSheet> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_deleted", 0);
            
            if (questionnaireId != null) {
                queryWrapper.eq("questionnaire_id", questionnaireId);
            }
            
            // 总提交数
            long totalCount = answerSheetService.count(queryWrapper);
            
            // 今日提交数
            QueryWrapper<AnswerSheet> todayQuery = new QueryWrapper<>();
            todayQuery.eq("is_deleted", 0);
            if (questionnaireId != null) {
                todayQuery.eq("questionnaire_id", questionnaireId);
            }
            todayQuery.ge("submit_time", java.time.LocalDate.now().toString());
            long todayCount = answerSheetService.count(todayQuery);
            
            // 已完成数
            QueryWrapper<AnswerSheet> completedQuery = new QueryWrapper<>();
            completedQuery.eq("is_deleted", 0);
            completedQuery.eq("status", "COMPLETED");
            if (questionnaireId != null) {
                completedQuery.eq("questionnaire_id", questionnaireId);
            }
            long completedCount = answerSheetService.count(completedQuery);
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalCount", totalCount);
            stats.put("todayCount", todayCount);
            stats.put("completedCount", completedCount);
            
            return Result.success(stats);
        } catch (Exception e) {
            return Result.error("获取统计信息失败：" + e.getMessage());
        }
    }

    /**
     * 统计问卷下所有选择题各选项的选择次数和占比
     */
    @GetMapping("/answerSheets/optionStats")
    @Operation(summary = "统计选择题选项分布", description = "统计问卷下所有选择题各选项的选择次数和占比")
    public Result getOptionStats(@RequestParam Long questionnaireId) {
        // 1. 查询该问卷下所有选择题
        List<Question> questions = questionService.listByQuestionnaireId(questionnaireId)
            .stream()
            .filter(q -> "SINGLE_CHOICE".equals(q.getType()) || "MULTIPLE_CHOICE".equals(q.getType()))
            .collect(Collectors.toList());

        List<Map<String, Object>> result = new ArrayList<>();
        for (Question q : questions) {
            // 2. 查询该题所有选项
            List<QuestionOption> options = questionOptionService.listByQuestionId(q.getId());
            // 3. 查询该题所有答案
            List<Answer> answers = answerService.list(
                new QueryWrapper<Answer>().eq("question_id", q.getId()).eq("is_deleted", 0)
            );
            int total = answers.size();
            Map<Long, Long> optionCount = answers.stream()
                .filter(a -> a.getOptionId() != null)
                .collect(Collectors.groupingBy(Answer::getOptionId, Collectors.counting()));

            List<Map<String, Object>> optionStats = new ArrayList<>();
            for (QuestionOption opt : options) {
                long count = optionCount.getOrDefault(opt.getId(), 0L);
                double percent = total == 0 ? 0 : (count * 1.0 / total);
                Map<String, Object> optMap = new HashMap<>();
                optMap.put("optionId", opt.getId());
                optMap.put("content", opt.getContent());
                optMap.put("count", count);
                optMap.put("percent", percent);
                optionStats.add(optMap);
            }
            Map<String, Object> qMap = new HashMap<>();
            qMap.put("questionId", q.getId());
            qMap.put("questionTitle", q.getContent());
            qMap.put("options", optionStats);
            result.add(qMap);
        }
        return Result.success(result);
    }
} 