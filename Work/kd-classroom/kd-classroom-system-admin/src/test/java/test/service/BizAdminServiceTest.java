package test.service;

import com.booksroo.classroom.common.dao.AdminMapper;
import com.booksroo.classroom.common.domain.AdminDomain;
import com.booksroo.classroom.service.BizAdminService;
import com.sun.scenario.effect.impl.prism.PrImage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunitTest;

import java.util.Date;

public class BizAdminServiceTest extends BaseJunitTest {

    @Autowired
    private BizAdminService bizAdminService;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testAdd(){
        AdminDomain adminDomain = new AdminDomain();
        adminDomain.setAccount("tom");
        adminDomain.setPassword("e10adc3949ba59abbe56e057f20f883e");
        adminDomain.setCreateTime(new Date());
        adminDomain.setUpdateTime(new Date());

        adminMapper.insert(adminDomain);

    }

    @Test
    public void testQuery() throws Exception{
        System.out.println(bizAdminService.checkLoginInfo("root","e10adc3949ba59abbe56e057f20f883e"));
    }

}
