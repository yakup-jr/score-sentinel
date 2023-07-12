package com.ss.portal.roles.repository;

import com.ss.portal.roles.entity.RoleEntity;
import com.ss.portal.roles.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(@Param("name") RoleEnum name);

    @Modifying
    @Transactional
    @Query("update RoleEntity r set r.name = :name where r.id = :id")
    void updateNameById(@Param("id") Long id, @Param("name") RoleEnum name);

    void deleteById(@Param("id") Long id);

    void deleteByName(@Param("name") RoleEnum name);
}
