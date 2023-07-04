package cn.edu.neusoft.service.impl;

import cn.edu.neusoft.pojo.Student;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.neusoft.pojo.Teacher;
import cn.edu.neusoft.service.TeacherService;
import cn.edu.neusoft.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 赵林阳
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2023-06-21 11:19:02
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public Teacher selectTeacherByUsernameAndPwd(String username, String password) {
        return teacherMapper.selectOne(
                new LambdaQueryWrapper<Teacher>().eq(Teacher::getName,username).eq(Teacher::getPassword,password)
        );
    }

    @Override
    public Teacher selectTeacherById(Long userId) {
        return teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>().eq(Teacher::getId,userId));
    }


}




