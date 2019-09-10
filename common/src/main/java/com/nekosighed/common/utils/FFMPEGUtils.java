package com.nekosighed.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ffmpeg 处理类
 * https://www.jianshu.com/p/7156d1007f84
 * https://www.cnblogs.com/dwdxdy/p/3240167.html
 * https://blog.csdn.net/dxpqxb/article/details/76640328
 * 中文文档 https://www.longqi.cf/tools/2015/02/13/ffmpegcn/
 *
 * @author lyl
 */
public class FFMPEGUtils {
    private static final Logger logger = LoggerFactory.getLogger(FFMPEGUtils.class);

    private static final String FFMPEG_EXE = "D:/development_tool/ffmpeg/bin/ffmpeg.exe";

    public static void main(String[] args) {
        mergerVideoAndAudio("H:/video.mp4", "H:/music.mp3", 20, "H:/targetbbb.mp4");

    }

    /**
     * 提供调用，将视频和音频混合为一个视频
     *
     * @param video
     * @param audio
     * @param seconds
     * @param target
     * @return
     */
    public static boolean mergerVideoAndAudio(String video, String audio, double seconds, String target) {
        logger.error(video);
        logger.error(audio);
        logger.error(target);
        if (!new File(video).exists()) {
            logger.info("视频源 {} 不存在", video);
        }

        if (!new File(audio).exists()) {

            logger.info("音频源 {} 不存在", audio);
        }
        File targetFile = new File(target);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }


        int count = 0;

        // 抽取后的无音频的临时视频
        String tmpSeparateVideo = "H:/" + UuidUtils.createUUID() + ".mp4";
        // 抽取后的无视频的临时音频
        String tmpSeparateAudio = "H:/" + UuidUtils.createUUID() + ".mp3";
        // 合成两个音频后的临时音频
        String tmpAudio = "H:/" + UuidUtils.createUUID() + ".mp3";

        if (invoke(videoSeparateToVideo(video, tmpSeparateVideo)) == 0) {
            count++;
            if (invoke(videoSeparateToAudio(video, tmpSeparateAudio)) == 0) {
                count++;
                if (invoke(mergeAudioToNewAudio(audio, tmpSeparateAudio, tmpAudio, seconds)) == 0) {
                    count++;
                    if (invoke(mergeVideoAudioToNewVideo(tmpAudio, tmpSeparateVideo, target, seconds)) == 0) {
                        count++;
                    } else {
                        logger.warn("合并视频和音频失败");
                    }
                } else {
                    logger.warn("合并音频失败");
                }
            } else {
                logger.warn("分离纯音频失败");
            }
        } else {
            logger.warn("分离静态视频失败");
        }
        // 删除临时文件
        if (count == 4) {
            File[] files = new File[3];
            files[0] = new File(tmpSeparateVideo);
            files[1] = new File(tmpSeparateAudio);
            files[2] = new File(tmpAudio);
            for (File file : files) {
                if (!file.exists()) {
                    logger.info("文件 {} 不存在", file.getAbsoluteFile());
                } else {
                    if (!file.delete()) {
                        logger.info("文件 {} 无法删除", file.getAbsoluteFile());
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 分离视频流
     *
     * @param filesToBeProcessed
     * @param outPutVideoFile
     * @return
     */
    private static List<String> videoSeparateToVideo(String filesToBeProcessed, String outPutVideoFile) {
        // 对文件名进行判别

        // ffmpeg.exe -i H:\video.mp4 -vcodec copy -an -y H:\source.mp4
        List<String> commandList = new ArrayList<>();
        commandList.add(FFMPEG_EXE);
        commandList.add("-i");
        commandList.add(filesToBeProcessed);
        // 设定视频编解码器，未设定时则使用与输入流相同的编解码器
        commandList.add("-vcodec");
        // 使用 -i 相同的编解码器
        commandList.add("copy");
        // 不处理音频
        commandList.add("-an");
        commandList.add("-y");
        commandList.add(outPutVideoFile);
        logger.info(commandList.toString());
        return commandList;
    }

    /**
     * 分离音频
     *
     * @param filesToBeProcessed
     * @param outputAudioFile
     * @return
     */
    private static List<String> videoSeparateToAudio(String filesToBeProcessed, String outputAudioFile) {
        // ffmpeg.exe -i H:\video.mp4 -acodec copy -vn -y H:\source2.mp4
        List<String> commandList = new ArrayList<>();
        commandList.add(FFMPEG_EXE);
        commandList.add("-i");
        commandList.add(filesToBeProcessed);
        // 设定声音编解码器，未设定时则使用与输入流相同的编解码器
        commandList.add("-acodec");
        // 设置为解码器为 libmp3lame; 不设置则填入 copy
        commandList.add("libmp3lame");
        // 不处理视频
        commandList.add("-vn");
        commandList.add("-y");
        commandList.add(outputAudioFile);
        logger.info(commandList.toString());
        return commandList;
    }

    /**
     * 合并音频
     *
     * @param firstAudio
     * @param secondAudio
     * @param targetAudio
     * @param seconds
     * @return
     */
    private static List<String> mergeAudioToNewAudio(String firstAudio, String secondAudio, String targetAudio, double seconds) {
        // 使用 输入1 的时长
        // ffmpeg.exe -i H:\music.mp3 -i H:\music2.mp3 -filter_complex amix=inputs=2:duration=first:dropout_transition=1 H:\aaa.mp3
        // 使用 -t 的时长
        // ffmpeg.exe -i H:\music.mp3 -i H:\music2.mp3 -filter_complex amix=inputs=2:dropout_transition=1 -t 10 H:\aaa.mp3
        List<String> commandList = new ArrayList<>();
        commandList.add(FFMPEG_EXE);
        commandList.add("-i");
        commandList.add(firstAudio);
        commandList.add("-i");
        commandList.add(secondAudio);
        commandList.add("-filter_complex");
        // 使用 输入1 的时长
        // commandList.add("amix=inputs=2:duration=first:dropout_transition=2")；
        // 不使用 输入1 的时长
        commandList.add("amix=inputs=2:dropout_transition=2");
        // 设置时长
        commandList.add("-t");
        commandList.add(String.valueOf(seconds));
        commandList.add("-y");
        commandList.add(targetAudio);
        logger.info(commandList.toString());
        return commandList;
    }

    private static List<String> mergeVideoAudioToNewVideo(String audio, String video, String targetVideo, double seconds) {
        List<String> commandList = new ArrayList<>();
        commandList.add(FFMPEG_EXE);
        commandList.add("-i");
        commandList.add(audio);
        commandList.add("-i");
        commandList.add(video);
        commandList.add("-t");
        commandList.add(String.valueOf(seconds));
        commandList.add("-y");
        commandList.add(targetVideo);
        logger.info(commandList.toString());
        return commandList;
    }


    /**
     * 执行指定程序
     *
     * @param commandList
     * @return 程序执行错误码 错误执行返回-1 正确返回0
     */
    private static int invoke(List<String> commandList) {
        ProcessBuilder builder = new ProcessBuilder(commandList);
        Process process;
        try {
            process = builder.start();
            try (InputStreamReader inputStreamReader = new InputStreamReader(process.getErrorStream());
                 BufferedReader reader = new BufferedReader(inputStreamReader);
            ) {
                // 避免卡死?
                String str;
                while ((str=reader.readLine()) != null) {
                    System.out.println(str);
                }
            }
            process.destroy();
            return process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
