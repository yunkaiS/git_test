package cn.weli.biz.author.meta;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import suishen.framework.dao.annotation.SuishenPrimary;
import suishen.framework.dao.annotation.SuishenTable;

/**
 * 原创作者审批日志
 * @author yunk.shen
 */
@Data
@ApiModel
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@SuishenTable(columnNameUnderScore = true, alias = "examine_log")
public class ExamineLog {

    @ApiModelProperty("审批日志ID")
    @SuishenPrimary
    private long examineId;

    @ApiModelProperty("作者ID")
    private long authorId;

    @ApiModelProperty("作者名称")
    private String authorName;

    @ApiModelProperty("申请时间")
    private long applyTime;

    @ApiModelProperty("审批状态 1:待审批; 2:审批通过; 3:审批未通过；4:撤回")
    private int status;

    @ApiModelProperty("审批人")
    private String opUser;

    @ApiModelProperty("审批时间")
    private long examineTime;

    @ApiModelProperty("审批意见")
    private String examineContent;

}
