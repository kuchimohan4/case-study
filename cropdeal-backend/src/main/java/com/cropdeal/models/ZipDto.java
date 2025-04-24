package com.cropdeal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZipDto {

    private  String parentAddress;
    private String srcAddress;
    private String toAddress;
    private Boolean forZIpAndMail;

}
