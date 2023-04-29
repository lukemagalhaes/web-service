package com.example.demo.conta;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@SpringBootApplication
@Table(name = "produtos")
@Entity(name = "produtos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // id, descrição, marca, preço
    private Long id;
    private String descricao;
    private String marca;
    private double preco;

    public Produto(ProdutoRequestDTO data) {
        this.descricao = data.descricao();
        this.marca = data.marca();
        this.preco = data.preco();

    }
}
