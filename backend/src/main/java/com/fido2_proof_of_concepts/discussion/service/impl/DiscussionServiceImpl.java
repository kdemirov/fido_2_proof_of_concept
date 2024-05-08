package com.fido2_proof_of_concepts.discussion.service.impl;

import com.fido2_proof_of_concepts.discussion.model.aggregates.Discussion;
import com.fido2_proof_of_concepts.discussion.model.dto.DiscussionViewDto;
import com.fido2_proof_of_concepts.discussion.model.identifiers.DiscussionId;
import com.fido2_proof_of_concepts.discussion.model.views.CommentView;
import com.fido2_proof_of_concepts.discussion.model.views.DiscussionView;
import com.fido2_proof_of_concepts.discussion.repository.CommentViewRepository;
import com.fido2_proof_of_concepts.discussion.repository.DiscussionJpaRepository;
import com.fido2_proof_of_concepts.discussion.repository.DiscussionViewRepository;
import com.fido2_proof_of_concepts.discussion.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionJpaRepository discussionJpaRepository;
    private final DiscussionViewRepository discussionViewRepository;
    private final CommentViewRepository commentViewRepository;

    @Override
    public Optional<DiscussionViewDto> findById(DiscussionId discussionId) {
        Optional<DiscussionView> discussionView = this.discussionViewRepository.findById(discussionId);
        List<CommentView> commentViews = this.commentViewRepository.findAllByDiscussionId(discussionId);
        return discussionView.map(view -> new DiscussionViewDto(view, commentViews));
    }

    @Override
    public List<Discussion> findByName(String name) {
        String queryName = "%" + name + "%";
        return this.discussionJpaRepository.findByNameLike(queryName);
    }

    @Override
    public Page<Discussion> findAllWithPagination(Pageable pageable) {
        return this.discussionJpaRepository.findAll(pageable);
    }

    @Override
    public Long getNumberOfDiscussion() {
        return this.discussionJpaRepository.count();
    }
}
