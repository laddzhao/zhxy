package cn.edu.neusoft.service;

import cn.edu.neusoft.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 赵林阳
* @description 针对表【tb_teacher】的数据库操作Service
* @createDate 2023-06-21 11:19:02
*/
public interface TeacherService extends IService<Teacher> {

    Teacher selectTeacherByUsernameAndPwd(String username, String password);

    Teacher selectTeacherById(Long userId);
}
