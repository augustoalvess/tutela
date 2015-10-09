--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: ajusta_senha_para_md5(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION ajusta_senha_para_md5() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.senha := MD5(NEW.senha);

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.ajusta_senha_para_md5() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: pessoa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pessoa (
    idpessoa integer NOT NULL,
    nome character varying(45) NOT NULL,
    estadocivil character varying(45) NOT NULL,
    datanascimento date NOT NULL,
    sexo character(1) NOT NULL,
    origemetnica character varying(45) NOT NULL,
    estado character varying(45) NOT NULL,
    cidade character varying(45) NOT NULL,
    bairro character varying(45) NOT NULL,
    rua character varying(45) NOT NULL,
    numero integer NOT NULL,
    complemento character varying(45),
    rg character varying(45),
    cpf character varying(14),
    telefoneresidencial character varying(45),
    telefonecelular character varying(45) NOT NULL,
    email character varying(45)
);


ALTER TABLE pessoa OWNER TO postgres;
ALTER TABLE pessoa ADD CONSTRAINT ctr_pessoa_idpessoa UNIQUE (idpessoa);

--
-- Name: crianca; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE crianca (
    possuinecessidadeespecial boolean DEFAULT false NOT NULL,
    necessidadeespecial character varying(45),
    nomemae character varying(45),
    nomepai character varying(45),
    outroresponsavel character varying(45),
    certidaonascimento character varying(45) NOT NULL
)
INHERITS (pessoa);


ALTER TABLE crianca OWNER TO postgres;

--
-- Name: login; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE login (
    idlogin integer NOT NULL,
    login character varying(45) NOT NULL,
    senha character varying(45) NOT NULL,
    idpessoa integer,
    eadmin boolean DEFAULT false
);


ALTER TABLE login OWNER TO postgres;

--
-- Name: login_idlogin_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE login_idlogin_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE login_idlogin_seq OWNER TO postgres;

--
-- Name: login_idlogin_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE login_idlogin_seq OWNED BY login.idlogin;


--
-- Name: ocorrencia_idocorrencia_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ocorrencia_idocorrencia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ocorrencia_idocorrencia_seq OWNER TO postgres;

--
-- Name: ocorrencia; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ocorrencia (
    idocorrencia integer DEFAULT nextval('ocorrencia_idocorrencia_seq'::regclass) NOT NULL,
    pessoaid integer,
    dataocorrencia text,
    nomedeclarante text,
    telefonedeclarante text,
    criancaid integer,
    estadoocorreu text,
    cidadeocorreu text,
    bairroocorreu text,
    ruaocorreu text,
    numeroocorreu text,
    complementoocorreu text,
    declaracao text,
    observacao text,
    dataintimacaoprocessada text,
    dataintimacao text,
    estadointimacao text,
    cidadeintimacao text,
    bairrointimacao text,
    ruaintimacao text,
    numerointimacao text,
    complementointimacao text,
    providenciatomada text,
    parecerconclusivo text,
    situacao text,
    intimacao text
);


ALTER TABLE ocorrencia OWNER TO postgres;

--
-- Name: ocorrenciapessoa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ocorrenciapessoa (
    idocorrencia integer,
    idpessoa integer
);


ALTER TABLE ocorrenciapessoa OWNER TO postgres;

--
-- Name: pessoa_idpessoa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pessoa_idpessoa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pessoa_idpessoa_seq OWNER TO postgres;

--
-- Name: pessoa_idpessoa_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pessoa_idpessoa_seq OWNED BY pessoa.idpessoa;


--
-- Name: idpessoa; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY crianca ALTER COLUMN idpessoa SET DEFAULT nextval('pessoa_idpessoa_seq'::regclass);


--
-- Name: idlogin; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY login ALTER COLUMN idlogin SET DEFAULT nextval('login_idlogin_seq'::regclass);


--
-- Name: idpessoa; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pessoa ALTER COLUMN idpessoa SET DEFAULT nextval('pessoa_idpessoa_seq'::regclass);


--
-- Data for Name: crianca; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY crianca (idpessoa, nome, estadocivil, datanascimento, sexo, origemetnica, estado, cidade, bairro, rua, numero, complemento, rg, cpf, telefoneresidencial, telefonecelular, email, possuinecessidadeespecial, necessidadeespecial, nomemae, nomepai, outroresponsavel, certidaonascimento) FROM stdin;
1	Crianca 1	Casado(a)	2000-01-01	M	Pardo	RS - Rio Grande do Sul	asd	asd	asd	123			   .   .   -  	(  ) -          	(22) - 222222222		t	asdasd	Maria	Jo�o		11111111
2	Crian�a sombria 2	Vi�vo(a)	1111-11-11	M	Negro	RS - Rio Grande do Sul	asd	zxc	zxc	1111			   .   .   -  	(  ) -          	(22) - 222222222		f		Jucelina	Dilma		1111111
\.


--
-- Data for Name: login; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY login (idlogin, login, senha, idpessoa, eadmin) FROM stdin;
2	login	e8d95a51f3af4a3b134bf6bb680a213a	3	t
\.


--
-- Name: login_idlogin_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('login_idlogin_seq', 2, true);


--
-- Data for Name: ocorrencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ocorrencia (idocorrencia, pessoaid, dataocorrencia, nomedeclarante, telefonedeclarante, criancaid, estadoocorreu, cidadeocorreu, bairroocorreu, ruaocorreu, numeroocorreu, complementoocorreu, declaracao, observacao, dataintimacaoprocessada, dataintimacao, estadointimacao, cidadeintimacao, bairrointimacao, ruaintimacao, numerointimacao, complementointimacao, providenciatomada, parecerconclusivo, situacao, intimacao) FROM stdin;
11	1	25/04/2015	Ocorrencia Master Blaster	(22) - 222222222	2	RS - Rio Grande do Sul	3333333333333	44444444	5555555555555	5555555555555	7777777777777777	dfsd\nfs\ndf\nsd\nf\ns\t	54\n64\n56\n45\n64\n5\n6	25/04/2015	11/11/1111	RS - Rio Grande do Sul	22222222	333333333333	444444444	55555	66666666666	sfsdfsdfsdf\t	111111111111111111111111111111111111111111111111	Encaminhada para Institui��o Terceira	00000000000000000000000000000000000000000000000000000000000000000000
12	1	25/04/2015	sdfsd	(11) - 111111111	0	RS - Rio Grande do Sul	333333333	555555555555	666666	666666	4			25/04/2015	23/43/4423	RS - Rio Grande do Sul	111111	2222222	33333333	4444444	55555	sdfsd	sdds	Fechada	555555555
\.


--
-- Name: ocorrencia_idocorrencia_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ocorrencia_idocorrencia_seq', 12, true);


--
-- Data for Name: ocorrenciapessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ocorrenciapessoa (idocorrencia, idpessoa) FROM stdin;
11	1
11	3
11	4
11	2
12	2
12	2
\.


--
-- Data for Name: pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pessoa (idpessoa, nome, estadocivil, datanascimento, sexo, origemetnica, estado, cidade, bairro, rua, numero, complemento, rg, cpf, telefoneresidencial, telefonecelular, email) FROM stdin;
1	Pessoa de teste	Solteiro	1870-02-27	F	Neg�o	Rond�nia	JudasPerdeuAsBotas do Norte	Fim da picada	No fim do mercado	666	Bem no fim	445566	778899	190	192	gutometalero@bing.com.br
2	Pessoa de teste M�e	Solteiro	1870-02-27	F	Neg�o	Rond�nia	JudasPerdeuAsBotas do Norte	Fim da picada	No fim do mercado	666	Bem no fim	445566	778899	190	192	gutometalero@bing.com.br
\.


--
-- Name: pessoa_idpessoa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pessoa_idpessoa_seq', 2, true);


--
-- Name: crt_crianca_idpessoa; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT crt_crianca_idpessoa PRIMARY KEY (idpessoa);


--
-- Name: idocorrencia; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ocorrencia
    ADD CONSTRAINT idocorrencia PRIMARY KEY (idocorrencia);


--
-- Name: login_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY login
    ADD CONSTRAINT login_pkey PRIMARY KEY (idlogin);


--
-- Name: pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pessoa
    ADD CONSTRAINT pessoa_pkey PRIMARY KEY (idpessoa);


--
-- Name: idx_login_idlogin; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_login_idlogin ON login USING btree (idlogin);


--
-- Name: idx_pessoa_idpessoa; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX idx_pessoa_idpessoa ON pessoa USING btree (idpessoa);


--
-- Name: trg_ajusta_senha_para_md5; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_ajusta_senha_para_md5 BEFORE INSERT OR UPDATE ON login FOR EACH ROW EXECUTE PROCEDURE ajusta_senha_para_md5();


--
-- Name: login_idpessoa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY login
    ADD CONSTRAINT login_idpessoa_fkey FOREIGN KEY (idpessoa) REFERENCES pessoa(idpessoa);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;

CREATE OR REPLACE FUNCTION obterMesExtenso(p_mes int)
RETURNS varchar AS
$BODY$
BEGIN
    RETURN CASE p_mes
        WHEN 01 THEN 'Janeiro'
        WHEN 02 THEN 'Fevereiro'
        WHEN 03 THEN 'Março'
        WHEN 04 THEN 'Abril'
        WHEN 05 THEN 'Maio'
        WHEN 06 THEN 'Junho'
        WHEN 07 THEN 'Julho'
        WHEN 08 THEN 'Agosto'
        WHEN 09 THEN 'Setembro'
        WHEN 10 THEN 'Outubro'
        WHEN 11 THEN 'Novembro'
        WHEN 12 THEN 'Dezembro'
    END;
END;
$BODY$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION dataPorExtenso(p_data date)
RETURNS varchar AS
$BODY$
BEGIN
    RETURN EXTRACT(day FROM p_data) || ' de ' || obterMesExtenso(EXTRACT(month FROM p_data)::int) || ' de ' || EXTRACT(year FROM p_data);
END;
$BODY$
LANGUAGE 'plpgsql';

CREATE TABLE registrolog(
    registrologid SERIAL PRIMARY KEY NOT NULL,
    datalog TIMESTAMP NOT NULL DEFAULT NOW(),
    nomeusuario VARCHAR(255) NOT NULL,
    sqllog TEXT NOT NULL
);

ALTER TABLE pessoa ADD COLUMN eConselheiro BOOLEAN NOT NULL DEFAULT FALSE;
