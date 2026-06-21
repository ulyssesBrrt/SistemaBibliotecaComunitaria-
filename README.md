# SistemaBibliotecaComunitaria-

Esse é o projeto prático desenvolvido para a disciplina de Linguagem Técnica II.

O objetivo do sistema é gerenciar uma biblioteca comunitária em formato desktop, onde os próprios usuários podem cadastrar livros e realizar trocas entre si.



 # O que o sistema faz

Autenticação: Cadastro de novas contas e login para acessar o sistema.

Dashboard: Uma tela inicial com um resumo dos dados (total de livros, quantos estão disponíveis, trocas concluídas e em análise).

Acervo: Onde ficam todos os livros cadastrados. Dá para pesquisar pelo título e ver o que está disponível.

Trocas: O coração do projeto. O usuário pode solicitar o livro de outra pessoa, e o dono do livro pode aceitar ou recusar a solicitação na aba de trocas.

Perfil: Uma tela focada no usuário logado, mostrando os dados dele e apenas os livros que ele cadastrou no sistema.



# Ferramentas utilizadas

O projeto foi feito usando o padrão MVC e o conceito de DAO para separar bem a regra de negócio do banco de dados.

Linguagem: Java

Telas: JavaFX com SceneBuilder (arquivos .fxml)

Banco de Dados: MySQL

Conexão: JDBC



# Organização do projeto

br.com.biblioteca.aplicacao: Contém a classe Main que inicializa o projeto no JavaFX.

br.com.biblioteca.controller: Faz a ponte entre as telas FXML e a regra de negócio.

br.com.biblioteca.dao: Operações do banco de dados (o CRUD no MySQL).

br.com.biblioteca.model: As classes dos objetos do sistema (Livro, Troca, Usuario).

br.com.biblioteca.sessao: Controla o estado global e quem está logado no momento.

br.com.biblioteca.conexao: Classe dedicada apenas para conectar com o banco.

resources/view: Os arquivos XML das telas.

resources/view/imagens: As imagens e arquivos de design da interface.



# Desenvolvedores

Hemily Machado

Iasmim Silva

Ulysses Barreto