package cn.edu.neusoft.controller;

import cn.edu.neusoft.pojo.Clazz;
import cn.edu.neusoft.pojo.Grade;
import cn.edu.neusoft.service.ClazzService;
import cn.edu.neusoft.service.GradeService;
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


//    判断请求体中是否有id，保存或更新班级
    @ApiOperation("判断请求体中是否有id，保存或更新班级")
    @PostMapping("/saveOrUpdateClazz")
    public Result<Object> saveOrUpdateClazz(@ApiParam("用实体类封装请求体中的json数据") @RequestBody Clazz clazz){
//        判断请求体中是否有id，有就是修改。否则就是添加。
        Integer id =clazz.getId();
        if(id == null){
            //增加
            clazzService.save(clazz);
        }else {
            clazzService.update(clazz, new QueryWrapper<Clazz>().eq("id",clazz.getId()));
        }
        return Result.ok();
    }

//    删除和批量删除
//    pn 页数
//    ids 删除id的集合
    @ApiOperation("删除和批量删除班级信息")
    @DeleteMapping("/deleteClazz")
    public Result<Object> deleteClazz(@ApiParam("删除id的集合") @RequestBody List<Integer> ids){
        if (ids.size() == 1) {
            //单条记录删除
            clazzService.removeById(ids.get(0));
        }else
            //批量删除
            clazzService.removeBatchByIds(ids);
        return Result.ok("删除成功");
    }

//    获取班级
    @ApiOperation("获取班级")
    @GetMapping("/getClazzs")
    public Result<Object> getClazzs(){
        List<Clazz> clazzes = clazzService.list(null);

        return Result.ok(clazzes);
    }




}
