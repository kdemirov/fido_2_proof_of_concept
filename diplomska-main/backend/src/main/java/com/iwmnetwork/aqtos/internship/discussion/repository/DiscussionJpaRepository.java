package com.iwmnetwork.aqtos.internship.discussion.repository;

import com.iwmnetwork.aqtos.internship.discussion.model.aggregates.Discussion;
import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.DiscussionId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DiscussionJpaRepository extends JpaRepository<Discussion, DiscussionId> {

    List<Discussion> findByNameLike(String name);


    @Override
    Page<Discussion> findAll(Pageable pageable);
}
