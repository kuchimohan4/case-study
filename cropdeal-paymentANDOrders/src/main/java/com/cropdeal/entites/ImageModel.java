package com.cropdeal.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
//import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties({ "target", "source" })
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageModel {

		@Id
		@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
		@GenericGenerator(name = "native",strategy = "native")
		private Integer imgId;
	    private String name;
	    private String type;
	    private byte[] picByte;
		@Temporal(TemporalType.TIMESTAMP)
		private LocalDateTime uploadTime;
	
}