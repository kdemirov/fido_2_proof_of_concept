package com.iwmnetwork.aqtos.internship.discussion.model;

import com.iwmnetwork.aqtos.internship.discussion.model.identifiers.PersonId;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Mention.
 */
@AllArgsConstructor
@Getter
public class Mention {
    private PersonId personId;
    private String label;
}
