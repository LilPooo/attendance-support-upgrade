package com.lilpo.attendance_support_upgrade.repository;

import com.lilpo.attendance_support_upgrade.entity.CheckIn;
import com.lilpo.attendance_support_upgrade.entity.Classroom;
import com.lilpo.attendance_support_upgrade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    @Query("SELECT c FROM CheckIn c WHERE c.user = :user AND c.classroom = :classroom AND DATE(c.time) = :date")
    List<CheckIn> findByUserAndClassroomAndDate(
            @Param("user") User user,
            @Param("classroom") Classroom classroom,
            @Param("date") LocalDate date);


}
