package com.simon.controller;

import com.simon.domain.AppUser;
import com.simon.domain.Comment;
import com.simon.domain.Reply;
import com.simon.domain.ResultMsg;
import com.simon.exception.NoMsgException;
import com.simon.repository.AppUserRepository;
import com.simon.repository.CommentRepository;
import com.simon.repository.ReplyRepository;
import com.simon.util.TokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by simon on 2017/4/9.
 */
@Api(description = "消息")
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public ResultMsg get(@RequestParam String access_token,
                         @RequestParam(required = false) Integer limit,
                         @RequestParam(required = false) Integer offset) throws Exception{
        ResultMsg resultMsg = new ResultMsg();
        AppUser currentUser = TokenUtil.getInstance().getAppUserByAccessToken(appUserRepository, jdbcTemplate, access_token);
        List<Reply> replies = replyRepository.findByToUserId(currentUser.getId());
        replies.sort((Reply o1, Reply o2) -> {
            if(o1.getLastEditTime()<o2.getLastEditTime()) return 1;
            return -1;
        });
        List<Map<String, Object>> mapList = new ArrayList<>();
        for(int i=0; i<replies.size(); i++){
            Map<String, Object> map = new LinkedHashMap<>();

            Reply reply = replies.get(i);

            map.put("reply", reply);

            //replyType 0针对评论的回复，1针对回复的回复
            //replyType 表示回复的类型，因为回复可以是针对评论的回复(comment)，也可以是针对回复的回复(reply)，
            //通过这个字段来区分两种情景。
            if(0==reply.getReplyType()){
                //当是对评论的回复时，replyId=commentId
                Comment comment = commentRepository.findOne(reply.getCommentId());
                map.put("replyType", 0);
                map.put("comment", comment);
                map.put("questionId", reply.getQuestionId());
            }else if(1==reply.getReplyType()){
                Reply replyReply = replyRepository.findOne(reply.getReplyId());
                map.put("replyType", 1);
                map.put("replyReply", replyReply);
                map.put("questionId", reply.getQuestionId());
            }
            mapList.add(map);
        }

        if(mapList.size()<=0){
            throw new NoMsgException();
        }

        resultMsg.setStatus(200);
        resultMsg.setData(mapList);

        return resultMsg;
    }

}
