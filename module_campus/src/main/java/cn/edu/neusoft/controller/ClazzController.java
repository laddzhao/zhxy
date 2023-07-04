package cn.edu.neusoft.controller;

import cn.edu.neusoft.pojo.Clazz;
import cn.edu.neusoft.pojo.Grade;
import cn.edu.neusoft.service.ClazzService;
import cn.edu.neusoft.service.GradeService;
import cn.edu.neusoft.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author 赵林阳
 * @Date 2023/7/4 15:29
 * @Version 1.0
 */
@Api(tags = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

//    页数显示和模糊查询
//    pn 页数
//    pageSize 每页显示记录数
//    gradeName 模糊查询的条件
    @Resource
    private ClazzService clazzService;

    @ApiOperation("根据分页带条件查询班级信息")
    @GetMapping("/getClazzsByOpr/{pn}/{pageSize}")
    public Result<Object> getClazzsByOpr(@ApiParam("当前页数") @PathVariable("pn")Integer pn,
                                         @ApiParam("每页显示记录数")@PathVariable("pageSize")Integer pageSize,
                                         @ApiParam("模糊查询的条件封装") Clazz clazz){

        String gradeName = clazz.getGradeName();
        String name = clazz.getName();
        LambdaQueryWrapper<Clazz> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StrUtil.isNotBlank(gradeName), Clazz::getGradeName, gradeName).
                like(StrUtil.isNotBlank(name), Clazz::getGradeName, name).orderByDesc(Clazz::getId);
        Page<Clazz> page = clazzService.page(new Page<>(pn, pageSize), lambdaQueryWrapper);
        return Result.ok(page);
    }

}
