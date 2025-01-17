package cn.daenx.myauth.main.controller.web;

import cn.daenx.myauth.main.entity.Soft;
import cn.daenx.myauth.util.CheckUtils;
import cn.daenx.myauth.base.annotation.AdminLogin;
import cn.daenx.myauth.base.annotation.NoEncryptNoSign;
import cn.daenx.myauth.base.vo.Result;
import cn.daenx.myauth.main.entity.Admin;
import cn.daenx.myauth.base.vo.MyPage;
import cn.daenx.myauth.main.entity.User;
import cn.daenx.myauth.main.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 前端web使用的API接口
 *
 * @author DaenMax
 */
@Slf4j
@RestController
@RequestMapping("/web")
public class UserController {
    @Resource
    private IUserService userService;

    /**
     * 获取用户列表
     *
     * @param request
     * @return
     */
    @NoEncryptNoSign
    @AdminLogin
    @PostMapping("getUserList")
    public Result getUserList(HttpServletRequest request) {
        JSONObject jsonObject = (JSONObject) request.getAttribute("json");
        User user = jsonObject.toJavaObject(User.class);
        MyPage myPage = jsonObject.toJavaObject(MyPage.class);
        if (CheckUtils.isObjectEmpty(user) || CheckUtils.isObjectEmpty(myPage)) {
            return Result.error("参数错误");
        }
        if (CheckUtils.isObjectEmpty(myPage.getPageIndex()) || CheckUtils.isObjectEmpty(myPage.getPageSize())) {
            return Result.error("页码和尺寸参数不能为空");
        }
        if (CheckUtils.isObjectEmpty(user.getFromSoftId())) {
            return Result.error("fromSoftId参数不能为空");
        }
        return userService.getUserList(user, myPage);
    }


    /**
     * 查询用户，根据id
     *
     * @param request
     * @return
     */
    @NoEncryptNoSign
    @AdminLogin
    @PostMapping("getUser")
    public Result getUser(HttpServletRequest request) {
        JSONObject jsonObject = (JSONObject) request.getAttribute("json");
        User user = jsonObject.toJavaObject(User.class);
        if (CheckUtils.isObjectEmpty(user)) {
            return Result.error("参数错误");
        }
        if (CheckUtils.isObjectEmpty(user.getId())) {
            return Result.error("id不能为空");
        }
        return userService.getUser(user);
    }

    /**
     * 修改用户
     *
     * @param request
     * @return
     */
    @NoEncryptNoSign
    @AdminLogin
    @PostMapping("updUser")
    public Result updUser(HttpServletRequest request) {
        JSONObject jsonObject = (JSONObject) request.getAttribute("json");
        User user = jsonObject.toJavaObject(User.class);
        if (CheckUtils.isObjectEmpty(user)) {
            return Result.error("参数错误");
        }
        if (CheckUtils.isObjectEmpty(user.getId())) {
            return Result.error("id不能为空");
        }
        user.setLastIp(null);
        user.setLastTime(null);
        user.setRegTime(null);
        user.setFromSoftId(null);
        user.setFromSoftKey(null);
        user.setFromVerId(null);
        user.setFromVerKey(null);
        user.setDeviceInfo(null);
        user.setDeviceCode(null);
        if (CheckUtils.isObjectEmpty(user.getPass())
                && CheckUtils.isObjectEmpty(user.getName())
                && CheckUtils.isObjectEmpty(user.getPoint())
                && CheckUtils.isObjectEmpty(user.getQq())
                && CheckUtils.isObjectEmpty(user.getAuthTime())
                && CheckUtils.isObjectEmpty(user.getToken())
                && CheckUtils.isObjectEmpty(user.getRemark())
                && CheckUtils.isObjectEmpty(user.getCkey())) {
            return Result.error("参数不能全部为空");
        }
        return userService.updUser(user);
    }

