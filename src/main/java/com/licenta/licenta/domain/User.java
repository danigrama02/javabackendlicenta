package com.licenta.licenta.domain;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;



@Document(collection = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private ObjectId _id;

    private String username;

    private String password;
}
