package cn.weli.production.author.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户端用的作者的账号信息
 *
 * @author yunk.shen
 * @date 2019/12/31
 */
@Data
@ApiModel
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class ClientAuthorDTO {
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String nickName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("上次登录Ip地址")
    private String lastLoginIp;

    @ApiModelProperty("上次登录时间")
    private Long lastLoginTime;

    public ClientAuthorDTO(Builder builder) {
        this.id = builder.id;
        this.nickName = builder.nickName;
        this.email = builder.email;
        this.lastLoginIp = builder.lastLoginIp;
        this.lastLoginTime = builder.lastLoginTime;
    }

    public static Builder builder() {
            return new Builder();
    }

    public static class Builder {
        private Long id;
        private String nickName;
        private String email;
        private String lastLoginIp;
        private Long lastLoginTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder lastLoginIp(String lastLoginIp) {
            this.lastLoginIp = lastLoginIp;
            return this;
        }

        public Builder lastLoginTime(Long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
            return this;
        }

        public ClientAuthorDTO build() {
            return new ClientAuthorDTO(this);
        }

    }
}
