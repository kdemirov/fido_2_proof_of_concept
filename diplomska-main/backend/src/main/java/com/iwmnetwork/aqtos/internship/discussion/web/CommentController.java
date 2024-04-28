package com.iwmnetwork.aqtos.internship.discussion.web;

import com.iwmnetwork.aqtos.internship.discussion.api.commands.AddCommentCommand;
import com.iwmnetwork.aqtos.internship.discussion.api.commands.RemoveCommentCommand;
import com.iwmnetwork.aqtos.internship.discussion.api.commands.UpdateCommentCommand;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.CommentId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.service.DefaultService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/discussion/comments")
public class CommentController {

    private final DefaultService defaultService;

    public CommentController(DefaultService defaultService) {
        this.defaultService = defaultService;
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> addComment(@RequestBody AddCommentCommand cmd) throws ConstraintViolationException {
        return this.defaultService.dispatch(new AddCommentCommand(cmd.getDiscussionId(),
                cmd.getAuthor(),
                cmd.getBody(),
                null,
                null));


    }


    @PostMapping("/edit")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> editComment(@RequestBody UpdateCommentCommand cmd) throws ConstraintViolationException {
        return this.defaultService.dispatch(new UpdateCommentCommand(cmd.getDiscussionId(),
                cmd.getCommentId(),
                cmd.getNewBody(),
                null,
                null));

    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> deleteComment(@RequestParam String discussionId,
                                                   @RequestParam String commentId) {
        DiscussionId discussionId1 = new DiscussionId(discussionId);
        CommentId commentId1 = new CommentId(commentId);
        return this.defaultService.dispatch(new RemoveCommentCommand(discussionId1, commentId1));
    }


}
