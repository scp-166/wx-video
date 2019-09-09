package com.nekosighed.pojo.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("上传视频实体")
@Getter
@Setter
@ToString
public class UploadVideoInfoDto {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", name = "userId", example = "abc10086", required = true)
    @NotNull(message = "用户id不能为空")
    private String userId;

    /**
     * bgmId(可选)
     */
    @ApiModelProperty(value = "音频id", name = "audioId", example = "abc10086")
    private String audioId;

    /**
     * 视频时长
     */
    @ApiModelProperty(value = "视频时长", name = "videoSeconds", example = "40.1", required = true)
    @NotNull(message = "视频时长不能为空")
    private double videoSeconds;

    /**
     * 视频长度
     */
    @ApiModelProperty(value = "视频宽度", name = "videoWidth", example = "720", required = true)
    @NotNull(message = "视频宽度不能为空")
    private int videoWidth;

    /**
     * 视频宽度
     */
    @ApiModelProperty(value = "视频高度", name = "videoHeight", example = "1280", required = true)
    @NotNull(message = "视频高度不能为空")
    private int videoHeight;

    /**
     * 视频描述
     */
    @ApiModelProperty(value = "视频描述", name = "videoDesc", example = "这是视频描述", required = true)
    @NotNull(message = "视频描述不能为空")
    private String videoDesc;
}
