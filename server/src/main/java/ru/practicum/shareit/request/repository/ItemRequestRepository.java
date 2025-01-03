package ru.practicum.shareit.request.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    @Query("select req from ItemRequest req " +
            "join fetch req.requestor requestor " +
            "where requestor.id = ?1 " +
            "order by req.created desc")
    @EntityGraph(attributePaths = "items")
    List<ItemRequest> findAllByRequestorId(Long userId);

    @Query("select req from ItemRequest req " +
            "join fetch req.requestor requestor " +
            "where requestor.id != ?1 " +
            "order by req.created desc")
    @EntityGraph(attributePaths = "items")
    List<ItemRequest> findAll(Long userId);

}
