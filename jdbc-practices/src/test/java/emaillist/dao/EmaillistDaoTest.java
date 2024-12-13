package emaillist.dao;

import emaillist.vo.EmaillistVo;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmaillistDaoTest {
    private static Long count = 0L;

    @BeforeAll
    public static void setUp() {
        count = new EmaillistDao().count();
    }

    @AfterAll
    public static void cleanUp() {

    }

    @Test
    @Order(1)
    public void insertTest() {
        // given
        EmaillistVo vo = new EmaillistVo(null, "둘", "리", "dooly@gmail.com");

        // when
        Boolean result = new EmaillistDao().insert(vo);

        // then
        assertTrue(result);
    }

    @Test
    @Order(2)
    public void findAllTest() {
        // given

        // when
        List<EmaillistVo> list = new EmaillistDao().findAll();

        // then
        assertEquals(count + 1, list.size());
    }

    @Test
    @Order(3)
    public void deleteByEmailTest() {
        // given
        String email = "dooly@gmail.com";

        // when
        Boolean result = new EmaillistDao().deleteByEmail(email);

        // then
        assertTrue(result);
    }
}
