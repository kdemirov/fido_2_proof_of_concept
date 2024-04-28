package com.iwmnetwork.aqtos.internship.discussion.service.impl;

import com.iwmnetwork.aqtos.internship.discussion.model.aggregates.Discussion;
import com.iwmnetwork.aqtos.internship.discussion.model.dto.DiscussionViewDto;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import com.iwmnetwork.aqtos.internship.discussion.model.views.CommentView;
import com.iwmnetwork.aqtos.internship.discussion.model.views.DiscussionView;
import com.iwmnetwork.aqtos.internship.discussion.repository.CommentViewRepository;
import com.iwmnetwork.aqtos.internship.discussion.repository.DiscussionJpaRepository;
import com.iwmnetwork.aqtos.internship.discussion.repository.DiscussionViewRepository;
import com.iwmnetwork.aqtos.internship.discussion.service.DiscussionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionJpaRepository discussionJpaRepository;
    private final DiscussionViewRepository discussionViewRepository;
    private final CommentViewRepository commentViewRepository;


    public DiscussionServiceImpl(DiscussionJpaRepository discussionJpaRepository,
                                 DiscussionViewRepository discussionViewRepository,
                                 CommentViewRepository commentViewRepository) {
        this.discussionJpaRepository = discussionJpaRepository;
        this.discussionViewRepository = discussionViewRepository;
        this.commentViewRepository = commentViewRepository;
    }


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
