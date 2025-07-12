<div align="center">
  <h1>Gupyfy</h1>
  <p>
    An intelligent application to automate and supercharge your job search on the Gupy platform.
  </p>
  
  <p>
    <img src="https://img.shields.io/badge/status-in%20development-yellow" alt="Project Status"/>
    <img src="https://img.shields.io/badge/Java-17%2B-blue?logo=java&logoColor=white" alt="Java Version"/>
    <img src="https://img.shields.io/badge/Spring_Boot-3%2B-green?logo=spring&logoColor=white" alt="Spring Boot Version"/>
    <img src="https://img.shields.io/badge/React-18%2B-blue?logo=react&logoColor=white" alt="React Version"/>
    <img src="https://img.shields.io/badge/License-MIT-purple" alt="License"/>
  </p>
</div>

---

#### 🇬🇧 **Gupyfy** is a full-stack application that leverages Artificial Intelligence to find, filter, and rank job openings from the Gupy platform according to your profile. Say goodbye to manual searching and get straight to the opportunities that truly matter.

> **Note:** This is a portfolio project and focused on personal use. Its primary goal is to demonstrate modern software engineering skills, from system architecture and back-end development to front-end interfaces and integration with AI services.

<br>

### ✨ Core Features

* **🤖 AI-Powered Matching:** Uses a generative AI to calculate a compatibility score between your profile and each job opening.
* **📊 Smart Filtering:** Automatically extracts key technologies, experience level, and other data for advanced filtering.
* **🖥️ Modern UI:** A clean and responsive web interface built with React to view and manage job openings.
* **⚡ Automated Workflow:** A scheduled back-end service continuously searches for new jobs.
* **🔗 Notification Integration:** Uses n8n to send alerts for high-match jobs via Telegram, Discord, etc.

<br>

### 🚀 Tech Stack

| Area                     | Technologies                                                              |
| ------------------------ | ------------------------------------------------------------------------- |
| **☕ Back-end** | Java 17, Spring Boot, Spring Data JPA, Maven, OkHttp, Jackson, SQLite    |
| **⚛️ Front-end** | React, Vite, Tailwind CSS, Axios                                          |
| **🧠 Artificial Intelligence** | Google Gemini API (or OpenAI)                                     |
| **🔗 Integration** | n8n (Workflow Automation)                                                 |
| **🛠️ DevOps & Tools** | Git, GitHub, Docker (for n8n)                                             |

<br>

### 📂 Directory Structure

The project is organized as a **monorepo** to streamline development and management of both back-end and front-end services.

```

gupyfy/
├── 📁 backend/
│   ├── src/
│   └── pom.xml
│
├── 📁 frontend/
│   ├── src/
│   └── package.json
│
├── .gitignore
└── README.md

````

* **`/backend`**: Contains the entire Java/Spring Boot application responsible for the business logic, API, and data processing.
* **`/frontend`**: Contains the entire React application responsible for the user interface.

<br>

### 🏁 Quick Start

For a detailed guide, please refer to our **[Official Wiki](https://github.com/matheuskaiky/Gupyfy/wiki#--boas-vindas-à-wiki-do-gupyfy)**.

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/matheuskaiky/Gupyfy.git](https://github.com/matheuskaiky/Gupyfy.git)
    cd Gupyfy
    ```

2.  **Configure Environment Variables:**
    * In `/backend/src/main/resources/`, create a `config.properties` file from the example.
    * Add your `ia.api.key` and `n8n.webhook.url`.

3.  **Run the Back-end:**
    ```sh
    cd backend
    mvn spring-boot:run
    ```

4.  **Run the Front-end:**
    ```sh
    cd ../frontend
    npm install
    npm run dev
    ```

<br>

### 🤝 Contributing

Contributions are welcome! Please check the **[Contribution Guide](https://github.com/matheuskaiky/Gupyfy/wiki/How-to-Contribute)** in our Wiki for more details on how to get started.

<br>

### 📜 License

---

#### 🇧🇷 O **Gupyfy** é uma aplicação full-stack que utiliza Inteligência Artificial para encontrar, filtrar e classificar vagas de emprego da plataforma Gupy de acordo com o seu perfil. Diga adeus à busca manual e vá direto para as oportunidades que realmente importam.

> **Nota:** Este é um projeto de portfólio e focado no uso próprio. Seu objetivo principal é demonstrar habilidades de engenharia de software moderna, desde a arquitetura de sistemas e desenvolvimento back-end até a criação de interfaces front-end e integração com serviços de IA.

<br>

### ✨ Principais Funcionalidades

* **🤖 Match com IA:** Utiliza uma IA generativa para calcular uma pontuação de compatibilidade entre seu perfil e cada vaga.
* **📊 Filtragem Inteligente:** Extrai automaticamente tecnologias-chave, nível de experiência e outros dados para filtros avançados.
* **🖥️ UI Moderna:** Uma interface web limpa e responsiva, construída com React, para visualizar e gerenciar as vagas.
* **⚡ Fluxo Automatizado:** Um serviço de back-end agendado busca continuamente por novas vagas.
* **🔗 Integração com Notificações:** Usa o n8n para enviar alertas de vagas com alta compatibilidade via Telegram, Discord, etc.

<br>

### 🚀 Stack de Tecnologias

| Área                     | Tecnologias                                                              |
| ------------------------ | ------------------------------------------------------------------------- |
| **☕ Back-end** | Java 17, Spring Boot, Spring Data JPA, Maven, OkHttp, Jackson, SQLite    |
| **⚛️ Front-end** | React, Vite, Tailwind CSS, Axios                                          |
| **🧠 Inteligência Artificial** | Google Gemini API (ou OpenAI)                                     |
| **🔗 Integração** | n8n (Automação de Workflow)                                               |
| **🛠️ DevOps & Ferramentas**| Git, GitHub, Docker (para o n8n)                                          |

<br>

### 📂 Estrutura de Diretórios

O projeto é organizado como um **monorepo** para agilizar o desenvolvimento e o gerenciamento dos serviços de back-end e front-end.

````

gupyfy/
├── 📁 backend/
│   ├── src/
│   └── pom.xml
│
├── 📁 frontend/
│   ├── src/
│   └── package.json
│
├── .gitignore
└── README.md

````

* **`/backend`**: Contém toda a aplicação Java/Spring Boot responsável pela lógica de negócio, API e processamento de dados.
* **`/frontend`**: Contém toda a aplicação React responsável pela interface do usuário.

<br>

### 🏁 Início Rápido

Para um guia detalhado, por favor, consulte a nossa **[Wiki Oficial](https://github.com/matheuskaiky/Gupyfy/wiki)**.

1.  **Clonar o repositório:**
    ```sh
    git clone [https://github.com/matheuskaiky/Gupyfy.git](https://github.com/matheuskaiky/Gupyfy.git)
    cd Gupyfy
    ```

2.  **Configurar Variáveis de Ambiente:**
    * Em `/backend/src/main/resources/`, crie um arquivo `config.properties` a partir do exemplo.
    * Adicione sua `ia.api.key` e `n8n.webhook.url`.

3.  **Executar o Back-end:**
    ```sh
    cd backend
    mvn spring-boot:run
    ```

4.  **Executar o Front-end:**
    ```sh
    cd ../frontend
    npm install
    npm run dev
    ```

<br>

### 🤝 Como Contribuir

Contribuições são bem-vindas! Por favor, verifique o **[Guia de Contribuição](https://github.com/matheuskaiky/Gupyfy/wiki/How-to-Contribute#--como-contribuir)** em nossa Wiki para mais detalhes sobre como começar.

<br>

### 📜 Licença
