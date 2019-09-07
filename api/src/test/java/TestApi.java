import com.nekosighed.ApplicationStart;
import com.nekosighed.service.UsersService;
import com.nekosighed.service.imp.UsersServiceImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationStart.class)
public class TestApi {
    @Autowired
    UsersServiceImp usersService;

    @Test
    public void testDemo(){
        System.out.println(usersService.queryUserInfoByUserName("a"));

        System.out.println(usersService.queryUserInfoByUserName("abc"));
    }
}
