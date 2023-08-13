package com.ss.portal.shared.repository;

import com.ss.portal.team.entity.TeamEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Specs")
class SpecsTest {

    @Test
    @DisplayName("Should return the correct specification when the user ID is provided")
    void byUserIdWhenUserIdIsProvided() {
        Long userId = 1L;

        Specification<Object> specification = SharedRepository.Specs.byUserId(userId);

        assertNotNull(specification);

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        Root root = mock(Root.class);

        Predicate predicate =
            specification.toPredicate(root, criteriaQuery, criteriaBuilder);

        assertNotNull(predicate);
        verify(root, times(1)).join("users");
        verify(criteriaBuilder, times(1)).equal(any(), eq(userId));
    }

    @Test
    @DisplayName("Should throw an exception when the teamId is null")
    void byTeamIdWhenTeamIdIsNullThenThrowException() {
        assertThrows(IllegalArgumentException.class,
            () -> SharedRepository.Specs.byTeamId(null));
    }

    @Test
    @DisplayName("Should return the correct specification when the teamId is provided")
    void byTeamIdWhenTeamIdIsProvided() {
        Long teamId = 1L;
        Specification<TeamEntity> expectedSpecification = (root, query, builder) -> {
            var teamJoin = root.join("teams");
            return builder.equal(teamJoin.get("id"), teamId);
        };

        Specification<TeamEntity> actualSpecification =
            SharedRepository.Specs.byTeamId(teamId);

        assertEquals(expectedSpecification, actualSpecification);
    }
}