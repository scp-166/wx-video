package com.nekosighed.pojo.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 视频 Dto
 */
@ApiModel(value = "videoDto实体", description = "videoDto实体")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VideosDto implements Serializable {
    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "视频描述", name = "videoDesc", example = "这是视频描述")
    @NotNull(message = "请输入视频描述")
    private String videoDesc;
}
