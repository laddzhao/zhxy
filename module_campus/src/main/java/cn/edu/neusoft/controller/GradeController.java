package cn.edu.neusoft.controller;


import cn.edu.neusoft.pojo.Grade;
import cn.edu.neusoft.service.GradeService;
import cn.edu.neusoft.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

//    页数显示和模糊查询
//    pn 页数
//    pageSize 每页显示记录数
//    gradeName 模糊查询的条件
    @Resource
    private GradeService gradeService;
    @ApiOperation("页数显示和模糊查询")
    @GetMapping("/getGrades/{pn}/{pageSize}")
    public Result<Object> getGrades(@ApiParam("当前页数") @PathVariable("pn")Integer pn,
                                    @ApiParam("每页显示记录数")@PathVariable("pageSize")Integer pageSize,
                                    @ApiParam("模糊查询的条件")String gradeName){

        Page<Grade> page = gradeService.page(new Page<>(pn,pageSize),new LambdaQueryWrapper<Grade>().
                like(StrUtil.isNotBlank(gradeName),Grade::getName,gradeName).orderByDesc(Grade::getId));

        return Result.ok(page);
    }


//    删除和批量删除
//    pn 页数
//    ids 删除id的集合
    @ApiOperation("删除和批量删除")
    @DeleteMapping("/deleteGrade")
    public Result<Object> deleteGrade(@ApiParam("删除id的集合") @RequestBody List<Integer> ids){
        if (ids.size() == 1) {
            //单条记录删除
            gradeService.removeById(ids.get(0));
        }else
            //批量删除
            gradeService.removeBatchByIds(ids);
        return Result.ok("删除成功");
    }



//    判断请求体中是否有id，判断添加或修改
    @ApiOperation("判断请求体中是否有id，判断添加或修改")
    @PostMapping("/saveOrUpdateGrade")
    public Result<Object> saveOrUpdateGrade(@RequestBody Grade grade){
//        判断请求体中是否有id，有就是修改。否则就是添加。
        Integer id =grade.getId();
        if(id != null){
            gradeService.update(grade,new LambdaQueryWrapper<Grade>().eq(Grade::getId,id));
        }else {
            gradeService.save(grade);
        }
        return Result.ok();

    }
}
