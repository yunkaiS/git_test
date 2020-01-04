package cn.weli.production.author.meta;

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
    private long id;

    @ApiModelProperty("作者ID")
    private long authorId;

    @ApiModelProperty("操作类型：0、注册 1、登陆 2、修改 3、审批")
    private int opType;

    @ApiModelProperty("操作人")
    private String opUser;

    @ApiModelProperty("操作时间")
    private long opTime;

    @ApiModelProperty("操作内容")
    private String desc;

    @ApiModelProperty("操作Ip")
    private String opIp;

    @ApiModelProperty("备注")
    private String remark;

    public ExamineLog(Builder builder) {
        this.id = builder.id;
        this.authorId=builder.authorId;
        this.opType=builder.opType;
        this.opUser=builder.opUser;
        this.opTime=builder.opTime;
        this.desc=builder.desc;
        this.remark=builder.remark;
        this.opIp=builder.opIp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private long id;
        private long authorId;
        private int opType;
        private String opUser;
        private long opTime;
        private String desc;
        private String remark;
        private String opIp;

        public Builder id(long id){
            this.id=id;
            return this;
        }

        public Builder authorId(long authorId){
            this.authorId=authorId;
            return this;
        }

        public Builder opType(int opType){
            this.opType=opType;
            return this;
        }

        public Builder opUser(String opUser){
            this.opUser=opUser;
            return this;
        }

        public Builder opTime(long opTime){
            this.opTime=opTime;
            return this;
        }

        public Builder desc(String desc){
            this.desc=desc;
            return this;
        }

        public Builder remark(String remark){
            this.remark=remark;
            return this;
        }

        public Builder opIp(String opIp){
            this.opIp=opIp;
            return this;
        }

        public ExamineLog build() {
            return new ExamineLog(this);
        }
    }
}
