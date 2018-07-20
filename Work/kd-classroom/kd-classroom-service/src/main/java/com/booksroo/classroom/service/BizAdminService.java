package com.booksroo.classroom.service;

import com.booksroo.classroom.common.constant.CommonConstant;
import com.booksroo.classroom.common.constant.PromptConstant;
import com.booksroo.classroom.common.dao.AdminMapper;
import com.booksroo.classroom.common.domain.AdminDomain;
import com.booksroo.classroom.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bizAdminService")
public class BizAdminService extends BaseService{

    @Autowired
    private AdminMapper adminMapper;

    public AdminDomain checkLoginInfo(String account, String password) throws BizException {

        AdminDomain adminDomain = adminMapper.queryAdminInfoByAccount(account);

        //管理员身份信息不存在
        if(adminDomain==null){
            throw new BizException(CommonConstant.FAIL, PromptConstant.USER_INFO_NOT_EXIST);
        }

        //密码错误
        if (!password.equals(adminDomain.getPassword())){
            throw new BizException(CommonConstant.FAIL,PromptConstant.PASSWORD_INCORRECT);
        }

        return adminDomain;
    }

    public AdminDomain getById(long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

}
