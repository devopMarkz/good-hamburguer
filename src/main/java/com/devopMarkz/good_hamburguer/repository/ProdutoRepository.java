package com.devopMarkz.good_hamburguer.repository;

import com.devopMarkz.good_hamburguer.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
