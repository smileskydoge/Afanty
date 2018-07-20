package test.dao;

import com.booksroo.classroom.common.dao.ClassMapper;
import com.booksroo.classroom.common.dao.SystemMenuMapper;
import com.booksroo.classroom.common.domain.ClassDomain;
import com.booksroo.classroom.common.domain.SystemMenu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunitTest;

/**
 * @author liujianjian
 * @date 2018/6/2 17:46
 */
public class ClassMapperTest extends BaseJunitTest {

    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Test
    public void testAdd() {
//        ClassDomain domain = new ClassDomain();
//        domain.setSchoolId(1000L);
//        domain.setClassNo((byte) 1);
//        domain.setGrade((byte) 7);
//        classMapper.insertSelective(domain);

//        SystemMenu domain = new SystemMenu();
//        domain.setMenuName("学生管理");
//        domain.setMenuUrl("/");
//        domain.setId(1);
//        domain.setParentMenuId(1);
//        systemMenuMapper.updateByPrimaryKeySelective(domain);
    }

    @Test
    public void testSelect() {
        System.out.println(classMapper.selectByPrimaryKey(1L));
    }
}
