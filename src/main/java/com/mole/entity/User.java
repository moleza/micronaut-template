package com.mole.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 50, columnDefinition = "VARCHAR(50)")
    @NotBlank
    @NotNull
    private String name;
    @Column(length = 50, columnDefinition = "VARCHAR(50)")
    @NotBlank
    @NotNull
    private String surname;
    @Email
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;
    @Column(length = 100, columnDefinition = "VARCHAR(100)")
    @NotBlank
    @NotNull
    @JsonIgnore
    private String password;
    @NotBlank
    @NotNull
    @Column(columnDefinition = "boolean default true")
    private Boolean admin;
    @Column(length = 65535,columnDefinition="Text")
    private String venues;
}
