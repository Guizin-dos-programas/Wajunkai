# Wajunkai
Repositório para armazenar o desenvolvimento do sistema de estoque da Wajunkai

# 📦 Sistema de Gestão de Estoque - API REST

API REST para gerenciamento de estoque e usuários, desenvolvida em **Java 21** e **Spring Boot**, aplicando os princípios da **Clean Architecture (Arquitetura Limpa)** para garantir alto desacoplamento, testabilidade e facilidade de manutenção.

---

## 🏛️ Arquitetura e Design Systems

O projeto é estruturado separando estritamente a regra de negócio do ecossistema de frameworks/infraestrutura:

* **Domain (Domínio):** Contém as entidades principais, Value Objects (`Login`, etc.) e regras invariantes do sistema. Zero dependência do Spring.
* **Application (Casos de Uso):** Contém as portas (Ports) de entrada/saída e serviços da aplicação.
* **Infrastructure (Infraestrutura):** Implementação dos adaptadores (Adapters) para persistência (MariaDB/JPA), segurança (Spring Security / BCrypt) e Controllers REST.

---

## 🛠️ Tecnologias Utilizadas

* **Java 21** (Uso de Records, Pattern Matching)
* **Spring Boot 3** (Web, Security, Data JPA)
* **Jakarta Validation** (Validação de entrada com DTOs)
* **Postgresql** (Banco de dados relacional)
* **BCrypt** (Criptografia de senhas)
* **Maven** (Gerenciamento de dependências)

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
* Java 21 instalado
* Maven
* Banco de dados postgresql rodando localmente

### Passos

### Passos

1. **Clonar o repositório:**
   ```bash
   git clone [https://github.com/Guizin-dos-programas/Wajunkai.git](https://github.com/Guizin-dos-programas/Wajunkai.git)
   cd Wajunkai
