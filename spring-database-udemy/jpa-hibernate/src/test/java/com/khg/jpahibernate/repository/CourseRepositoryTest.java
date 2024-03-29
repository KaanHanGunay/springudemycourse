package com.khg.jpahibernate.repository;

import com.khg.jpahibernate.JpaHibernateApplication;
import com.khg.jpahibernate.entity.Course;
import com.khg.jpahibernate.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
public class CourseRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void findById_Basic() {
        Course course = courseRepository.findById(1001L);
        assertEquals("Test2", course.getName());
    }

    @Test
    @Transactional  // Transactional olmasa 1st level cached uygulanmayacktı. Aynı transaction da tüm işlemler yapıldığı için
    public void findById_FirstLevelCache() {
        Course course = courseRepository.findById(1001L);
        assertEquals("Test2", course.getName());
        Course course2 = courseRepository.findById(1001L);  // Aynı ver için tekrar sorgu gönderilmiyor. 1st level cached
        assertEquals("Test2", course2.getName());
    }

    @Test
    @DirtiesContext  // Verileri test öncesi hale getir
    public void deleteById_Basic() {
        courseRepository.deleteById(1008L);
        assertNull(courseRepository.findById(1008L));
    }

    @Test
    @DirtiesContext
    public void save_Basic() {
        // Get a courser
        Course course = courseRepository.findById(1001L);
        assertEquals("Test2", course.getName());
        // Update details
        course.setName("Test3");

        courseRepository.save(course);

        // Check value
        Course course1 = courseRepository.findById(1001L);
        assertEquals("Test3", course1.getName());
    }

    @Test
    @DirtiesContext
    public void playWithEntityManager_Basic() {
        courseRepository.playWithEntityManager();
    }

    @Test
    @Transactional
    public void retrieveReviewsForCourse() {
        Course course = courseRepository.findById(1001L);
        logger.info("1001L ID -> {}", course.getReviews());
    }

    @Test
    @Transactional
    public void retrieveCourseForReview() {
        Review review = entityManager.find(Review.class, 5001L);
        logger.info("5001L ID -> {}", review.getCourse());
    }

    /**
     * N+1 Problemi
     * Tüm kurslar sorgulandığında öğrenciler lazy fetch olduğu için dönmemekte
     * For döngüsü her döndüğünde tekrar veri tabanı sorgusu oluşturark sorgu trafiği meydana gelmektedir
     * Bunun önüne geçmek için Entity Graph Kullanılmaktadır.
     */
    @Test
    @Transactional
    public void nPlusOneProblem_EntityGraph() {
        EntityGraph<Course> courseEntityGraph = entityManager.createEntityGraph(Course.class);
        courseEntityGraph.addSubgraph("students");  // N+1 problemi oluşturacak alan
        List<Course> courses = entityManager.createNamedQuery("query_get_all_courses", Course.class)
                .setHint("javax.persistence.loadgraph", courseEntityGraph)
                .getResultList();
        for (Course course:courses) {
            logger.info("Course -> {} and Students -> {}", course, course.getStudents());
        }
    }

    /**
     * N+1 Problemi - Çözüm 2
     * Query Join Fetch ile oluşturulur.
     */
    @Test
    @Transactional
    public void nPlusOneProblem_JoinFetch() {
        List<Course> courses = entityManager.createNamedQuery("query_get_all_courses_join_fetch", Course.class)
                .getResultList();
        for (Course course:courses) {
            logger.info("Course -> {} and Students -> {}", course, course.getStudents());
        }
    }
}