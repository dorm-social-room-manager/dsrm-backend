package com.dsrm.dsrmbackend.pageable;


import com.dsrm.dsrmbackend.tables.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedUserResponse {
    private List<User> userList;
    private Long numberOfItems;
    private int numberOfPages;
}
