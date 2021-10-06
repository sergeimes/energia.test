SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

CREATE TABLE public.shopitem (
    id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    price NUMERIC NOT NULL,
    quantity INTEGER,
    image VARCHAR(50),
    donated BOOLEAN DEFAULT false NOT NULL,
PRIMARY KEY (id));

CREATE TABLE public.basket (
    id BIGINT NOT NULL,
    session VARCHAR(100) NOT NULL UNIQUE,
    created timestamp without time zone,
PRIMARY KEY (id));

CREATE TABLE public.basketitem (
    id BIGINT NOT NULL,
    basket_id BIGINT NOT NULL,
    shopitem_id BIGINT NOT NULL,
    quantity INTEGER,
PRIMARY KEY (id));

DROP SEQUENCE IF EXISTS public.basketseq;
CREATE SEQUENCE public.basketseq INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 CACHE 20 NO CYCLE;

DROP SEQUENCE IF EXISTS public.shopitemseq;
CREATE SEQUENCE public.shopitemseq INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 CACHE 20 NO CYCLE;

DROP SEQUENCE IF EXISTS public.basketitemseq;
CREATE SEQUENCE public.basketitemseq INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 CACHE 20 NO CYCLE;
