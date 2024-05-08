package com.iwmnetwork.aqtos.internship.discussion.web;

import com.iwmnetwork.aqtos.internship.discussion.api.commands.AddMemberCommand;
import com.iwmnetwork.aqtos.internship.discussion.api.commands.CreateDiscussionCommand;
import com.iwmnetwork.aqtos.internship.discussion.api.commands.CreateDiscussionWithMembersCommand;
import com.iwmnetwork.aqtos.internship.discussion.api.commands.RemoveMemberCommand;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import com.iwmnetwork.aqtos.internship.discussion.service.DefaultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Discussion rest controller.
 */
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/discussion")
public class DiscussionController {

    private final DefaultService defaultService;

    /**
     * Creates a discussion with given command.
     *
     * @param cmd {@link CreateDiscussionCommand}
     * @return id of the created discussion
     * @throws ConstraintViolationException
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> add(@RequestBody CreateDiscussionCommand cmd) throws ConstraintViolationException {
        return this.defaultService.dispatch(new CreateDiscussionCommand(cmd.getName(),
                cmd.getDescription(), cmd.getCreator()));
    }

    /**
     * Creates discussion with participants.
     *
     * @param name        name of the discussion
     * @param description description of the discussion
     * @param personId    creator's id.
     * @param personIds   participants id's.
     * @return id of created discussion
     * @throws ConstraintViolationException
     */
    @PostMapping("/add_with_members")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> add(@RequestParam String name,
                                         @RequestParam(required = false) String description,
                                         @RequestParam String personId,
                                         @RequestParam String[] personIds) throws ConstraintViolationException {
        List<PersonId> members = Arrays.stream(personIds).
                map(PersonId::new)
                .collect(Collectors.toList());
        PersonId creator = new PersonId(personId);
        return this.defaultService.dispatch(new CreateDiscussionWithMembersCommand(name, description, creator, members));
    }

    /**
     * Adds participant to a discussion with given discussion id.
     *
     * @param discussionId discussion id
     * @param personId     participant's id
     * @return id of the added participant
     */
    @PostMapping("/add-member")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> addMember(@RequestParam String discussionId,
                                               @RequestParam String personId) {
        DiscussionId discussionId1 = new DiscussionId(discussionId);
        PersonId member = new PersonId(personId);
        return this.defaultService.dispatch(new AddMemberCommand(discussionId1, member));
    }

    /**
     * Deletes participant from a discussion.
     *
     * @param discussionId discusiion id
     * @param personId     participant's id
     * @return id of the deleted participant
     */
    @DeleteMapping("/delete-member")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> deleteMember(@RequestParam String discussionId,
                                                  @RequestParam String personId) {
        DiscussionId discussionId1 = new DiscussionId(discussionId);
        PersonId member = new PersonId(personId);
        return this.defaultService.dispatch(new RemoveMemberCommand(discussionId1, member));
    }
}
