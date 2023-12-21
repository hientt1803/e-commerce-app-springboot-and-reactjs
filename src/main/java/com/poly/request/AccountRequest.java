package com.poly.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccountRequest {

    private String userName;
    private String fullname;
    private String email;
    private Boolean activated;

}
