import com.nekosighed.ApplicationStart;
import com.nekosighed.mapper.mapper.vo.VideosVoMapper;
import com.nekosighed.service.VideoService;
import com.nekosighed.service.imp.BgmServiceImpl;
import com.nekosighed.service.imp.UsersServiceImpl;
import com.nekosighed.service.imp.VideoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationStart.class)
public class TestApi {
    public static class FFMPEG {
        private FFMPEG ffmpeg;

        public static void invoke(String sourceUrl, String targetUrl) {
            ProcessBuilder builder = new ProcessBuilder();
            List<String> commandList = new ArrayList<>();
            commandList.add("D:\\development_tool\\ffmpeg\\bin\\ffmpeg.exe");
            commandList.add("-i");
            commandList.add(sourceUrl);
            commandList.add("-y");
            commandList.add(targetUrl);
            builder.command(commandList);
            try {
                Process process = builder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testaaa() {
        FFMPEG.invoke("H:\\video.mp4", "H:\\video2.mp4");
    }

    @Autowired
    UsersServiceImpl usersService;

    @Autowired
    BgmServiceImpl bgmService;

    @Test
    public void testDemo() {
        System.out.println(bgmService.queryBgmById(2));
        System.out.println(bgmService.queryBgmById("2"));
    }

    @Resource
    VideosVoMapper mapper;

    @Test
    public void testVo() {

    }

    @Resource
    VideoServiceImpl videoService;

    @Test
    public void testPage() {
//        System.out.println(videoService.getAllVideosByPage(1, 2));
//        System.out.println(videoService.getAllVideosByPage(1, 1));

        String str = "wxb88209db4bf1623e.o6zAJs7LNz7cd_TzFpB9Op3xQXCA.TWTj0CGQ5yvSfe6aa3012488604e765d7a216b9b69a4.jpg";
        int index = str.lastIndexOf(".");
        String prefix = str.substring(0, index);
        String suffix = str.substring(index+1);

        System.out.println(prefix);
        System.out.println(suffix);
    }

}
