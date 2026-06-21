CREATE DATABASE biblioteca_comunitaria;
USE biblioteca_comunitaria;

CREATE TABLE usuario (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         sobrenome VARCHAR(100) NOT NULL,
                         cpf VARCHAR(14) NOT NULL UNIQUE,
                         usuario VARCHAR(100) NOT NULL UNIQUE,
                         senha VARCHAR(100) NOT NULL
);

CREATE TABLE livro (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       titulo VARCHAR(100) NOT NULL,
                       autor VARCHAR(100) NOT NULL,
                       genero VARCHAR(50),
                       status VARCHAR(30),
                       id_dono INT,
                       FOREIGN KEY (id_dono) REFERENCES usuario(id)
);

CREATE TABLE troca (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       id_livro INT NOT NULL,
                       id_solicitante INT NOT NULL,
                       id_dono INT NOT NULL,
                       status VARCHAR(30) NOT NULL,
                       data_solicitacao DATETIME DEFAULT CURRENT_TIMESTAMP,
                       data_resposta DATETIME NULL,
                       FOREIGN KEY (id_livro) REFERENCES livro(id),
                       FOREIGN KEY (id_solicitante) REFERENCES usuario(id),
                       FOREIGN KEY (id_dono) REFERENCES usuario(id)
);