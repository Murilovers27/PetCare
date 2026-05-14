# 🧪 GUIA DE TESTES - SISTEMA PETCARE

## Como Executar os Testes Automatizados

### 1️⃣ Pré-requisitos
- ✅ Servidor Spring Boot rodando em `http://localhost:8080`
- ✅ Banco de dados MySQL conectado
- ✅ Arquivo `frontend/index.html` aberto no navegador

### 2️⃣ Acessar Testes Automatizados
1. Abra `frontend/index.html` no navegador
2. Clique na aba **🧪 Testes** (última aba)
3. Você verá 6 testes disponíveis

---

## 📋 Testes Disponíveis

### Teste 1: ✅ Fluxo Completo
**O que valida:** Cadastro de Tutor → Animal → Veterinário → Consulta

**Execução:**
- Clique em "Executar"
- O sistema automaticamente:
  1. Cria um tutor
  2. Cria um veterinário
  3. Cria um animal vinculado ao tutor
  4. Agenda uma consulta para esse animal

**Resultado esperado:** ✅ Status SUCCESS com IDs gerados

---

### Teste 2: ⚠️ Conflito de Agenda
**O que valida:** Sistema não permite 2 consultas no mesmo horário para o mesmo veterinário

**Execução:**
- Clique em "Executar"
- O sistema:
  1. Cria um veterinário
  2. Agenda 1ª consulta para ele (horário 15:30)
  3. Tenta agendar 2ª consulta no MESMO horário
  4. Esperado: Rejeição

**Resultado esperado:** ✅ Status ERROR "Horário já ocupado!"

**Regra no código:**
```java
// ConsultaService.java
boolean conflito = repository.existsByVeterinarioAndData(
    consulta.getVeterinario(),
    consulta.getData()
);
if (conflito) {
    throw new RuntimeException("Horário já ocupado!");
}
```

---

### Teste 3: 🔒 Especialidade Incompatível
**O que valida:** Veterinário só pode atender animais da sua especialidade

**Execução:**
- Clique em "Executar"
- O sistema:
  1. Cria veterinário especialista em "Gato"
  2. Cria um "Cachorro"
  3. Tenta agendar consulta (Cachorro com vet de Gato)
  4. Esperado: Rejeição

**Resultado esperado:** ✅ Status ERROR "Veterinário não atende essa espécie!"

**Regra no código:**
```java
// ConsultaService.java
if (!consulta.getVeterinario().getEspecialidade()
        .equalsIgnoreCase(consulta.getAnimal().getEspecie())) {
    throw new RuntimeException("Veterinário não atende essa espécie!");
}
```

---

### Teste 4: 🔗 Associação Tutor-Animal
**O que valida:** Cada animal está sempre vinculado a um tutor

**Execução:**
- Clique em "Executar"
- O sistema:
  1. Cria um tutor
  2. Cria um animal vinculado a esse tutor
  3. Busca o animal por ID
  4. Verifica se o `tutor` está presente na resposta

**Resultado esperado:** ✅ Animal encontrado com Tutor associado

**Como funciona no banco:**
```sql
CREATE TABLE animal (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    especie VARCHAR(50),
    tutor_id BIGINT NOT NULL,
    FOREIGN KEY (tutor_id) REFERENCES tutor(id)
);
```

---

### Teste 5: 📝 Histórico Completo (Prontuário)
**O que valida:** Sistema registra histórico completo de cada animal

**Execução:**
- Clique em "Executar"
- O sistema:
  1. Cria um animal
  2. Cria 3 prontuários para esse animal
  3. Lista prontuários via `GET /prontuarios/animal/{id}`
  4. Verifica se retorna todos os 3 registros

**Resultado esperado:** ✅ 3 registros de prontuário encontrados

**Endpoints usados:**
- `POST /prontuarios` - Criar novo prontuário
- `GET /prontuarios/animal/{id}` - Listar histórico

---

### Teste 6: 🔍 Busca de Animal
**O que valida:** Endpoint GET funciona corretamente

**Execução:**
- Clique em "Executar"
- O sistema:
  1. Cria um animal
  2. Faz GET /animais/{id}
  3. Verifica se retorna os dados corretos

