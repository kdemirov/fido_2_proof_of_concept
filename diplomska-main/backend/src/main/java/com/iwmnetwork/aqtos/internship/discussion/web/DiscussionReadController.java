package com.iwmnetwork.aqtos.internship.discussion.web;
import com.iwmnetwork.aqtos.internship.discussion.model.aggregates.Discussion;
import com.iwmnetwork.aqtos.internship.discussion.model.dto.DiscussionViewDto;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.service.DiscussionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/discussion")
public class DiscussionReadController {

    private final DiscussionService discussionService;

    public DiscussionReadController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }


    @GetMapping("/number")
    public Long getNumberOfDiscussion() {
        return this.discussionService.getNumberOfDiscussion();
    }

    @GetMapping("/pagination")
    public List<Discussion> findAll(Pageable pageable) {
        return this.discussionService.findAllWithPagination(pageable).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscussionViewDto> previewDiscussion(@PathVariable String id) {
        DiscussionId discussionId = new DiscussionId(id);
        return this.discussionService.findById(discussionId)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }


    @GetMapping("/name")
    public List<Discussion> findByNameLike(@RequestParam String name) {
        return this.discussionService.findByName(name);

    }


}
