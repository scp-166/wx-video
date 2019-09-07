package com.nekosighed.pojo.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "用户实体2", description = "用户信息2")
public class UsersVo implements Serializable {
    @ApiModelProperty(hidden = true)
    private String uuidToken;

    /**
     * 
     */
    @ApiModelProperty(hidden = true)
    private String id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", name = "username", example = "akarin", required = true)
    @NotNull(message = "用户账号不能为空")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", name = "password", example = "abcabc", required = true)
    @NotNull(message = "密码不能为空")
    private String password;

    /**
     * 我的头像，如果没有默认给一张
     */
    private String faceImage;

    /**
     * 昵称
     */
    @ApiModelProperty(hidden = true)
    private String nickname;

    /**
     * 我的粉丝数量
     */
    @ApiModelProperty(hidden = true)
    private Integer fansCounts;

    /**
     * 我关注的人总数
     */
    @ApiModelProperty(hidden = true)
    private Integer followCounts;

    /**
     * 我接受到的赞美/收藏 的数量
     */
    @ApiModelProperty(hidden = true)
    private Integer receiveLikeCounts;

    /**
     * users
     */
    private static final long serialVersionUID = 1L;
}