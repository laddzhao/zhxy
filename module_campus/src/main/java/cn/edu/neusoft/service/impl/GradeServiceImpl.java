package cn.edu.neusoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.neusoft.pojo.Grade;
import cn.edu.neusoft.service.GradeService;
import cn.edu.neusoft.mapper.GradeMapper;
import org.springframework.stereotype.Service;

/**
* @author 赵林阳
* @description 针对表【tb_grade】的数据库操作Service实现
* @createDate 2023-06-21 11:18:56
*/
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService{

}




