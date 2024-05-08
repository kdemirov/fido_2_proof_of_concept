package com.iwmnetwork.aqtos.internship.discussion.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Reference
 *
 * @param <T> generalized type for any id
 */
@AllArgsConstructor
@Getter
public class Reference<T> {
    private T id;
    private String label;
}
