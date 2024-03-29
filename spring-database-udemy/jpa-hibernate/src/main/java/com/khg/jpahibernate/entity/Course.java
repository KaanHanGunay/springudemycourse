package com.khg.jpahibernate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
@NamedQueries(value = {
        @NamedQuery(name = "query_get_all_courses", query = "select c from Course c"),
        @NamedQuery(name = "query_get_all_courses_join_fetch", query = "select c from Course c JOIN fetch c.students s"), // N+1 Problemi için
        @NamedQuery(name = "query_get_start_with_t", query = "Select c from Course c where name like 'T%'")
})
@Cacheable  // Second Level Active
@SQLDelete(sql = "update course set is_deleted=true where id=?")  // Soft delete işleminde uygulanacak sql
@Where(clause = "is_deleted = false")  // Her sorguya eklenecek komut, siliniş ise getirme demek için
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)  // Default fetch is lazy
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore  // Rest üzerinden geri döndürüldüğünde recursive olmaması için ignore eklenmiştir.
    private List<Student> students = new ArrayList<>();

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;  // Java 8 den önce java.util.time kullanılıyor.

    @CreationTimestamp
    private LocalDateTime createdDate;

    private Boolean isDeleted;

    @PreRemove
    private void preRemove() {
        isDeleted = true;  // Silinmeden önce aktif hale geldiğinden emin olmak için
    }

    public Course() {}

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public void removeCourse(Student student) {
        this.students.remove(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    @Override
    public String toString() {
        return getName();
    }
}
