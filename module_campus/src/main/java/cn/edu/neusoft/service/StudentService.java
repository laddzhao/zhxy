package cn.edu.neusoft.service;

import cn.edu.neusoft.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 赵林阳
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2023-06-21 11:18:59
*/
public interface StudentService extends IService<Student> {

    Student selectStudentByUsernameAndPwd(String username, String password);

    Student selectStudentById(Long userId);
}
