 # 📚 Biblioteca Comunitária Raízes
A Biblioteca Comunitária Raízes é um sistema desktop colaborativo criado para incentivar a leitura e a circulação de livros. Desenvolvido totalmente em Java, o software permite que leitores gerenciem seus próprios acervos e realizem trocas de obras de forma segura e organizada.

Este projeto foi construído como requisito acadêmico, aplicando na prática conceitos fundamentais como Programação Orientada a Objetos (POO), modelagem de banco de dados relacional, e estruturação de software através dos padrões MVC (Model-View-Controller) e DAO (Data Access Object).

# ✨ Principais Recursos
A aplicação foi desenhada para cobrir todo o fluxo de uma biblioteca comunitária virtual:

Gestão de Contas: Sistema de login e cadastro seguro para identificar cada leitor da comunidade.

Dashboard Interativo: Painel de controle com estatísticas atualizadas em tempo real (total de livros, obras disponíveis e métricas de trocas).

Exploração do Acervo: Vitrine com todos os livros cadastrados no sistema, permitindo visualizar a disponibilidade e fazer pedidos de reserva.

Sistema de Trocas: Área dedicada onde o usuário gerencia os pedidos que fez para outras pessoas e aprova/recusa as solicitações que recebeu em seus próprios livros.

Área do Usuário: Perfil individual contendo as estatísticas pessoais do leitor e o gerenciamento rápido dos livros que ele adicionou à plataforma.

# 🛠️ Stack Tecnológico
Para dar vida à aplicação, utilizamos as seguintes ferramentas e padrões:

Linguagem Base: Java (JDK 8 ou superior)

Interface Gráfica: JavaFX integrado com SceneBuilder para telas dinâmicas e modernas.

Banco de Dados: MySQL

Conectividade: JDBC (Java Database Connectivity)

Arquitetura: MVC e DAO, separando perfeitamente a interface visual das regras de negócio e da comunicação com o banco.

# 💾 O que o sistema gerencia?
Para garantir o funcionamento das trocas, o banco de dados armazena de forma estruturada:

Leitores: Nome, CPF, credenciais de acesso (usuário e senha).

Obras: Título, autor, gênero, status de disponibilidade e o ID do dono.

Solicitações (Trocas): Quem pediu, de quem é o livro, qual a obra desejada e o status atual da operação (Pendente, Em Análise, Concluída ou Recusada).

# 🚀 Como testar o projeto na sua máquina
Siga os passos abaixo para preparar o ambiente e rodar o software:

1. Preparando o Banco de Dados
   Certifique-se de ter o MySQL Server instalado.

2. Crie um banco de dados chamado biblioteca_comunitaria.

3. Execute o script SQL (disponível nos arquivos do projeto) para gerar as tabelas.

4. Acesse o arquivo ConexaoBanco.java no pacote br.com.biblioteca.conexao e insira as suas credenciais locais:

String url = "jdbc:mysql://localhost:3306/biblioteca_comunitaria";
String usuario = "SEU_USUARIO_AQUI";
String senha = "SUA_SENHA_AQUI";

5. Rodando a Aplicação

6. Clone este repositório:

Bash
git clone https://github.com/ulyssesBrrt/SistemaBibliotecaComunitaria-.git

7. Abra o projeto na IDE da sua escolha (IntelliJ, Eclipse, etc.).

8. Certifique-se de que a biblioteca do JavaFX está devidamente configurada no seu ambiente.

9. Localize a classe Main.java (dentro de br.com.biblioteca.aplicacao) e execute o código. A tela de login abrirá automaticamente!

# 👨💻 Equipe de Desenvolvimento
Este projeto foi desenhado e codificado por:

Hemily Machado

Iasmim Silva

Ulysses Barreto

Projeto desenvolvido para fins acadêmicos e avaliação na disciplina de Linguagem Técnica II.