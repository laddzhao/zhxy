package cn.edu.neusoft.controller;

import cn.edu.neusoft.pojo.Admin;
import cn.edu.neusoft.pojo.Student;
import cn.edu.neusoft.service.AdminService;
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
 * @Date 2023/7/8 15:53
 * @Version 1.0
 */
@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Resource
    private AdminService adminService;

        @ApiOperation("获取管理员信息,分页带条件")
        @GetMapping("/getAllAdmin/{pn}/{pageSize}")
        public Result<Object> getStudentByOpr(@ApiParam("当前页码") @PathVariable("pn") Integer pn,
                @ApiParam("每页显示的记录数") @PathVariable("pageSize") Integer pageSize,
                @ApiParam("请求参数") String name, @ApiParam("请求参数") String adminName)
        {
            Page<Admin> page = adminService.page(new Page<>(pn, pageSize), new LambdaQueryWrapper<Admin>()
                    .like(StrUtil.isNotBlank(adminName), Admin::getName, adminName).orderByDesc(Admin::getId));
            return Result.ok(page);
        }

//    判断请求体中是否有id，判断添加或修改
    @ApiOperation("判断请求体中是否有id，判断添加或修改")
    @PostMapping("/saveOrUpdateAdmin")
    public Result<Object> saveOrUpdateTeacher(@RequestBody Admin admin){
//        判断请求体中是否有id，有就是修改。否则就是添加。
        Integer id =admin.getId();
        if(id != null){
            adminService.update(admin,new LambdaQueryWrapper<Admin>().eq(Admin::getId,id));
        }else {
            adminService.save(admin);
        }
        return Result.ok();
    }

//    删除和批量删除
//    pn 页数
//    ids 删除id的集合
    @ApiOperation("删除和批量删除")
    @DeleteMapping("/deleteAdmin")
    public Result<Object> deleteTeacher(@ApiParam("删除id的集合") @RequestBody List<Integer> ids){
        if (ids.size() == 1) {
            //单条记录删除
            adminService.removeById(ids.get(0));
        }else
            //批量删除
            adminService.removeBatchByIds(ids);
        return Result.ok("删除成功");
    }


}
