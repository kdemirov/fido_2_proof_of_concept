package com.fido2_proof_of_concepts.discussion.web;

import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.discussion.api.commands.AddCommentCommand;
import com.fido2_proof_of_concepts.discussion.api.commands.RemoveCommentCommand;
import com.fido2_proof_of_concepts.discussion.api.commands.UpdateCommentCommand;
import com.fido2_proof_of_concepts.discussion.model.identifiers.CommentId;
import com.fido2_proof_of_concepts.discussion.model.identifiers.DiscussionId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.CompletableFuture;

/**
 * Comment rest controller.
 */
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/discussion/comments")
public class CommentController {

    private final DefaultService defaultService;

    /**
     * Adds a comment to a discussion.
     *
     * @param cmd {@link AddCommentCommand}
     * @return the id of the created comment
     * @throws ConstraintViolationException
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> addComment(@RequestBody AddCommentCommand cmd) throws ConstraintViolationException {
        return this.defaultService.dispatch(new AddCommentCommand(cmd.getDiscussionId(),
                cmd.getAuthor(),
                cmd.getBody(),
                null,
                null));
    }

    /**
     * Updates comment's content from a discussion.
     *
     * @param cmd {@link UpdateCommentCommand}
     * @return id of the updated comment.
     * @throws ConstraintViolationException
     */
    @PostMapping("/edit")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> editComment(@RequestBody UpdateCommentCommand cmd) throws ConstraintViolationException {
        return this.defaultService.dispatch(new UpdateCommentCommand(cmd.getDiscussionId(),
                cmd.getCommentId(),
                cmd.getNewBody(),
                null,
                null));
    }

    /**
     * Deletes comments from a discussion.
     *
     * @param discussionId discussion id
     * @param commentId    comment id
     * @return id of the deleted comment
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> deleteComment(@RequestParam String discussionId,
                                                   @RequestParam String commentId) {
        DiscussionId discussionId1 = new DiscussionId(discussionId);
        CommentId commentId1 = new CommentId(commentId);
        return this.defaultService.dispatch(new RemoveCommentCommand(discussionId1, commentId1));
    }
}
