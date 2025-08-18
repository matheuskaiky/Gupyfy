package br.com.matheuskaiky.gupyfy.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a Job entity that will be stored in the database.
 * Each instance of this class corresponds to a row in the 'jobs' table.
 */
@Entity // 1. Marca esta classe como uma entidade JPA, ou seja, ela representa uma tabela.
@Table(name = "jobs") // 2. (Opcional, mas boa prática) Especifica o nome da tabela no banco.
public class Job {

    @Id // 3. Marca este campo como a chave primária (Primary Key) da tabela.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 4. Configura a geração automática do ID.
    private Long id;

    private String title;
    private String companyName;
    private String url;

    // 5. O JPA precisa de um construtor sem argumentos para funcionar.
    public Job() {
    }

    // Construtor para facilitar a criação de novos objetos Job
    public Job(String title, String companyName, String url) {
        this.title = title;
        this.companyName = companyName;
        this.url = url;
    }

    // Getters e Setters são necessários para o JPA acessar os campos.
    // A sua IDE pode gerar isso para você (Alt+Insert no IntelliJ).

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}