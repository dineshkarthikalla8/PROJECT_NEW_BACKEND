package com.example.khatabackend.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String mobile;

    private String gender;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd") // 👈 ensure JSON date parsing works
    private LocalDate dob;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER"; // default role

    // ✅ Make age persistent in DB (remove @Transient)
    @Column(nullable = false)
    private Integer age;

    public User() {}

    // ---------- Getters & Setters ----------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    // ✅ Auto-calculate age when DOB is set
    @PrePersist
    @PreUpdate
    public void calculateAge() {
        if (this.dob != null) {
            this.age = Period.between(this.dob, LocalDate.now()).getYears();
        }
    }
}
