package cn.weli.production.author.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商户平台 用户登录的返回结果
 *
 * @author john.wu
 * @date 2018/09/26
 */
@Data
@ApiModel
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class ClientAuthorLoginResp {

    @ApiModelProperty("密钥")
    private String token;

    @ApiModelProperty("用户信息")
    private ClientAuthorDTO account;
}
