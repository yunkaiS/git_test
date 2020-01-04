package cn.weli.production.author.meta;

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

    @ApiModelProperty("用户名")
    private String nickName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("密码")
    private String pwd;

    @ApiModelProperty("密码盐")
    private String pwdSalt;

    @ApiModelProperty("状态：0：已删除、1：正常、2：锁定、3：审核拒绝")
    private int status;

    @ApiModelProperty("qq")
    private long qq;

    @ApiModelProperty("开户行")
    private String bankName;

    @ApiModelProperty("银行卡号")
    private String bankAccount;

    @ApiModelProperty("上次登录Ip")
    private String lastLoginIp;

    @ApiModelProperty("上次登录时间")
    private long lastLoginTime;

    @ApiModelProperty("创建时间")
    private long createTime;

    @ApiModelProperty("最后更新时间")
    private long updateTime;

    public AuthorShip() {
    }

    public AuthorShip(Builder builder) {
        this.id = builder.id;
        this.nickName=builder.nickName;
        this.email=builder.email;
        this.mobile=builder.mobile;
        this.pwd=builder.pwd;
        this.pwdSalt=builder.pwdSalt;
        this.status=builder.status;
        this.qq=builder.qq;
        this.bankName=builder.bankName;
        this.bankAccount=builder.bankAccount;
        this.lastLoginIp=builder.lastLoginIp;
        this.lastLoginTime=builder.lastLoginTime;
        this.createTime=builder.createTime;
        this.updateTime=builder.updateTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private long id;
        private String nickName;
        private String email;
        private String mobile;
        private String pwd;
        private String pwdSalt;
        private int status;
        private long qq;
        private String bankName;
        private String bankAccount;
        private String lastLoginIp;
        private long lastLoginTime;
        private long createTime;
        private long updateTime;

        public Builder id(long id){
            this.id=id;
            return this;
        }

        public Builder nickName(String nickName){
            this.nickName=nickName;
            return this;
        }

        public Builder email(String email){
            this.email=email;
            return this;
        }

        public Builder mobile(String mobile){
            this.mobile=mobile;
            return this;
        }

        public Builder pwd(String pwd){
            this.pwd=pwd;
            return this;
        }

        public Builder pwdSalt(String pwdSalt){
            this.pwdSalt=pwdSalt;
            return this;
        }

        public Builder status(int status){
            this.status=status;
            return this;
        }

        public Builder qq(long qq){
            this.qq=qq;
            return this;
        }

        public Builder bankName(String bankName){
            this.bankName=bankName;
            return this;
        }

        public Builder bankAccount(String bankAccount){
            this.bankAccount=bankAccount;
            return this;
        }

        public Builder lastLoginIp(String lastLoginIp){
            this.lastLoginIp=lastLoginIp;
            return this;
        }

        public Builder lastLoginTime(long lastLoginTime){
            this.lastLoginTime=lastLoginTime;
            return this;
        }

        public Builder createTime(long createTime){
            this.createTime=createTime;
            return this;
        }

        public Builder updateTime(long updateTime){
            this.updateTime=updateTime;
            return this;
        }

        public AuthorShip build() {
            return new AuthorShip(this);
        }
    }
}
