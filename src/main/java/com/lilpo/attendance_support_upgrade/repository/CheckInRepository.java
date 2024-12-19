package com.lilpo.attendance_support_upgrade.repository;

import com.lilpo.attendance_support_upgrade.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
}
