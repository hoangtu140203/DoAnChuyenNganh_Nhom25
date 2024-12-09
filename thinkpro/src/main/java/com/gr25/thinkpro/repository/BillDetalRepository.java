package com.gr25.thinkpro.repository;

import com.gr25.thinkpro.domain.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetalRepository extends JpaRepository<BillDetail, Long> {
}
