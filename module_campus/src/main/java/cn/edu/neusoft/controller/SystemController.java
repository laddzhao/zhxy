package cn.edu.neusoft.controller;


import cn.edu.neusoft.pojo.Admin;
import cn.edu.neusoft.pojo.LoginForm;
import cn.edu.neusoft.pojo.Student;
import cn.edu.neusoft.pojo.Teacher;
import cn.edu.neusoft.service.AdminService;
import cn.edu.neusoft.service.StudentService;
import cn.edu.neusoft.service.TeacherService;
import cn.edu.neusoft.utils.*;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Resource
    private AdminService adminService;

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;


    /**
     * 获取验证码图片响应到浏览器,并将验证码中的值保存到session域中 用于用户登录时/login校验
     */
    @ApiOperation("获取验证码图片")
    @RequestMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpSession session, HttpServletResponse response) throws IOException {
        //通过工具类CreateVerifiCodeImage 获得验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取验证码图片中的值 并保存在session域中 用于用于登录时校验
        String code = new String(CreateVerifiCodeImage.getVerifiCode());
        session.setAttribute("code", code);
        //将获取到的验证码图片响应到浏览器
        ImageIO.write(verifiCodeImage, "JPG", response.getOutputStream());
    }

    /**
     * 登录:进行验证码以及用户输入的账号密码进行校验
     */
    @ApiOperation("进行验证码以及用户输入的账号密码进行校验")
    @PostMapping("login")
    public Result<Object> login(@RequestBody LoginForm loginForm, HttpSession session){
        //获取session中存放的验证码中的值
        String code = (String) session.getAttribute("code");
        //判断session中的验证码的值是否还在 若时间太长 会失效
        if (code == null || "".equals(code)){
            return Result.fail().message("验证码失效,请重新输入");
        }
        //获取用户输入的验证码
        String userInputCode = loginForm.getVerifiCode();
        if (!userInputCode.equalsIgnoreCase(code)){
            return Result.fail().message("验证码有误");
        }
        //销毁session中的验证码的值
        session.removeAttribute("code");

        //用户名密码校验
        Integer userType = loginForm.getUserType();
        //获取用户名密码
        String username = loginForm.getUsername();
        String password = MD5.encrypt(loginForm.getPassword());
        Map<String,Object> map = new LinkedHashMap<>();
        if (userType == 1){
            //管理员
            Admin admin =  adminService.selectAdminByUsernameAndPwd(username,password);
            //判断输入（用户名密码）是否在数据库中
            if (admin != null){
                //登录成功, 根据用户ip和用户类型 生成token 并返回给浏览器
                String token = JwtHelper.createToken(admin.getId().longValue(),userType);
                map.put("token",token);
                return Result.ok(map);
            }
              return Result.fail().message("用户名或密码有误");

        }else if (userType == 2){
            //学生
            Student student =  studentService.selectStudentByUsernameAndPwd(username,password);
            if (student != null){
                String token = JwtHelper.createToken(student.getId().longValue(),userType);
                map.put("token",token);
                return Result.ok(map);
            }
            return Result.fail().message("用户名或密码有误");
        }else {
            //教师
            Teacher teacher =  teacherService.selectTeacherByUsernameAndPwd(username,password);
            if (teacher != null){
                String token = JwtHelper.createToken(teacher.getId().longValue(),userType);
                map.put("token",token);
                return Result.ok(map);
            }
            return Result.fail().message("用户名或密码有误");
        }
    }

    @ApiOperation("将请求头中的token解析成id和用户类型 并根据id和类型查询用户信息 将信息和用户类型数据一起返回")
    @GetMapping("/getInfo")
    public Result<Object> getInfo(@RequestHeader("token")String token){
        //判断token是否有效
        if (JwtHelper.isExpiration(token)){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //通过token 获取用户ip类型 将用户类型返回给浏览器
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("userType",userType);
        if (userType == 1){
            //管理员
            Admin admin = adminService.selectAdminById(userId);
            map.put("user",admin);
        }else if (userType == 2){
             //学生
            Student student = studentService.selectStudentById(userId);
            map.put("user",student);
        }else{
            //老师
            Teacher teacher = teacherService.selectTeacherById(userId);
            map.put("user",teacher);
        }
        return Result.ok(map);
    }

    @ApiOperation("上传头像")
    @PostMapping("/headerImgUpload")
    public Result<Object> headerImgUpload(@ApiParam("封装请求体中的图片二进制数据") @RequestPart("multipartFile") MultipartFile multipartFile) throws IOException {
//        先上传文件的名称
        String originalFilename = multipartFile.getOriginalFilename();
        assert originalFilename != null;
//        获取文件格式 如 .jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        为了避免文件保存到服务端，文件名相同导致覆盖。随机生成uuid数。
        String fileName = UUID.randomUUID().toString().toLowerCase().replace("-", "").concat(suffix);
//        保存路径
        String destPath = "D:/5e/campus/module_campus/src/main/resources/static/upload/".concat(fileName);
//        文件保存到服务器端
        multipartFile.transferTo(new File(destPath));
        return Result.ok("upload/".concat(fileName));
    }


}
