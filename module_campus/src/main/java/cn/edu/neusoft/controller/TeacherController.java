package cn.edu.neusoft.controller;

import cn.edu.neusoft.pojo.Clazz;
import cn.edu.neusoft.pojo.Grade;
import cn.edu.neusoft.pojo.Teacher;
import cn.edu.neusoft.service.TeacherService;
import cn.edu.neusoft.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 赵林阳
 * @Date 2023/7/5 16:48
 * @Version 1.0
 */
@Api(tags = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @ApiOperation("获取教师信息,分页带条件")
    @GetMapping("/getTeachers/{pn}/{pageSize}")
    public Result<Object> getTeachers(@ApiParam("当前页码") @PathVariable("pn") Integer pn,
                                      @ApiParam("每页显示的记录数") @PathVariable("pageSize") Integer pageSize,
                                      @ApiParam("请求参数") String name, @ApiParam("请求参数") String clazzName)
    {
        Page<Teacher> page = teacherService.page(new Page<>(pn, pageSize),
                new LambdaQueryWrapper<Teacher>().like(StrUtil.isNotBlank(name), Teacher::getName, name)
                        .like(StrUtil.isNotBlank(clazzName), Teacher::getClazzName, clazzName).orderByDesc(Teacher::getId));
        return Result.ok(page);
    }

    //    判断请求体中是否有id，判断添加或修改
    @ApiOperation("判断请求体中是否有id，判断添加或修改")
    @PostMapping("/saveOrUpdateTeacher")
    public Result<Object> saveOrUpdateTeacher(@RequestBody Teacher teacher){
//        判断请求体中是否有id，有就是修改。否则就是添加。
        Integer id =teacher.getId();
        if(id != null){
            teacherService.update(teacher,new LambdaQueryWrapper<Teacher>().eq(Teacher::getId,id));
        }else {
            teacherService.save(teacher);
        }
        return Result.ok();
    }

//    删除和批量删除
//    pn 页数
//    ids 删除id的集合
    @ApiOperation("删除和批量删除")
    @DeleteMapping("/deleteTeacher")
    public Result<Object> deleteTeacher(@ApiParam("删除id的集合") @RequestBody List<Integer> ids){
        if (ids.size() == 1) {
            //单条记录删除
            teacherService.removeById(ids.get(0));
        }else
            //批量删除
            teacherService.removeBatchByIds(ids);
        return Result.ok("删除成功");
    }

}
