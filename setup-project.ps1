# Script para criar a estrutura de diretórios e arquivos iniciais do projeto Gupyfy.
# Execute este script a partir da pasta raiz do seu repositório local.

# Limpa a tela para uma visualização limpa
Clear-Host

Write-Host "🚀 Iniciando a criação da estrutura de diretórios para o Gupyfy..." -ForegroundColor Yellow
Write-Host "------------------------------------------------------------"

# --- Criação da Estrutura do Back-end ---
Write-Host "☕ Criando diretórios do Back-end (Java/Spring Boot)..." -ForegroundColor Cyan

try {
    # Cria a árvore de diretórios principal do Maven de uma só vez
    mkdir "backend/src/main/java/br/com/matheuskaiky/gupyfy" -ErrorAction Stop
    mkdir "backend/src/main/resources" -ErrorAction Stop
    mkdir "backend/src/test/java/br/com/matheuskaiky/gupyfy" -ErrorAction Stop

    # Cria arquivos de configuração vazios
    New-Item -Path "backend/pom.xml" -ItemType File | Out-Null
    New-Item -Path "backend/src/main/resources/application.properties" -ItemType File | Out-Null
    # Cria o arquivo de exemplo para as chaves de API
    New-Item -Path "backend/src/main/resources/config.properties.example" -ItemType File | Out-Null

    Write-Host "   -> Diretórios do Back-end criados com sucesso." -ForegroundColor Green
}
catch {
    Write-Host "   -> ERRO ao criar diretórios do Back-end: $_" -ForegroundColor Red
}

Write-Host "" # Linha em branco para espaçamento

# --- Criação da Estrutura do Front-end ---
Write-Host "⚛️  Criando diretórios do Front-end (React)..." -ForegroundColor Cyan

try {
    # Cria os diretórios padrão do React
    mkdir "frontend/public" -ErrorAction Stop
    mkdir "frontend/src/components" -ErrorAction Stop
    mkdir "frontend/src/pages" -ErrorAction Stop
    mkdir "frontend/src/services" -ErrorAction Stop # Pasta para chamadas de API

    # Cria um package.json vazio como placeholder
    New-Item -Path "frontend/package.json" -ItemType File | Out-Null

    Write-Host "   -> Diretórios do Front-end criados com sucesso." -ForegroundColor Green
}
catch {
    Write-Host "   -> ERRO ao criar diretórios do Front-end: $_" -ForegroundColor Red
}

Write-Host "" # Linha em branco para espaçamento

# --- Criação de Arquivos na Raiz ---
Write-Host "📄 Criando arquivos na raiz do projeto..." -ForegroundColor Cyan

try {
    # Cria um .gitignore já com conteúdo útil para projetos Java e Node.js
    $gitIgnoreContent = @"
# Compiled Java files
*.class
*.jar
*.war
*.ear
target/

# Node.js
node_modules/
dist/
.npm/
npm-debug.log*
yarn-debug.log*
yarn-error.log*

# IDE files
.idea/
*.iml
.vscode/
*.suo
*.ntvs*
*.njsproj
*.sln
"@
    
    $gitIgnoreContent | Out-File -FilePath ".gitignore" -Encoding utf8
    
    Write-Host "   -> Arquivo .gitignore criado com sucesso." -ForegroundColor Green
}
catch {
    Write-Host "   -> ERRO ao criar .gitignore: $_" -ForegroundColor Red
}

Write-Host "------------------------------------------------------------"
Write-Host "✅ Estrutura de diretórios do Gupyfy criada com sucesso!" -ForegroundColor Yellow
Write-Host "Próximo passo: Inicie os projetos com o Spring Initializr (copiando para a pasta 'backend') e Vite (na pasta 'frontend')."