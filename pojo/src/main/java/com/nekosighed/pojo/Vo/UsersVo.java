package com.nekosighed.pojo.Vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;

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
    private String uuidToken;

    /**
     * 
     */
    private String id;

    /**
     * 用户名
     */

    private String username;

    /**
     * 密码
     */

    @JsonIgnore
    private String password;

    /**
     * 我的头像，如果没有默认给一张
     */
    private String faceImage;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 我的粉丝数量
     */
    private Integer fansCounts;

    /**
     * 我关注的人总数
     */
    private Integer followCounts;

    /**
     * 我接受到的赞美/收藏 的数量
     */
    private Integer receiveLikeCounts;

    /**
     * users
     */
    private static final long serialVersionUID = 1L;
}