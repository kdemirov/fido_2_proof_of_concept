package com.iwmnetwork.aqtos.internship.discussion.web;

import com.iwmnetwork.aqtos.internship.discussion.api.commands.*;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import com.iwmnetwork.aqtos.internship.discussion.service.DefaultService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/discussion")
public class DiscussionController {
    private final DefaultService defaultService;

    public DiscussionController(DefaultService defaultService) {
        this.defaultService = defaultService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> add(@RequestBody CreateDiscussionCommand cmd) throws ConstraintViolationException {
        return this.defaultService.dispatch(new CreateDiscussionCommand(cmd.getName(),
                cmd.getDescription(), cmd.getCreator()));
    }

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

    @PostMapping("/add-member")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> addMember(@RequestParam String discussionId,
                                               @RequestParam String personId) {
        DiscussionId discussionId1 = new DiscussionId(discussionId);
        PersonId member = new PersonId(personId);
        return this.defaultService.dispatch(new AddMemberCommand(discussionId1, member));
    }

    @DeleteMapping("/delete-member")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CompletableFuture<String> deleteMember(@RequestParam String discussionId,
                                                  @RequestParam String personId) {
        DiscussionId discussionId1 = new DiscussionId(discussionId);
        PersonId member = new PersonId(personId);
        return this.defaultService.dispatch(new RemoveMemberCommand(discussionId1, member));
    }


}
