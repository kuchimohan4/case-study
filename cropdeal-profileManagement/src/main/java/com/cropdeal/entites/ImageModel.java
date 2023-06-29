package com.cropdeal.entites;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({ "target", "source" })
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageModel {

		@Id
	    private String name;
	    private String type;
	    private byte[] picByte;
	
}