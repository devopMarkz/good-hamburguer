package com.devopMarkz.good_hamburguer.repository;

import com.devopMarkz.good_hamburguer.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
