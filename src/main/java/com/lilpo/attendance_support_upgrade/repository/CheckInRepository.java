package com.lilpo.attendance_support_upgrade.repository;

import com.lilpo.attendance_support_upgrade.entity.CheckIn;
import com.lilpo.attendance_support_upgrade.entity.Classroom;
import com.lilpo.attendance_support_upgrade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    @Query("SELECT c FROM CheckIn c WHERE c.user = :user AND c.classroom = :classroom AND DATE(c.time) = :date")
    List<CheckIn> findByUserAndClassroomAndDate(
            @Param("user") User user,
            @Param("classroom") Classroom classroom,
            @Param("date") LocalDate date);

    @Query(value = "SELECT c.classroom_id, cl.name, COUNT(c.id) AS absence_count " +
            "FROM check_in c " +
            "JOIN classroom cl ON c.classroom_id = cl.id " +
            "WHERE c.user_id = :userId AND c.status = false " +
            "GROUP BY c.classroom_id, cl.name",
            nativeQuery = true)
    List<Object[]> findClassroomAbsenceSummaryByUserId(@Param("userId") String userId);

//    @Query(value = "SELECT DISTINCT c.time AS absence_datetime " +
//            "FROM check_in c " +
//            "WHERE c.classroom_id = :classroomId AND c.user_id = :userId AND c.status = false",
//            nativeQuery = true)
//    List<Object[]> findAbsenceDateTimeByClassroomAndUserId(
//            @Param("classroomId") Long classroomId,
//            @Param("userId") String userId);

    //    @Query("SELECT DISTINCT c.time FROM CheckIn c WHERE c.user.id = :userId AND c.classroom.id = :classroomId AND c.status = false")
//    List<LocalDateTime> findAbsenceDateTimeByClassroomAndUserId(
//            @Param("userId") String userId,
//            @Param("classroomId") Long classroomId
//    );

    @Query("SELECT distinct c.time from CheckIn c where c.classroom.id=:classroomId AND c.user.id=:userId and c.status = false")
    List<LocalDateTime> findAbsenceDateTimeByClassroomAndUserId(
            @Param("classroomId") Long classroomId,
            @Param("userId") String userId);

    @Query(value = "SELECT u.id AS user_id, u.username, COUNT(c.id) AS absence_count " +
            "FROM check_in c " +
            "JOIN user u ON c.user_id = u.id " +
            "WHERE c.classroom_id = :classroomId AND c.status = false " +
            "GROUP BY u.id, u.username",
            nativeQuery = true)
    List<Object[]> findStudentAbsenceSummaryByClassroomId(@Param("classroomId") Long classroomId);

}
