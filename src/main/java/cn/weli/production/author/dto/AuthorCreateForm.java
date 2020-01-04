package cn.weli.production.author.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 创建原创作者用户
 *
 * @author yunk.shen
 * @date 2019.12.6
 */
@Data
@ApiModel
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class  AuthorCreateForm {
    @ApiModelProperty("作者名")
    @Length(max = 64, message = "作者名超过长度限制，请重新输入")
    private String nickName;

    @ApiModelProperty("邮箱，登录的凭证，不可为空，并且唯一")
    @NotBlank(message = "邮箱不可为空")
    @Length(max = 128, message = "邮箱超过长度限制，请重新输入")
    private String email;

    @ApiModelProperty("手机号码")
    @Length(max = 16, message = "手机号超过长度限制")
    private String mobile;

    @ApiModelProperty("密码")
    @Length(max = 16, message = "密码超过长度限制")
    private String pwd;

    @ApiModelProperty("qq")
    private long qq;

    @ApiModelProperty("开户行")
    private String bankName;

    @ApiModelProperty("银行卡号")
    private String bankAccount;


    public static final class builder {
        private String nickName;
        private String email;
        private String mobile;
        private String pwd;
        private long qq;
        private String bankName;
        private String bankAccount;

        private builder() {
        }

        public static builder builder() {
            return new builder();
        }

        public builder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public builder email(String email) {
            this.email = email;
            return this;
        }

        public builder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public builder pwd(String pwd) {
            this.pwd = pwd;
            return this;
        }

        public builder qq(long qq) {
            this.qq = qq;
            return this;
        }

        public builder bankName(String bankName) {
            this.bankName = bankName;
            return this;
        }

        public builder bankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
            return this;
        }

        public AuthorCreateForm build() {
            AuthorCreateForm authorCreateForm = new AuthorCreateForm();
            authorCreateForm.setNickName(nickName);
            authorCreateForm.setEmail(email);
            authorCreateForm.setMobile(mobile);
            authorCreateForm.setPwd(pwd);
            authorCreateForm.setQq(qq);
            authorCreateForm.setBankName(bankName);
            authorCreateForm.setBankAccount(bankAccount);
            return authorCreateForm;
        }
    }
}
