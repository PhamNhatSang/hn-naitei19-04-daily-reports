package com.example.G4_DailyReport.repository;

import com.example.G4_DailyReport.model.Department;
import com.example.G4_DailyReport.model.Project;
import com.example.G4_DailyReport.model.ProjectMember;
import com.example.G4_DailyReport.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserName(String username);
    Page<User> findByDepartment(Department department, Pageable pageable);
    Page<User> findByDepartmentIsNull(Pageable pageable);
    List<User> findByDepartmentIsNull();
    List<User> findByDepartment(Department department);
    Page<User> findAllByDepartmentIdAndPositionName(UUID department, String position, Pageable pageable);
    Page<User> findAllByDepartmentIsNullAndPositionName(String position, Pageable pageable);
    Page<User> findAllByDepartmentIsNullAndPositionNameAndNameContaining(String position, String name, Pageable pageable);
    @Query("SELECT pm.user FROM ProjectMember pm " +
           "JOIN pm.project p " +
           "JOIN pm.user u " +
           "WHERE p.id = ?1" +
           "AND u.roles LIKE '%'+?2+'%'")
    List<User> findAllMemberInProjectByProjectIdAndRoles(UUID projectId, String roles);
    @Query(value = "select u.userName, u.id from User u WHERE u.name in ?1")
    Map<String, UUID> findAllUserIdByUserName(Set<String> UserName);
}
