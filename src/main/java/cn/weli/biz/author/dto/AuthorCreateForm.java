package cn.weli.biz.author.dto;

import cn.weli.util.ValidationUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

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
    @ApiModelProperty("作者Id")
//    @NotBlank(message = "作者Id不可为空")
//    @Pattern(regexp = ValidationUtils.POSITIVE_NUMBER, message = "作者Id有误")
    private long authorId;

    @ApiModelProperty("作者名")
    @Length(max = 64, message = "作者名超过长度限制，请重新输入")
    private String authorName;

    @ApiModelProperty("邮箱，登录的凭证，不可为空，并且唯一")
    @NotBlank(message = "邮箱不可为空")
    @Length(max = 128, message = "邮箱超过长度限制，请重新输入")
    private String email;

    @ApiModelProperty("手机号码")
    @Length(max = 16, message = "手机号超过长度限制")
    private String mobile;

}
