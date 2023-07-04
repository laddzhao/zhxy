package cn.edu.neusoft.service.impl;

import cn.edu.neusoft.pojo.Admin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.neusoft.pojo.Student;
import cn.edu.neusoft.service.StudentService;
import cn.edu.neusoft.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 赵林阳
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2023-06-21 11:18:59
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

    @Resource
    private StudentMapper studentMapper;

    @Override
    public Student selectStudentByUsernameAndPwd(String username, String password) {
        return studentMapper.selectOne(
                new LambdaQueryWrapper<Student>().eq(Student::getName,username).eq(Student::getPassword,password)
        );
    }

    @Override
    public Student selectStudentById(Long userId) {
        return studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getId,userId));
    }
}




