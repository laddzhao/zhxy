package cn.edu.neusoft.service.impl;

import cn.edu.neusoft.pojo.Teacher;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.neusoft.pojo.Admin;
import cn.edu.neusoft.service.AdminService;
import cn.edu.neusoft.mapper.AdminMapper;
import kotlin.UByte;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 赵林阳
* @description 针对表【tb_admin】的数据库操作Service实现
* @createDate 2023-06-21 11:18:23
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin selectAdminByUsernameAndPwd(String username, String password) {
        return adminMapper.selectOne(
                new LambdaQueryWrapper<Admin>().eq(Admin::getName,username).eq(Admin::getPassword,password)
        );
    }

    @Override
    public Admin selectAdminById(Long id) {
        return adminMapper.selectOne(new LambdaQueryWrapper<Admin>().eq(Admin::getId,id));
    }


}




