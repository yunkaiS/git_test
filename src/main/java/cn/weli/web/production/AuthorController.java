package cn.weli.web.production;

import cn.weli.common.PageWrapper;
import cn.weli.production.author.dto.*;
import cn.weli.production.author.meta.AuthorShip;
import cn.weli.production.author.meta.ExamineLog;
import cn.weli.production.author.service.AuthorShipService;
import cn.weli.production.author.service.ExamineLogService;
import cn.weli.common.helper.BaseNovelController;
import cn.weli.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import suishen.libs.meta.JSONResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author yunk.shen
 */
@Api(value = "Author", description = "原创作者平台")
@RestController
@RequestMapping("/authorship")
public class AuthorController extends BaseNovelController {
    @Resource
    private AuthorShipService authorShipService;

    @Resource
    private ExamineLogService examineLogService;

    @ApiOperation(value = "作者登陆")
    @PostMapping(value = "/login", headers = "Accept=application/json")
    public JSONResult login(HttpServletRequest request,
                            @ApiParam("作者邮箱") @RequestParam(name = "email")  String email,
                            @ApiParam("密码") @RequestParam(name = "pwd")  String pwd){
        ClientAuthorLoginResp resp=authorShipService.login(email,pwd, IpUtils.getRemoteHost(request));
        AuthorShip authorShip=authorShipService.getByEmail(email);
        examineLogService.loginRecord(authorShip.getId(),IpUtils.getRemoteHost(request));
        return JSONResult.okResult(resp);
    }

    @ApiOperation(value = "作者注册")
    @PostMapping(value = "/input", headers = "Accept=application/json")
    public JSONResult input(HttpServletRequest request,
            @ApiParam @RequestBody @Valid AuthorCreateForm form){
        AuthorShip authorShip=authorShipService.create(form);
        ExamineLog log=examineLogService.inputRecord(authorShip.getId(),IpUtils.getRemoteHost(request));
        return JSONResult.okResult(log);
    }

    @ApiOperation(value = "修改密码")
    @PostMapping(value = "/resetpwd", headers = "Accept=application/json")
    public JSONResult resetPwd(HttpServletRequest request,
                               @ApiParam("作者邮箱") @RequestParam(name = "email")  String email,
                               @ApiParam("密码") @RequestParam(name = "pwd")  String new_pwd){
        authorShipService.resetPassword(email,new_pwd);
        ExamineLog log=examineLogService.updateRecord(email,IpUtils.getRemoteHost(request));
        return JSONResult.okResult(log);
    }

    @ApiOperation(value = "更新基本信息")
    @PostMapping(value = "/updateinfo", headers = "Accept=application/json")
    public JSONResult updateInfo(HttpServletRequest request,
                                @ApiParam("作者id") @RequestParam(name = "id")  long id,
                                 @ApiParam("用户名") @RequestParam(name = "nick_name")  String nickName,
                                 @ApiParam("邮箱") @RequestParam(name = "email")  String email,
                                 @ApiParam("电话") @RequestParam(name = "mobile")  String mobile,
                               @ApiParam("qq") @RequestParam(name = "qq")  String qq){
        authorShipService.updateBasicInfo(id,nickName,email,mobile,qq);
        ExamineLog log=examineLogService.updateRecord(email,IpUtils.getRemoteHost(request));
        return JSONResult.okResult(log);
    }

    @ApiOperation(value = "作者审批")
    @PostMapping("/examine")
    @ResponseBody
    public JSONResult examine(HttpServletRequest request,
                                  @ApiParam("作者id") @RequestParam(name = "author_id")  long authorId,
                                  @ApiParam("操作人") @RequestParam(name = "op_user")  String opUser,
                                  @ApiParam("操作内容") @RequestParam(name = "desc")  String desc,
                                  @ApiParam("审批结果") @RequestParam(name = "opRes")  int opRes){
        examineLogService.examine(authorId,opUser,desc,opRes,IpUtils.getRemoteHost(request));
        return JSONResult.okResult(opRes);
    }

    @ApiOperation(value = "后台查询")
    @RequestMapping("/query")
    @ResponseBody
    public JSONResult authorQuery(@ApiParam @RequestBody @Valid AuthorQueryForm form){
        PageWrapper<AuthorShip> authorQueryRespDTO=authorShipService.adminQuery(form);
        return JSONResult.okResult(authorQueryRespDTO);
    }

    @ApiOperation(value = "后台查询所有作者")
    @RequestMapping("/queryAll")
    @ResponseBody
    public JSONResult getAllAuthor(){
        List<AuthorShip> list=authorShipService.getAllList();
        return JSONResult.okResult(list);
    }
}
