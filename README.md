<div align="center">
  <h1>Gupyfy</h1>
  <p>
    An intelligent job search assistant for the Gupy platform, powered by AI-driven matching and automation.
  </p>
  
  <p>
    <img src="https://img.shields.io/badge/status-paused%20(architecture%20stable)-orange" alt="Project Status"/>
    <img src="https://img.shields.io/badge/Java-17%2B-blue?logo=java&logoColor=white" alt="Java Version"/>
    <img src="https://img.shields.io/badge/Spring_Boot-3%2B-green?logo=spring&logoColor=white" alt="Spring Boot Version"/>
    <img src="https://img.shields.io/badge/React-18%2B-blue?logo=react&logoColor=white" alt="React Version"/>
    <img src="https://img.shields.io/badge/License-MIT-purple" alt="License"/>
  </p>
</div>

---

#### 🇬🇧 **Gupyfy** is a full-stack application that leverages Artificial Intelligence to find, filter, and rank job openings from the Gupy platform according to your professional profile. Automate the tedious parts of job searching and focus on the opportunities that truly matter.

> **Project Status:** Currently paused to focus on [Termo Fácil](https://github.com/matheuskaiky/termo-facil), a government AI research project. The architecture is stable and well-documented — resuming development is straightforward. This is a portfolio project demonstrating modern software architecture, full-stack development, and AI integration.

### ✨ Core Features

* **🤖 AI-Powered Matching:** Generative AI calculates compatibility scores between your profile and job openings based on skills, experience, and expectations.
* **📊 Smart Filtering:** Automatically extracts technologies, seniority levels, salary ranges, and other structured data from job listings.
* **🖥️ Modern UI:** Clean, responsive React interface for browsing, filtering, and managing opportunities.
* **⚡ Automated Workflow:** Scheduled back-end service continuously monitors the Gupy platform for new relevant positions.
* **🔔 Smart Notifications:** Integrates with n8n to send curated alerts via Telegram, Discord, or Email for high-match opportunities.

### 🏗️ Architecture Highlights

**Backend (Java/Spring Boot):**
- RESTful API with Spring Data JPA for ORM
- Scheduled job processing for web scraping and data enrichment
- AI integration layer (Google Gemini / OpenAI)
- PostgreSQL for persistence (Supabase for zero-ops hosting)

**Frontend (React + Vite):**
- Component-driven UI with Tailwind CSS
- Real-time job feed with advanced filtering
- User preference management and saved searches

**Integration:**
- n8n workflows for notification orchestration
- Web scraping to extract Gupy job listings
- API-first design for extensibility

### 🚀 Tech Stack

| Area                        | Technologies                                                                         |
| ----------------------      | ------------------------------------------------------------------------------------- |
| **☕ Back-end**             | Java 17, Spring Boot 3, Spring Data JPA, Maven, OkHttp, Jackson, PostgreSQL          |
| **⚛️ Front-end**            | React 18, Vite, Tailwind CSS, Axios, React Router                                    |
| **🧠 AI Integration**       | Google Gemini API (or OpenAI GPT)                                                     |
| **🔗 Automation**           | n8n (Workflow Orchestration & Notifications)                                         |
| **☁️ Cloud & DevOps**       | Supabase (PostgreSQL), Docker, GitHub Actions, Git                                   |

### 📂 Project Structure

The project follows a **monorepo** pattern for streamlined development of both services:

```
gupyfy/
├── 📁 backend/
│   ├── src/
│   │   ├── main/java/com/gupyfy/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── entity/
│   │   │   └── config/
│   │   └── resources/
│   │       └── application.properties
│   └── pom.xml
│
├── 📁 frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── styles/
│   ├── package.json
│   └── vite.config.js
│
├── .gitignore
├── .env.example
└── README.md
```

* **`/backend`**: Spring Boot application handling business logic, data processing, AI integration, and job scheduling.
* **`/frontend`**: React SPA for user interactions, job discovery, and profile management.

### 🏁 Quick Start

For detailed documentation, see the **[Wiki](https://github.com/matheuskaiky/Gupyfy/wiki)**.

1. **Clone the repository:**
   ```sh
   git clone https://github.com/matheuskaiky/Gupyfy.git
   cd Gupyfy
   ```

2. **Backend Setup:**
   ```sh
   cd backend
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   # Edit application.properties with your API keys and DB credentials
   mvn clean install
   mvn spring-boot:run
   ```
   The API will be available at `http://localhost:8080`

3. **Frontend Setup:**
   ```sh
   cd ../frontend
   npm install
   cp .env.example .env.local
   # Edit .env.local with your backend URL
   npm run dev
   ```
   The UI will be available at `http://localhost:5173`

### 🔐 Environment Variables

**Backend** (`application.properties`):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gupyfy
spring.datasource.username=your_user
spring.datasource.password=your_password
ai.provider=GOOGLE_GEMINI
ai.api.key=your_api_key
n8n.webhook.url=https://your-n8n-instance.com/webhook/gupyfy
```

**Frontend** (`.env.local`):
```env
VITE_API_BASE_URL=http://localhost:8080
VITE_APP_NAME=Gupyfy
```

### 🛠️ Development

**Build backend:**
```sh
cd backend && mvn clean package
```

**Build frontend:**
```sh
cd frontend && npm run build
```

**Run tests:**
```sh
cd backend && mvn test
cd ../frontend && npm test
```
---

#### 🇧🇷 O **Gupyfy** é uma aplicação full-stack que utiliza Inteligência Artificial para encontrar, filtrar e classificar vagas de emprego da plataforma Gupy de acordo com seu perfil profissional. Automatize as partes tediosas da busca de emprego e foque nas oportunidades que realmente importam.

> **Status do Projeto:** Atualmente pausado para focar no [Termo Fácil](https://github.com/matheuskaiky/termo-facil), um projeto de pesquisa em IA para o governo. A arquitetura está estável e bem documentada — retomar o desenvolvimento é direto. Este é um projeto de portfólio que demonstra arquitetura de software moderna, desenvolvimento full-stack e integração com IA.

### ✨ Principais Funcionalidades

* **🤖 Matching com IA:** Inteligência Artificial generativa calcula pontuações de compatibilidade entre seu perfil e oportunidades baseadas em skills, experiência e expectativas.
* **📊 Filtragem Inteligente:** Extrai automaticamente tecnologias, níveis de senioridade, faixas salariais e outros dados estruturados das vagas.
* **🖥️ UI Moderna:** Interface React limpa e responsiva para navegar, filtrar e gerenciar oportunidades.
* **⚡ Fluxo Automatizado:** Serviço de back-end agendado monitora continuamente a plataforma Gupy por novas posições relevantes.
* **🔔 Notificações Inteligentes:** Integra com n8n para enviar alertas curados via Telegram, Discord ou Email para oportunidades de alta compatibilidade.

### 🏗️ Destaques da Arquitetura

**Backend (Java/Spring Boot):**
- API RESTful com Spring Data JPA para ORM
- Processamento de jobs agendados para scraping e enriquecimento de dados
- Camada de integração com IA (Google Gemini / OpenAI)
- PostgreSQL para persistência (Supabase para hosting zero-ops)

**Frontend (React + Vite):**
- UI component-driven com Tailwind CSS
- Feed de vagas em tempo real com filtros avançados
- Gerenciamento de preferências do usuário e buscas salvas

**Integração:**
- Workflows n8n para orquestração de notificações
- Web scraping para extrair vagas da plataforma Gupy
- Design API-first para extensibilidade

### 🚀 Stack de Tecnologias

| Área                        | Tecnologias                                                                       |
| ----------------------      | ----------------------------------------------------------------------------------- |
| **☕ Back-end**             | Java 17, Spring Boot 3, Spring Data JPA, Maven, OkHttp, Jackson, PostgreSQL        |
| **⚛️ Front-end**            | React 18, Vite, Tailwind CSS, Axios, React Router                                  |
| **🧠 Integração com IA**    | Google Gemini API (ou OpenAI GPT)                                                   |
| **🔗 Automação**            | n8n (Orquestração de Workflows & Notificações)                                     |
| **☁️ Cloud & DevOps**       | Supabase (PostgreSQL), Docker, GitHub Actions, Git                                 |

### 📂 Estrutura do Projeto

O projeto segue um padrão **monorepo** para desenvolvimento ágil de ambos os serviços:

```
gupyfy/
├── 📁 backend/
│   ├── src/
│   │   ├── main/java/com/gupyfy/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── entity/
│   │   │   └── config/
│   │   └── resources/
│   │       └── application.properties
│   └── pom.xml
│
├── 📁 frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── styles/
│   ├── package.json
│   └── vite.config.js
│
├── .gitignore
├── .env.example
└── README.md
```

* **`/backend`**: Aplicação Spring Boot responsável pela lógica de negócio, processamento de dados, integração com IA e agendamento de jobs.
* **`/frontend`**: SPA React para interações do usuário, descoberta de vagas e gerenciamento de perfil.

### 🏁 Início Rápido

Para documentação detalhada, veja a **[Wiki](https://github.com/matheuskaiky/Gupyfy/wiki)**.

1. **Clone o repositório:**
   ```sh
   git clone https://github.com/matheuskaiky/Gupyfy.git
   cd Gupyfy
   ```

2. **Setup do Backend:**
   ```sh
   cd backend
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   # Edite application.properties com suas chaves de API e credenciais do BD
   mvn clean install
   mvn spring-boot:run
   ```
   A API estará disponível em `http://localhost:8080`

3. **Setup do Frontend:**
   ```sh
   cd ../frontend
   npm install
   cp .env.example .env.local
   # Edite .env.local com a URL do seu backend
   npm run dev
   ```
   A UI estará disponível em `http://localhost:5173`

### 🔐 Variáveis de Ambiente

**Backend** (`application.properties`):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gupyfy
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
ai.provider=GOOGLE_GEMINI
ai.api.key=sua_api_key
n8n.webhook.url=https://sua-instancia-n8n.com/webhook/gupyfy
```

**Frontend** (`.env.local`):
```env
VITE_API_BASE_URL=http://localhost:8080
VITE_APP_NAME=Gupyfy
```

### 🛠️ Desenvolvimento

**Build do backend:**
```sh
cd backend && mvn clean package
```

**Build do frontend:**
```sh
cd frontend && npm run build
```

**Executar testes:**
```sh
cd backend && mvn test
cd ../frontend && npm test
```
