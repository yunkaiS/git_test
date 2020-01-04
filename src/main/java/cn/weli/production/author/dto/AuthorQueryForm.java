package cn.weli.production.author.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class AuthorQueryForm {
    @ApiModelProperty("作者id")
    private Long id;

    @ApiModelProperty("作者名称")
    private String nickName;

    @ApiModelProperty("账号状态 0:待审核 1:正常 2:锁定 3:审核拒绝")
    private Integer status;

    @ApiModelProperty("页次 从1开始")
    @NotNull(message = "页次不能为空")
    @Min(value = 1, message = "无效的页次参数")
    private Integer page;

    @ApiModelProperty("页大小")
    @NotNull(message = "页大小不能为空")
    @Min(value = 1, message = "无效的页大小参数")
    private Integer pageSize;
}
