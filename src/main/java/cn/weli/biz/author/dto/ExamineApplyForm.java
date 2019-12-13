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

@Data
@ApiModel
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class ExamineApplyForm {
    @ApiModelProperty("审批日志Id")
//    @Pattern(regexp = ValidationUtils.POSITIVE_NUMBER, message = "Id有误")
    long examineId;

    @ApiModelProperty("审批结果")
    int status;

    @ApiModelProperty("审批人")
    @Length(max = 64, message = "审批人名称超过长度限制，请重新输入")
    String opUser;

    @ApiModelProperty("审批意见")
    @Length(max = 256, message = "审批意见超过长度限制，请重新输入")
    String content;
}
