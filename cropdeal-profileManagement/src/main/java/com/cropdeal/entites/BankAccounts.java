package com.cropdeal.entites;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "target", "source" })
@Document
public class BankAccounts {
	@Id
	private int id;
	@NotEmpty
    @Size(max = 100)
    private String accountHolderName;

    @NotEmpty
    @Size(max = 18,min = 8)
    private String accountNumber;

    @NotEmpty
    @Pattern(regexp = "[A-Z]{4}[0-9]{7}")
    private String ifscCode;
}
