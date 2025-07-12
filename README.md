<div align="center">
  <img src="NO-LOGO-YET" alt="Gupyfy Logo" width="150"/>
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

### 🇬🇧 English

**Gupyfy** is a full-stack application that leverages Artificial Intelligence to find, filter, and rank job openings from the Gupy platform according to your profile. Say goodbye to manual searching and get straight to the opportunities that truly matter.

> **Note:** This is a portfolio project. Its primary goal is to demonstrate modern software engineering skills.

<br>

### 🇧🇷 Português

O **Gupyfy** é uma aplicação full-stack que utiliza Inteligência Artificial para encontrar, filtrar e classificar vagas de emprego da plataforma Gupy de acordo com o seu perfil. Diga adeus à busca manual e vá direto para as oportunidades que realmente importam.

> **Nota:** Este é um projeto de portfólio. Seu objetivo principal é demonstrar habilidades de engenharia de software moderna.

---

### ✨ Core Features

* **🤖 AI-Powered Matching:** Uses a generative AI to calculate a compatibility score between your profile and each job opening.
* **📊 Smart Filtering:** Automatically extracts key technologies, experience level, and other data for advanced filtering.
* **🖥️ Modern UI:** A clean and responsive web interface built with React to view and manage job openings.
* **⚡ Automated Workflow:** A scheduled back-end service continuously searches for new jobs.
* **🔗 Notification Integration:** Uses n8n to send alerts for high-match jobs via Telegram, Discord, etc.

---

### 🚀 Tech Stack

| Area                  | Technologies                                                              |
| --------------------- | ------------------------------------------------------------------------- |
| **☕ Back-end** | Java 17, Spring Boot, Spring Data JPA, Maven, OkHttp, Jackson, SQLite    |
| **⚛️ Front-end** | React, Vite, Tailwind CSS, Axios                                          |
| **🧠 Artificial Intelligence** | Google Gemini API (or OpenAI)                                     |
| **🔗 Integration** | n8n (Workflow Automation)                                                 |
| **🛠️ DevOps & Tools** | Git, GitHub, Docker (for n8n)                                             |

---

### 🏁 Quick Start

For a detailed guide, please refer to our **[Official Wiki](https://github.com/matheuskaiky/Gupyfy/wiki)**.

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/matheuskaiky/Gupyfy.git](https://github.com/matheuskaiky/Gupyfy.git)
    cd Gupyfy
    ```

2.  **Configure Environment Variables:**
    * In `/backend`, create a `config.properties` file.
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

---

### 🤝 Contributing

Contributions are welcome! Please check the **[Contribution Guide](https://github.com/matheuskaiky/Gupyfy/wiki/How-to-Contribute)** in our Wiki for more details on how to get started.

---

### 📜 License

This project is licensed under the MIT License. See the `LICENSE` file for more details.