**Resultado esperado:** ✅ Animal encontrado com dados íntegros

---

## 🎯 Executar Todos os Testes de Uma Vez

**Botão:** "Executar Todos os Testes"

- Executa os 6 testes em sequência
- Mostra resumo final com:
  - Total de testes
  - Testes que passaram ✅
  - Testes que falharam ❌

---

## 📊 Resumo de Validação

### ✅ Módulos Obrigatórios
- [x] Cadastro de Animais
- [x] Agendamento de Consultas
- [x] Prontuário e Vacinação

### ✅ Regras Obrigatórias
- [x] Não permitir conflito de agenda
- [x] Veterinário atende apenas sua especialidade
- [x] Registro completo de histórico
- [x] Associação tutor ↔ animal

### ✅ Critérios Técnicos
- [x] Entendimento de Service (lógica no Service, não no Controller)
- [x] Modelagem de regras reais
- [x] Evolução do CRUD básico
- [x] Testes unitários (ConsultaServiceTest.java)
- [x] Tratamento de exceções

---

## 🔧 Troubleshooting

### Problema: "Servidor desconectado"
**Solução:** Verifique se a aplicação Spring Boot está rodando
```bash
mvn spring-boot:run
```

### Problema: "Erro ao criar tutor"
**Solução:** Verifique se o banco de dados está conectado
```bash
# Verificar status do MySQL
mysql -u petcare_user -p
```

### Problema: Testes não encontram dados
**Solução:** Limpe o banco e reinicie
```sql
TRUNCATE TABLE consulta;
TRUNCATE TABLE animal;
TRUNCATE TABLE veterinario;
TRUNCATE TABLE tutor;
```

---

## 📝 Testes Unitários (JUnit)

Rodando do terminal:
```bash
mvn test
```

Arquivo: `src/test/java/com/patcare/facens/services/ConsultaServiceTest.java`

**Testes implementados:**
- ✅ `deveAgendarConsultaComSucesso()`
- ✅ `naoDevePermitirHorarioDuplicado()`
- ✅ `naoDevePermitirEspecialidadeInvalida()`

---

## 📚 Arquitetura do Projeto

```
src/main/java/com/patcare/facens/
├── FacensApplication.java         (Main)
├── controller/
│   ├── AnimalController.java       (REST endpoints)
│   ├── ConsultaController.java
│   ├── TutorController.java
│   ├── VeterinarioController.java
│   └── ProntuarioController.java
├── services/
│   ├── AnimalService.java          (Lógica de negócio)
│   ├── ConsultaService.java        (Validações)
│   ├── ProntuarioService.java
│   └── ...
├── repository/
│   ├── AnimalRepository.java       (Acesso a dados)
│   ├── ConsultaRepository.java
│   └── ...
└── entity/
    ├── Animal.java                 (Entidades JPA)
    ├── Consulta.java
    ├── Tutor.java
    ├── Veterinario.java
    └── Prontuario.java
```

---

## 🎓 O Que Foi Avaliado

### 👉 Entendimento de Service
- ✅ Service contém **lógica de negócio**
- ✅ Repository faz apenas **acesso a dados**
- ✅ Injeção de dependência via Constructor
- ✅ Exceções tratadas apropriadamente

### 👉 Modelagem de Regras Reais
- ✅ Validação de conflito de agenda (duplicação)
- ✅ Validação de especialidade (constraint de negócio)
- ✅ Histórico persistido (Prontuário)
- ✅ Integridade referencial (Tutor ↔ Animal)

### 👉 Evolução do CRUD Básico
- ✅ Controllers expõem REST API
- ✅ Services implementam regras
- ✅ Repositories usam queries customizadas
- ✅ Entidades com relacionamentos complexos
- ✅ Testes validam cenários críticos

---

## ✨ Conclusão

Todo o código foi implementado seguindo:
- **Padrão MVC** com separação clara de responsabilidades
- **Spring Boot Best Practices**
- **Domain-Driven Design** (validações no Service)
- **Test-Driven Development** (testes unitários)
- **REST API Design**

**Status Geral:** ✅ 100% CONFORME

---

**Data:** 14 de Maio de 2026  
**Versão:** Spring Boot 4.0.6 | Java 21 | MySQL 8.0
