package br.com.matheuskaiky.gupyfy.repository;

import br.com.matheuskaiky.gupyfy.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is the data access layer for the Job entity.
 * Spring Data JPA will automatically implement all the basic CRUD methods.
 */
@Repository // (Opcional, mas boa prática) Indica que esta é uma interface de repositório.
public interface JobRepository extends JpaRepository<Job, Long> {
    // Ao estender JpaRepository<Job, Long>, nós ganhamos métodos como:
    // save(), findById(), findAll(), deleteById(), etc., sem escrever nenhum código!
    // O 'Job' diz qual entidade gerenciar, e o 'Long' diz o tipo da chave primária (nosso ID).
}