    /**
     * 添加用户
     *
     * @param request
     * @return
     */
    @NoEncryptNoSign
    @AdminLogin
    @PostMapping("addUser")
    public Result addUser(HttpServletRequest request) {
        JSONObject jsonObject = (JSONObject) request.getAttribute("json");
        User user = jsonObject.toJavaObject(User.class);
        if (CheckUtils.isObjectEmpty(user)) {
            return Result.error("参数错误");
        }
        if (CheckUtils.isObjectEmpty(user.getFromSoftId())) {
            return Result.error("fromSoftId参数不能为空");
        }
        user.setId(null);
        user.setLastIp(null);
        user.setLastTime(null);
        user.setDeviceInfo(null);
        user.setDeviceCode(null);
        user.setFromVerKey(null);
        user.setFromVerId(null);
        user.setFromSoftKey(null);
        if (CheckUtils.isObjectEmpty(user.getUser())
                && CheckUtils.isObjectEmpty(user.getPass())
                && CheckUtils.isObjectEmpty(user.getName())
                && CheckUtils.isObjectEmpty(user.getPoint())
                && CheckUtils.isObjectEmpty(user.getQq())
                && CheckUtils.isObjectEmpty(user.getAuthTime())
                && CheckUtils.isObjectEmpty(user.getRemark())
                && CheckUtils.isObjectEmpty(user.getCkey())) {
            return Result.error("参数不能全部为空");
        }
        Admin myAdmin = (Admin) request.getAttribute("obj_admin");
        return userService.addUser(user, myAdmin);
    }

    /**
     * 删除用户（支持批量）
     *
     * @param request
     * @return
     */
    @NoEncryptNoSign
    @AdminLogin
    @PostMapping("delUser")
    public Result delUser(HttpServletRequest request) {
        JSONObject jsonObject = (JSONObject) request.getAttribute("json");
        String ids = jsonObject.getString("ids");
        if (CheckUtils.isObjectEmpty(ids)) {
            return Result.error("ids不能为空，多个用英文逗号隔开");
        }
        return userService.delUser(ids);
    }

    /**
     * 获取我的授权
     *
     * @param request
     * @return
     */
    @NoEncryptNoSign
    @AdminLogin(is_super_role = false)
    @PostMapping("getMyUserList")
    public Result getMyUserList(HttpServletRequest request) {
        JSONObject jsonObject = (JSONObject) request.getAttribute("json");
        User user = jsonObject.toJavaObject(User.class);
        MyPage myPage = jsonObject.toJavaObject(MyPage.class);
        Admin admin = (Admin) request.getAttribute("obj_admin");
        if (CheckUtils.isObjectEmpty(user) || CheckUtils.isObjectEmpty(myPage)) {
            return Result.error("参数错误");
        }
        if (CheckUtils.isObjectEmpty(myPage.getPageIndex()) || CheckUtils.isObjectEmpty(myPage.getPageSize())) {
            return Result.error("页码和尺寸参数不能为空");
        }
        return userService.getMyUserList(user, myPage, admin);
    }

    /**
     * 查询用户在线信息
     *
     */
    @NoEncryptNoSign
    @AdminLogin
    @PostMapping("queryUserOnlineInfo")
    public Result queryUserOnlineInfo(HttpServletRequest request) {
        JSONObject jsonObject = (JSONObject) request.getAttribute("json");
        User user = jsonObject.toJavaObject(User.class);
        if (CheckUtils.isObjectEmpty(user)) {
            return Result.error("参数错误");
        }
        return userService.queryUserOnlineInfo(user);
    }

    /**
     * 按条件批量操作用户授权
     *
     * @param request
     * @return
     */
    @NoEncryptNoSign
    @AdminLogin
    @PostMapping("updateUserAuthInfo")
    public Result updateUserAuthInfo(HttpServletRequest request){
        JSONObject jsonObject = (JSONObject) request.getAttribute("json");

        Integer fromSoftId = jsonObject.getInteger("fromSoftId");

        Integer minPoint = jsonObject.getInteger("minPoint");
        Integer maxPoint = jsonObject.getInteger("maxPoint");

        Integer minAuthTime = jsonObject.getInteger("minAuthTime");
        Integer maxAuthTime = jsonObject.getInteger("maxAuthTime");

        Integer minRegTime = jsonObject.getInteger("minRegTime");
        Integer maxRegTime = jsonObject.getInteger("maxRegTime");

        Integer updPoint = jsonObject.getInteger("updPoint");
        Integer updAuthTime = jsonObject.getInteger("updAuthTime");

        return userService.updateUserAuthInfo(fromSoftId,minPoint,maxPoint,minAuthTime,maxAuthTime,minRegTime,maxRegTime,updPoint,updAuthTime);
    }
}
