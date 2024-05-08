package com.fido2_proof_of_concepts.discussion.web;

import com.fido2_proof_of_concepts.discussion.model.aggregates.Discussion;
import com.fido2_proof_of_concepts.discussion.model.dto.DiscussionViewDto;
import com.fido2_proof_of_concepts.discussion.model.identifiers.DiscussionId;
import com.fido2_proof_of_concepts.discussion.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Discussion read controller.
 */
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/discussion")
public class DiscussionReadController {

    private final DiscussionService discussionService;

    /**
     * Gets number of existing discussions.
     *
     * @return number of existing discussions.
     */
    @GetMapping("/number")
    public Long getNumberOfDiscussion() {
        return this.discussionService.getNumberOfDiscussion();
    }

    /**
     * Finds all discussions in page.
     *
     * @param pageable pageable request how many discussion per page.
     * @return list of {@link Discussion}
     */
    @GetMapping("/pagination")
    public List<Discussion> findAll(Pageable pageable) {
        return this.discussionService.findAllWithPagination(pageable).getContent();
    }

    /**
     * Finds details page about discussion by given discussion id.
     *
     * @param id discussion id
     * @return {@link DiscussionViewDto}
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiscussionViewDto> previewDiscussion(@PathVariable String id) {
        DiscussionId discussionId = new DiscussionId(id);
        return this.discussionService.findById(discussionId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Returns all discussion that have simular name like the given string.
     *
     * @param name given string
     * @return list of {@link Discussion}
     */
    @GetMapping("/name")
    public List<Discussion> findByNameLike(@RequestParam String name) {
        return this.discussionService.findByName(name);

    }
}
