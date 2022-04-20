package com.finalproject.util.autocomplete;

import com.finalproject.model.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserItem {

    private User id;
    private String text;
    private String slug;
}
