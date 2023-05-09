package com.yarns.december.adapter;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yarns.december.entity.base.bo.LoginBo;
import com.yarns.december.entity.base.vo.EnumsVo;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.system.SysRole;
import com.yarns.december.entity.system.SysUser;
import com.yarns.december.entity.system.SysUserRole;
import com.yarns.december.entity.system.bo.SysUserBo;
import com.yarns.december.entity.system.bo.SysUserEditBo;
import com.yarns.december.entity.system.bo.SysUserPageBo;
import com.yarns.december.entity.system.bo.SysUserPutPassBo;
import com.yarns.december.entity.system.vo.SysUserInfoVo;
import com.yarns.december.entity.system.vo.SysUserPageVo;
import com.yarns.december.entity.system.vo.SysUserSessionVo;
import com.yarns.december.service.SysRoleService;
import com.yarns.december.service.SysUserRoleService;
import com.yarns.december.service.SysUserService;
import com.yarns.december.service.impl.ValidateCodeService;
import com.yarns.december.support.constant.Constant;
import com.yarns.december.support.exception.BaseException;
import com.yarns.december.support.utils.CommonUtils;
import com.yarns.december.support.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SysUserAdapter {
    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SysRoleService sysRoleService;
    private final ObjectMapper objectMapper;
    private final ValidateCodeService validateCodeService;

    /**
     * 定制化登录
     * @param bo
     * @return
     * @throws BaseException
     * @throws JsonProcessingException
     */
    public String login(LoginBo bo) throws BaseException, JsonProcessingException {
        // 校验验证码
        validateCodeService.check(bo.getKey(),bo.getCaptcha());
        val user = sysUserService.getUserByLoginName(bo.getUsername());
        if(user == null){
            log.info("用户名不存在");
            throw new BaseException("认证失败");
        }
        if(!bCryptPasswordEncoder.matches(bo.getPassword(),user.getPassword())){
            log.info("密码不正确");
            throw new BaseException("认证失败");
        }
        if(!user.getUserStatus()){
            throw new BaseException("账号被冻结");
        }
        val userSession = new SysUserSessionVo();
        BeanUtils.copyProperties(user, userSession);
        StpUtil.login(user.getId(),objectMapper.writeValueAsString(userSession));
        return StpUtil.getTokenValue();
    }

    /**
     * 修改密码
     * @param bo
     * @throws BaseException
     */
    public void updatePass(SysUserPutPassBo bo) throws BaseException {
        if(bo.getId().equals(1L) && !StpUtil.hasRole(Constant.ADMIN_ROLE)){
            throw new BaseException("本次操作非法");
        }
        if(bo.getOldPwd().equals(bo.getNewPwd())){
            throw new BaseException("新密码不能与旧密码相同");
        }
        val temp = sysUserService.getById(bo.getId());
        if(Objects.isNull(temp)){
            throw new BaseException("用户不存在");
        }
        if(!bCryptPasswordEncoder.matches(bo.getOldPwd(),temp.getPassword())){
            throw new BaseException("旧密码不正确");
        }
        val user = new SysUser();
        user.setId(bo.getId());
        user.setPassword(bCryptPasswordEncoder.encode(bo.getNewPwd()));
        sysUserService.updateSysUser(user);
    }

    public IPage<SysUserPageVo> findSysUsers(QueryRequest request, SysUserPageBo sysUser) {
        val user = new SysUser();
        BeanUtils.copyProperties(sysUser,user);
        return sysUserService.findSysUsers(request,user);
    }

    /**
     * 新增校验
     * @param sysUser
     * @throws BaseException
     */
    private void createCheck(SysUser sysUser) throws BaseException {
        if(!ValidatorUtil.isMobile(sysUser.getMobile())){
            throw new BaseException("手机号不合法");
        }
        SysUser temp1 = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMobile,sysUser.getMobile()));
        if(Objects.nonNull(temp1)){
            throw new BaseException("该手机号已经存在账号");
        }
        SysUser temp2 = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,sysUser.getUsername()));
        if(Objects.nonNull(temp2)){
            throw new BaseException("该账号已经存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void createSysUser(SysUserBo sysUser) throws BaseException {
        val u = new SysUser();
        BeanUtils.copyProperties(sysUser,u);
        createCheck(u);
        //加密
        u.setPassword(bCryptPasswordEncoder.encode(sysUser.getPwd()));
        sysUserService.createSysUser(u);
        if(CollectionUtils.isNotEmpty(sysUser.getRoleIds())){
            setUserRoles(u, sysUser.getRoleIds());
        }
    }

    private void setUserRoles(SysUser u, List<Long> roleIds) {
        var userRoles = new ArrayList<SysUserRole>();
        for (Long r : roleIds) {
            var ur = new SysUserRole();
            ur.setUserId(u.getId());
            ur.setRoleId(r);
            userRoles.add(ur);
        }
        sysUserRoleService.insertUserRoles(userRoles);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateUser(SysUserEditBo sysUser) throws BaseException {
        if(sysUser.getId().equals(1L) && !StpUtil.hasRole(Constant.ADMIN_ROLE)){
            throw new BaseException("本次操作非法");
        }
        val user = new SysUser();
        BeanUtils.copyProperties(sysUser,user);
        if(!ValidatorUtil.isMobile(sysUser.getMobile())){
            throw new BaseException("手机号不合法");
        }
        sysUserService.updateSysUser(user);
        sysUserRoleService.deleteUserRoleByUserId(user.getId());
        if(CollectionUtils.isNotEmpty(sysUser.getRoleIds())){
            setUserRoles(user, sysUser.getRoleIds());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteSysUsers(String[] ids) throws BaseException {
        for (String id : ids) {
            if(id.equals("1") && !StpUtil.hasRole(Constant.ADMIN_ROLE)){
                throw new BaseException("本次操作非法");
            }
        }
        sysUserService.deleteSysUsers(ids);
        sysUserRoleService.deleteUserRoleByUserIds(ids);
    }

    public SysUserSessionVo getCurrentUserInfo() {
        SysUserSessionVo vo =  CommonUtils.getCurrentUserInfo();
        if(StpUtil.hasRole(Constant.ADMIN_ROLE)){
            vo.setAdminTag(true);
        }
        //获取角色
        List<SysRole> roles = sysRoleService.getUserRoles(vo.getId());
        vo.setRoleNames(roles.stream().map(SysRole::getRoleToken).collect(Collectors.toList()));
        return vo;
    }

    public SysUserInfoVo getUserInfo(Long userId) {
        var infoVo = new SysUserInfoVo();
        SysUser user = sysUserService.getById(userId);
        BeanUtils.copyProperties(user,infoVo);
        List<SysUserRole> sysUserRoles = sysUserRoleService.getRolesByUserId(userId);
        infoVo.setRoleIds(sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
        return infoVo;
    }
}
