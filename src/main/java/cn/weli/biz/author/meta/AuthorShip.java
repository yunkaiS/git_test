package cn.weli.biz.author.meta;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import suishen.framework.dao.annotation.SuishenPrimary;
import suishen.framework.dao.annotation.SuishenTable;

/**
 * 原创作者表
 * @author yunk.shen
 */
@Data
@ApiModel
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@SuishenTable(columnNameUnderScore = true, alias = "author_ship")
public class AuthorShip {

    @ApiModelProperty("主键")
    @SuishenPrimary
    private long id;

    @ApiModelProperty("作者id")
    private String authorId;

    @ApiModelProperty("用户名")
    private String authorName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("状态：0：已删除、1：正常、2：锁定")
    private int status;

    @ApiModelProperty("创建时间")
    private long createTime;

//    @ApiModelProperty("上次登录Ip")
//    private String lastLoginIp;

//    @ApiModelProperty("上次登录时间")
//    private long lastLoginTime;

    @ApiModelProperty("最后更新时间")
    private long updateTime;
}
