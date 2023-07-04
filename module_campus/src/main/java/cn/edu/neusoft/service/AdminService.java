package cn.edu.neusoft.service;

import cn.edu.neusoft.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import jdk.internal.dynalink.support.AutoDiscovery;

/**
* @author 赵林阳
* @description 针对表【tb_admin】的数据库操作Service
* @createDate 2023-06-21 11:18:23
*/
public interface AdminService extends IService<Admin> {

    Admin selectAdminByUsernameAndPwd(String username, String password);

    Admin selectAdminById(Long id);
}
