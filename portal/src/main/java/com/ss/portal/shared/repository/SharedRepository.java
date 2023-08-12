package com.ss.portal.shared.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedRepository<T, ID> extends JpaRepository<T, ID>,
    JpaSpecificationExecutor<T> {

    interface Specs {
        static <K> Specification<K> byTeamId(Long teamId) {
            return (root, query, builder) -> {
                var teamJoin = root.join("teams");
                return builder.equal(teamJoin.get("id"), teamId);
            };
        }

        static <K> Specification<K> byUserId(Long userId) {
            return (root, query, builder) -> {
                var userJoin = root.join("users");
                return builder.equal(userJoin.get("id"), userId);
            };
        }

        static <K> Specification<K> byGameId(Long gameId) {
            return (root, query, builder) -> {
                var gameJoin = root.join("games");
                return builder.equal(gameJoin.get("id"), gameId);
            };
        }

        static <K> Specification<K> byMatchId(Long matchId) {
            return (root, query, builder) -> {
                var matchJoin = root.join("matches");
                return builder.equal(matchJoin.get("id"), matchId);
            };
        }
    }

}
