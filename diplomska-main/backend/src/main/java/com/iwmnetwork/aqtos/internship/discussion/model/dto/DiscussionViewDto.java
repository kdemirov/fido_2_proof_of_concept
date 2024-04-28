package com.iwmnetwork.aqtos.internship.discussion.model.dto;

import com.iwmnetwork.aqtos.internship.discussion.model.views.CommentView;
import com.iwmnetwork.aqtos.internship.discussion.model.views.DiscussionView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionViewDto {
    private DiscussionView discussionView;
    private List<CommentView> comments;
}
