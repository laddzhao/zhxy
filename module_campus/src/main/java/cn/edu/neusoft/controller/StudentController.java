package cn.edu.neusoft.controller;

import cn.edu.neusoft.pojo.Student;
import cn.edu.neusoft.pojo.Teacher;
import cn.edu.neusoft.service.StudentService;
import cn.edu.neusoft.service.TeacherService;
import cn.edu.neusoft.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
 * @Date 2023/7/8 15:36
 * @Version 1.0
 */
@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Resource
    private StudentService studentService;

    @ApiOperation("获取学生信息,分页带条件")
    @GetMapping("/getStudentByOpr/{pn}/{pageSize}")
    public Result<Object> getStudentByOpr(@ApiParam("当前页码") @PathVariable("pn") Integer pn,
                                      @ApiParam("每页显示的记录数") @PathVariable("pageSize") Integer pageSize,
                                      @ApiParam("请求参数") String name, @ApiParam("请求参数") String clazzName)
    {
        Page<Student> page = studentService.page(new Page<>(pn, pageSize),
                new LambdaQueryWrapper<Student>().like(StrUtil.isNotBlank(name), Student::getName, name)
                        .like(StrUtil.isNotBlank(clazzName), Student::getClazzName, clazzName)
                        .orderByDesc(Student::getId));

        return Result.ok(page);
    }

//    判断请求体中是否有id，判断添加或修改
    @ApiOperation("判断请求体中是否有id，判断添加或修改")
    @PostMapping("/addOrUpdateStudent")
    public Result<Object> saveOrUpdateTeacher(@RequestBody Student student){
//        判断请求体中是否有id，有就是修改。否则就是添加。
        Integer id =student.getId();
        if(id != null){
            studentService.update(student,new LambdaQueryWrapper<Student>().eq(Student::getId,id));
        }else {
            studentService.save(student);
        }
        return Result.ok();
    }

//    删除和批量删除
//    pn 页数
//    ids 删除id的集合
    @ApiOperation("删除和批量删除")
    @DeleteMapping("/delStudentById")
    public Result<Object> deleteTeacher(@ApiParam("删除id的集合") @RequestBody List<Integer> ids){
        if (ids.size() == 1) {
            //单条记录删除
            studentService.removeById(ids.get(0));
        }else
            //批量删除
            studentService.removeBatchByIds(ids);
        return Result.ok("删除成功");
    }


}
