package com.example.crud_app.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") 
@Data
// @XmlRootElement
@JacksonXmlRootElement(localName = "user")


public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;
    private String email;
}
