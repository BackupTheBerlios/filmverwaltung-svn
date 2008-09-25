--
-- PostgreSQL database dump
--

SET client_encoding = 'UNICODE';
SET check_function_bodies = false;

SET SESSION AUTHORIZATION 'stepsi';

SET search_path = public, pg_catalog;

--
-- TOC entry 3 (OID 37552)
-- Name: film; Type: TABLE; Schema: public; Owner: stepsi
--

CREATE TABLE film (
    id character varying(50) NOT NULL,
    ofdbid integer,
    name character varying(255) NOT NULL,
    ofdbname character varying(255),
    erscheinungsjahr integer,
    ofdbbeschreibung character varying(10485760),
    uploader character varying(50)
) WITHOUT OIDS;


--
-- TOC entry 4 (OID 37557)
-- Name: filmbewertung; Type: TABLE; Schema: public; Owner: stepsi
--

CREATE TABLE filmbewertung (
    id character varying(50) NOT NULL,
    ofdbid integer,
    film_id character varying(50) NOT NULL,
    benutzer character varying(50) NOT NULL,
    imbesitz boolean,
    punkte integer,
    beschreibung character varying(10485760),
    kommentar character varying(10485760),
    eintragsdatum timestamp without time zone
) WITHOUT OIDS;


--
-- TOC entry 5 (OID 37562)
-- Name: benutzer; Type: TABLE; Schema: public; Owner: stepsi
--

CREATE TABLE benutzer (
    id character varying(50) NOT NULL,
    anmeldename character varying(255) NOT NULL,
    passwort character varying(255) NOT NULL,
    anmeldedatum timestamp without time zone,
    letzterzugriff timestamp without time zone,
    programmversion integer
) WITHOUT OIDS;


--
-- TOC entry 6 (OID 37564)
-- Name: genre; Type: TABLE; Schema: public; Owner: stepsi
--

CREATE TABLE genre (
    id character varying(50) NOT NULL,
    name character varying(255) NOT NULL
) WITHOUT OIDS;


--
-- TOC entry 7 (OID 37566)
-- Name: film_genre; Type: TABLE; Schema: public; Owner: stepsi
--

CREATE TABLE film_genre (
    film_id character varying(50) NOT NULL,
    genre_id character varying(50) NOT NULL,
    genres_index integer NOT NULL
) WITHOUT OIDS;


--
-- TOC entry 8 (OID 37568)
-- Name: person; Type: TABLE; Schema: public; Owner: stepsi
--

CREATE TABLE person (
    id character varying(50) NOT NULL,
    name character varying(255) NOT NULL
) WITHOUT OIDS;


--
-- TOC entry 9 (OID 37570)
-- Name: film_person; Type: TABLE; Schema: public; Owner: stepsi
--

CREATE TABLE film_person (
    film_id character varying(50) NOT NULL,
    person_id character varying(50) NOT NULL,
    cast_index integer NOT NULL
) WITHOUT OIDS;


--
-- TOC entry 11 (OID 37572)
-- Name: film_pkey; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY film
    ADD CONSTRAINT film_pkey PRIMARY KEY (id);


--
-- TOC entry 12 (OID 37574)
-- Name: filmbewertung_pkey; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY filmbewertung
    ADD CONSTRAINT filmbewertung_pkey PRIMARY KEY (id);


--
-- TOC entry 13 (OID 37576)
-- Name: benutzer_pkey; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY benutzer
    ADD CONSTRAINT benutzer_pkey PRIMARY KEY (id);


--
-- TOC entry 15 (OID 37578)
-- Name: genre_pkey; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);


--
-- TOC entry 17 (OID 37580)
-- Name: film_genre_pkey; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY film_genre
    ADD CONSTRAINT film_genre_pkey PRIMARY KEY (film_id, genres_index);


--
-- TOC entry 19 (OID 37582)
-- Name: person_pkey; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 21 (OID 37584)
-- Name: film_person_pkey; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY film_person
    ADD CONSTRAINT film_person_pkey PRIMARY KEY (film_id, cast_index);


--
-- TOC entry 18 (OID 37586)
-- Name: person_name_key; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY person
    ADD CONSTRAINT person_name_key UNIQUE (name);


--
-- TOC entry 14 (OID 37588)
-- Name: genre_name_key; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY genre
    ADD CONSTRAINT genre_name_key UNIQUE (name);


--
-- TOC entry 10 (OID 37590)
-- Name: film_name_key; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY film
    ADD CONSTRAINT film_name_key UNIQUE (name);


--
-- TOC entry 20 (OID 37620)
-- Name: film_person_film_id_key; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY film_person
    ADD CONSTRAINT film_person_film_id_key UNIQUE (film_id, person_id);


--
-- TOC entry 16 (OID 37622)
-- Name: film_genre_film_id_key; Type: CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY film_genre
    ADD CONSTRAINT film_genre_film_id_key UNIQUE (film_id, genre_id);


--
-- TOC entry 22 (OID 37592)
-- Name: fk_film_uploader; Type: FK CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY film
    ADD CONSTRAINT fk_film_uploader FOREIGN KEY (uploader) REFERENCES benutzer(id);


--
-- TOC entry 23 (OID 37596)
-- Name: fk_filmbewertung_benutzer; Type: FK CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY filmbewertung
    ADD CONSTRAINT fk_filmbewertung_benutzer FOREIGN KEY (benutzer) REFERENCES benutzer(id);


--
-- TOC entry 24 (OID 37600)
-- Name: fk_filmbewertung_film_id; Type: FK CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY filmbewertung
    ADD CONSTRAINT fk_filmbewertung_film_id FOREIGN KEY (film_id) REFERENCES film(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 25 (OID 37604)
-- Name: fk_film_genre_genre; Type: FK CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY film_genre
    ADD CONSTRAINT fk_film_genre_genre FOREIGN KEY (genre_id) REFERENCES genre(id);


--
-- TOC entry 26 (OID 37608)
-- Name: fk_film_genre_film; Type: FK CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY film_genre
    ADD CONSTRAINT fk_film_genre_film FOREIGN KEY (film_id) REFERENCES film(id);


--
-- TOC entry 27 (OID 37612)
-- Name: fk_film_person_person; Type: FK CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY film_person
    ADD CONSTRAINT fk_film_person_person FOREIGN KEY (person_id) REFERENCES person(id);


--
-- TOC entry 28 (OID 37616)
-- Name: fk_film_person_film; Type: FK CONSTRAINT; Schema: public; Owner: stepsi
--

ALTER TABLE ONLY film_person
    ADD CONSTRAINT fk_film_person_film FOREIGN KEY (film_id) REFERENCES film(id);


