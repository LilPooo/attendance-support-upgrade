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

    @Query(value = "SELECT cl.id AS classroom_id, cl.name AS classroom_name, COALESCE(COUNT(c.id), 0) AS absence_count " +
            "FROM classroom cl " +
            "LEFT JOIN check_in c ON c.classroom_id = cl.id AND c.user_id = :userId AND c.status = FALSE " +
            "WHERE cl.id IN (SELECT uc.classroom_id FROM user_classroom uc WHERE uc.user_id = :userId) " +
            "GROUP BY cl.id, cl.name",
            nativeQuery = true)
    List<Object[]> findClassroomAbsenceSummaryByUserId(@Param("userId") String userId);


    @Query("SELECT distinct c.time from CheckIn c where c.classroom.id=:classroomId AND c.user.id=:userId and c.status = false")
    List<LocalDateTime> findAbsenceDateTimeByClassroomAndUserId(
            @Param("classroomId") Long classroomId,
            @Param("userId") String userId);


    @Query(value = """
                SELECT 
                    u.id AS user_id, 
                    u.username, 
                    COALESCE(SUM(CASE WHEN c.status = false THEN 1 ELSE 0 END), 0) AS absence_count
                FROM 
                    user u
                LEFT JOIN 
                    check_in c ON u.id = c.user_id AND c.classroom_id = :classroomId
                WHERE 
                    EXISTS (
                        SELECT 1 
                        FROM user_classroom cu 
                        WHERE cu.user_id = u.id AND cu.classroom_id = :classroomId
                    )
                GROUP BY 
                    u.id, u.username
            """, nativeQuery = true)
    List<Object[]> findStudentAbsenceSummaryByClassroomId(@Param("classroomId") Long classroomId);

}
