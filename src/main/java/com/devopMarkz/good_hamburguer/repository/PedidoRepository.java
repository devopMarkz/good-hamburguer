package com.devopMarkz.good_hamburguer.repository;

import com.devopMarkz.good_hamburguer.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
