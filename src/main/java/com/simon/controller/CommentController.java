package com.simon.controller;

import com.netflix.discovery.converters.Auto;
import com.simon.domain.AppUser;
import com.simon.domain.Comment;
import com.simon.domain.Reply;
import com.simon.domain.ResultMsg;
import com.simon.exception.NoCommmentException;
import com.simon.repository.AppUserRepository;
import com.simon.repository.CommentRepository;
import com.simon.repository.ReplyRepository;
import com.simon.util.ReplyType;
import com.simon.util.TokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by simon on 2017/3/18.
 */
@Api(description = "评论接口")
@RestController
@RequestMapping("/api/questions")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppUserRepository appUserRepository;


    @RequestMapping(value = "/{questionId}/comments", method = RequestMethod.POST)
    public ResultMsg insert(
            @PathVariable String questionId,
            @RequestParam Integer questionType,
            @RequestParam String content,
            @RequestParam String access_token){
        AppUser commentUser = TokenUtil.getInstance().getAppUserByAccessToken(appUserRepository, jdbcTemplate, access_token);
        Comment comment = new Comment();
        comment.setQuestionId(questionId);
        comment.setQuestionType(questionType);
        comment.setContent(content);
        comment.setUserId(commentUser.getId());
        comment.setCommentUser(commentUser);
        comment.setLastEditTime(System.currentTimeMillis());
        comment = commentRepository.insert(comment);

        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setStatus(201);
        resultMsg.setMessage("评论成功！");
        resultMsg.setData(comment);
        return resultMsg;
    }

    @RequestMapping(value = "/{questionId}/comments", method = RequestMethod.GET)
    public ResultMsg getByQuestionId(@PathVariable String questionId) throws Exception{

        List<Comment> comments = commentRepository.findByQuestionId(questionId);
        ResultMsg resultMsg = new ResultMsg();
        if(comments.size()<=0){
            throw new NoCommmentException();
        }
        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(comments);
        return resultMsg;
    }

    /**
     * 获取所有的回复，包括对评论的回复和对回复的回复
     * @param questionId
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/{questionId}/comments/{commentId}/replies", method = RequestMethod.GET)
    public ResultMsg getByCommentId(@PathVariable String questionId,
                                    @PathVariable String commentId){
        ResultMsg resultMsg = new ResultMsg();
        //先找出所有的回复
        List<Reply> replies = replyRepository.findByCommentId(commentId);
        Collections.sort(replies);//按照lastEditTime升序
        //Collections.reverse(replies);//按照lastEditTime降序

        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(replies);

        return resultMsg;
    }

    /**
     * 当是对评论的回复时，commentId=replyId
     * @param questionId
     * @param commentId
     * @param replyId
     * @param replyType
     * @param content
     * @param access_token
     * @return
     */
    @RequestMapping(value = "/{questionId}/comments/{commentId}/replies", method = RequestMethod.POST)
    public ResultMsg insert(@PathVariable String questionId,
                            @PathVariable String commentId,
                            @RequestParam String replyId,
                            @RequestParam Integer replyType,
                            @RequestParam String content,
                            @RequestParam String access_token
                            ){
        ResultMsg resultMsg = new ResultMsg();

        AppUser currentUser = TokenUtil.getInstance().getAppUserByAccessToken(appUserRepository, jdbcTemplate, access_token);

        Reply reply = new Reply();
        reply.setCommentId(commentId);
        reply.setContent(content);
        reply.setReplyType(replyType);
        reply.setReplyId(replyId);
        reply.setFromUserId(currentUser.getId());
        reply.setFromUser(currentUser);
        reply.setLastEditTime(System.currentTimeMillis());

        if(replyType== ReplyType.COMMENT){
            Comment comment = commentRepository.findOne(replyId);
            AppUser toUser = appUserRepository.findById(comment.getUserId());
            reply.setToUser(toUser);
            reply.setToUserId(toUser.getId());
        }else{
            Reply originReply = replyRepository.findOne(replyId);
            AppUser toUser = appUserRepository.findById(originReply.getFromUserId());
            reply.setToUserId(toUser.getId());
            reply.setToUser(toUser);
        }
        reply = replyRepository.insert(reply);

        resultMsg.setStatus(201);
        resultMsg.setMessage("回复成功！");
        resultMsg.setData(reply);

        return resultMsg;
    }
}
