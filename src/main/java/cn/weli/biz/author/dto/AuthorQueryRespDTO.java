package cn.weli.biz.author.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class AuthorQueryRespDTO<T> {
    @ApiModelProperty("当前页")
    private int page;

    @ApiModelProperty("总页数")
    private int totalPage;

    @ApiModelProperty("总个数，必填")
    private int totalCount;

    @ApiModelProperty("每页应该有的条目数,必填")
    private int pageSize;

    @ApiModelProperty("list结果集")
    private List<T> list;

    @ApiModelProperty("商家总数")
    private int totalMerchant;

    @ApiModelProperty("合同书籍数")
    private int contractBookNum;

    @ApiModelProperty("实际书籍数")
    private int realBookNum;
}
