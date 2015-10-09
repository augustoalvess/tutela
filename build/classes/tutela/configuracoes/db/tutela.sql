BEGIN;

CREATE TABLE pessoa (
    idPessoa SERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(45) NOT NULL,
    estadoCivil VARCHAR(45) NOT NULL,
    dataNascimento DATE NOT NULL,
    sexo CHAR NOT NULL,
    origemEtnica VARCHAR(45) NOT NULL,
    estado VARCHAR(45) NOT NULL,
    cidade VARCHAR(45) NOT NULL,
    bairro VARCHAR(45) NOT NULL,
    rua VARCHAR(45) NOT NULL,
    numero INT NOT NULL,
    complemento VARCHAR(45),
    rg VARCHAR(45),
    cpf VARCHAR(14),
    telefoneResidencial VARCHAR(45),
    telefoneCelular VARCHAR(45) NOT NULL,
    email VARCHAR(45)
);
CREATE INDEX idx_pessoa_idPessoa ON pessoa(idPessoa);
--

CREATE TABLE crianca (
    possuiNecessidadeEspecial BOOLEAN NOT NULL DEFAULT FALSE,
    necessidadeEspecial VARCHAR(45),
    nomeMae VARCHAR(45),
    nomePai VARCHAR(45),
    outroResponsavel VARCHAR(45),
    certidaoNascimento VARCHAR(45) NOT NULL
) INHERITS(pessoa);

ALTER TABLE crianca ADD CONSTRAINT crt_crianca_idPessoa PRIMARY KEY (idPessoa);
--

CREATE TABLE login (
    idLogin SERIAL PRIMARY KEY NOT NULL,
    login VARCHAR(45) NOT NULL,
    senha VARCHAR(45) NOT NULL,
    idPessoa INT REFERENCES pessoa(idPessoa)
);
CREATE INDEX idx_login_idlogin ON login(idLogin);

CREATE OR REPLACE FUNCTION ajusta_senha_para_md5()
RETURNS TRIGGER AS
$BODY$
BEGIN
    NEW.senha := MD5(NEW.senha);

    RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql;

                CREATE TRIGGER trg_ajusta_senha_para_md5 
    BEFORE INSERT OR UPDATE ON login 
FOR EACH ROW EXECUTE PROCEDURE ajusta_senha_para_md5();


COMMIT;