package test.dao;

import com.booksroo.classroom.common.dao.ClassMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.service.BaseJunitTest;

/**
 * @author liujianjian
 * @date 2018/6/4 21:35
 */
public class ClassMapperTest extends BaseJunitTest {

    @Autowired
    private ClassMapper classMapper;

    @Test
    public void testSelect() {
        System.out.println(classMapper.selectByPrimaryKey(1L));
    }
}
