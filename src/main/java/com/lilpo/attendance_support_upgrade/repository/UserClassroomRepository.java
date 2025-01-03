package com.lilpo.attendance_support_upgrade.repository;

import com.lilpo.attendance_support_upgrade.entity.Classroom;
import com.lilpo.attendance_support_upgrade.entity.User;
import com.lilpo.attendance_support_upgrade.entity.UserClassroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserClassroomRepository extends JpaRepository<UserClassroom, Long> {
    boolean existsByUserAndClassroom(User user, Classroom classroom);

    Optional<UserClassroom> findByUserIdAndClassroomId(String userId, Long classroomId);

    @Query("SELECT uc.classroom from  UserClassroom uc where uc.user.id=:userId and uc.active = true")
    List<Classroom> findClassroomByUserId(String userId);

    Optional<UserClassroom> findByUserAndClassroom(User user, Classroom classroom);

    @Query("SELECT uc.classroom FROM UserClassroom uc JOIN uc.user u where u.username = :username AND uc.active = true")
    List<Classroom> findClassroomsByUsername(@Param("username") String username);

    List<UserClassroom> findByClassroom(Classroom classroom);

    @Query("SELECT uc FROM UserClassroom uc " +
            "JOIN uc.user u " +
            "JOIN u.roles r " +
            "WHERE uc.classroom = :classroom AND r.name = 'STUDENT'")
    List<UserClassroom> findStudentUserClassrooms(@Param("classroom") Classroom classroom);

}
