--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.9
-- Dumped by pg_dump version 9.3.6
-- Started on 2015-09-09 15:50:43 BRT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 226 (class 3079 OID 11789)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3518 (class 0 OID 0)
-- Dependencies: 226
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 227 (class 3079 OID 557526)
-- Name: postgis; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;


--
-- TOC entry 3519 (class 0 OID 0)
-- Dependencies: 227
-- Name: EXTENSION postgis; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis IS 'PostGIS geometry, geography, and raster spatial types and functions';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 208 (class 1259 OID 559006)
-- Name: access_levels; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE access_levels (
    id integer NOT NULL,
    impact_factor double precision NOT NULL,
    environment_type_id integer NOT NULL,
    access_type_id integer NOT NULL
);


ALTER TABLE public.access_levels OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 559004)
-- Name: access_levels_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE access_levels_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.access_levels_id_seq OWNER TO postgres;

--
-- TOC entry 3520 (class 0 OID 0)
-- Dependencies: 207
-- Name: access_levels_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE access_levels_id_seq OWNED BY access_levels.id;


--
-- TOC entry 220 (class 1259 OID 559125)
-- Name: access_type_classifier; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE access_type_classifier (
    id integer NOT NULL,
    user_profile_environment_id integer NOT NULL,
    environment_type_id integer NOT NULL,
    weekday integer NOT NULL,
    shift character(1) NOT NULL,
    workday character(1) NOT NULL,
    access_type_id integer NOT NULL,
    frequency_level_id integer NOT NULL
);


ALTER TABLE public.access_type_classifier OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 559123)
-- Name: access_type_classifier_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE access_type_classifier_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.access_type_classifier_id_seq OWNER TO postgres;

--
-- TOC entry 3521 (class 0 OID 0)
-- Dependencies: 219
-- Name: access_type_classifier_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE access_type_classifier_id_seq OWNED BY access_type_classifier.id;


--
-- TOC entry 206 (class 1259 OID 558998)
-- Name: access_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE access_types (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.access_types OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 558996)
-- Name: access_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE access_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.access_types_id_seq OWNER TO postgres;

--
-- TOC entry 3522 (class 0 OID 0)
-- Dependencies: 205
-- Name: access_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE access_types_id_seq OWNED BY access_types.id;


--
-- TOC entry 214 (class 1259 OID 559064)
-- Name: actions; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE actions (
    id integer NOT NULL,
    access_level_id integer NOT NULL,
    functionality_id integer NOT NULL,
    custom_environment_id integer,
    action_name character varying(100) NOT NULL,
    start_date timestamp without time zone,
    end_date timestamp without time zone,
    start_daily_interval integer,
    interval_duration integer
);


ALTER TABLE public.actions OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 559087)
-- Name: actions_args; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE actions_args (
    id integer NOT NULL,
    label character varying(100) DEFAULT NULL::character varying,
    arg_value character varying(100) NOT NULL,
    action_id integer NOT NULL
);


ALTER TABLE public.actions_args OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 559085)
-- Name: actions_args_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE actions_args_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.actions_args_id_seq OWNER TO postgres;

--
-- TOC entry 3523 (class 0 OID 0)
-- Dependencies: 215
-- Name: actions_args_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE actions_args_id_seq OWNED BY actions_args.id;


--
-- TOC entry 213 (class 1259 OID 559062)
-- Name: actions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE actions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.actions_id_seq OWNER TO postgres;

--
-- TOC entry 3524 (class 0 OID 0)
-- Dependencies: 213
-- Name: actions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE actions_id_seq OWNED BY actions.id;


--
-- TOC entry 199 (class 1259 OID 558945)
-- Name: communication_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE communication_types (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.communication_types OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 558943)
-- Name: communication_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE communication_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.communication_types_id_seq OWNER TO postgres;

--
-- TOC entry 3525 (class 0 OID 0)
-- Dependencies: 198
-- Name: communication_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE communication_types_id_seq OWNED BY communication_types.id;


--
-- TOC entry 201 (class 1259 OID 558953)
-- Name: device_communications; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE device_communications (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    address character varying(100) NOT NULL,
    parameters text,
    address_format character varying(100),
    port character varying(50),
    device_id integer NOT NULL,
    communication_type_id integer NOT NULL,
    preferred_order integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.device_communications OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 558951)
-- Name: device_communications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE device_communications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.device_communications_id_seq OWNER TO postgres;

--
-- TOC entry 3526 (class 0 OID 0)
-- Dependencies: 200
-- Name: device_communications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE device_communications_id_seq OWNED BY device_communications.id;


--
-- TOC entry 204 (class 1259 OID 558981)
-- Name: device_functionalities; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE device_functionalities (
    device_id integer NOT NULL,
    functionality_id integer NOT NULL
);


ALTER TABLE public.device_functionalities OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 558912)
-- Name: device_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE device_types (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.device_types OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 558910)
-- Name: device_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE device_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.device_types_id_seq OWNER TO postgres;

--
-- TOC entry 3527 (class 0 OID 0)
-- Dependencies: 194
-- Name: device_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE device_types_id_seq OWNED BY device_types.id;


--
-- TOC entry 197 (class 1259 OID 558920)
-- Name: devices; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE devices (
    id integer NOT NULL,
    code character varying(50) NOT NULL,
    name character varying(100) NOT NULL,
    device_type_id integer NOT NULL,
    user_id integer,
    current_environment_id integer
);


ALTER TABLE public.devices OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 558918)
-- Name: devices_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE devices_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.devices_id_seq OWNER TO postgres;

--
-- TOC entry 3528 (class 0 OID 0)
-- Dependencies: 196
-- Name: devices_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE devices_id_seq OWNED BY devices.id;


--
-- TOC entry 225 (class 1259 OID 559168)
-- Name: environment_frequency_levels; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE environment_frequency_levels (
    environment_id integer NOT NULL,
    frequency_level_id integer NOT NULL,
    min double precision NOT NULL,
    max double precision NOT NULL,
    period integer NOT NULL,
    metric character(1) NOT NULL
);


ALTER TABLE public.environment_frequency_levels OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 558839)
-- Name: environment_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE environment_types (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.environment_types OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 558837)
-- Name: environment_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE environment_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.environment_types_id_seq OWNER TO postgres;

--
-- TOC entry 3529 (class 0 OID 0)
-- Dependencies: 186
-- Name: environment_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE environment_types_id_seq OWNED BY environment_types.id;


--
-- TOC entry 191 (class 1259 OID 558857)
-- Name: environments; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE environments (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    location geometry(PointZ,4326) NOT NULL,
    shape geometry(Polygon,4326),
    operating_range double precision DEFAULT 1.0 NOT NULL,
    version integer DEFAULT 0 NOT NULL,
    parent_environment_id integer,
    environment_type_id integer NOT NULL,
    localization_type_id integer NOT NULL,
    level integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.environments OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 558855)
-- Name: environments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE environments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.environments_id_seq OWNER TO postgres;

--
-- TOC entry 3530 (class 0 OID 0)
-- Dependencies: 190
-- Name: environments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE environments_id_seq OWNED BY environments.id;


--
-- TOC entry 224 (class 1259 OID 559162)
-- Name: frequency_levels; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE frequency_levels (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    level smallint NOT NULL
);


ALTER TABLE public.frequency_levels OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 559160)
-- Name: frequency_levels_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE frequency_levels_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.frequency_levels_id_seq OWNER TO postgres;

--
-- TOC entry 3531 (class 0 OID 0)
-- Dependencies: 223
-- Name: frequency_levels_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE frequency_levels_id_seq OWNED BY frequency_levels.id;


--
-- TOC entry 203 (class 1259 OID 558975)
-- Name: functionalities; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE functionalities (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.functionalities OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 558973)
-- Name: functionalities_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE functionalities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.functionalities_id_seq OWNER TO postgres;

--
-- TOC entry 3532 (class 0 OID 0)
-- Dependencies: 202
-- Name: functionalities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE functionalities_id_seq OWNED BY functionalities.id;


--
-- TOC entry 189 (class 1259 OID 558847)
-- Name: localization_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE localization_types (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    "precision" double precision DEFAULT 5.0 NOT NULL,
    metric character varying(20) DEFAULT 'm2'::character varying NOT NULL
);


ALTER TABLE public.localization_types OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 558845)
-- Name: localization_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE localization_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.localization_types_id_seq OWNER TO postgres;

--
-- TOC entry 3533 (class 0 OID 0)
-- Dependencies: 188
-- Name: localization_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE localization_types_id_seq OWNED BY localization_types.id;


--
-- TOC entry 218 (class 1259 OID 559101)
-- Name: log_events; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE log_events (
    id integer NOT NULL,
    log_time timestamp without time zone DEFAULT now() NOT NULL,
    shift character(1),
    weekday integer,
    workday character(1),
    user_id integer,
    device_id integer,
    environment_id integer NOT NULL,
    exiting boolean DEFAULT false NOT NULL
);


ALTER TABLE public.log_events OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 559099)
-- Name: log_events_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE log_events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.log_events_id_seq OWNER TO postgres;

--
-- TOC entry 3534 (class 0 OID 0)
-- Dependencies: 217
-- Name: log_events_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE log_events_id_seq OWNED BY log_events.id;


--
-- TOC entry 183 (class 1259 OID 558821)
-- Name: play_evolutions; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE play_evolutions (
    id integer NOT NULL,
    hash character varying(255) NOT NULL,
    applied_at timestamp without time zone NOT NULL,
    apply_script text,
    revert_script text,
    state character varying(255),
    last_problem text
);


ALTER TABLE public.play_evolutions OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 559148)
-- Name: user_credentials; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_credentials (
    id integer NOT NULL,
    external_id character varying(255) NOT NULL,
    system character varying(50) NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.user_credentials OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 559146)
-- Name: user_credentials_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_credentials_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_credentials_id_seq OWNER TO postgres;

--
-- TOC entry 3535 (class 0 OID 0)
-- Dependencies: 221
-- Name: user_credentials_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_credentials_id_seq OWNED BY user_credentials.id;


--
-- TOC entry 212 (class 1259 OID 559032)
-- Name: user_in_environment; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_in_environment (
    id integer NOT NULL,
    user_id integer NOT NULL,
    user_profile_environment_id integer DEFAULT 1 NOT NULL,
    environment_id integer NOT NULL,
    impact_factor double precision DEFAULT 0.0,
    current_access_type_id integer NOT NULL,
    current_frequency_level_id integer
);


ALTER TABLE public.user_in_environment OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 559030)
-- Name: user_in_environment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_in_environment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_in_environment_id_seq OWNER TO postgres;

--
-- TOC entry 3536 (class 0 OID 0)
-- Dependencies: 211
-- Name: user_in_environment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_in_environment_id_seq OWNED BY user_in_environment.id;


--
-- TOC entry 210 (class 1259 OID 559024)
-- Name: user_profile_environments; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_profile_environments (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.user_profile_environments OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 559022)
-- Name: user_profile_environments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_profile_environments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_profile_environments_id_seq OWNER TO postgres;

--
-- TOC entry 3537 (class 0 OID 0)
-- Dependencies: 209
-- Name: user_profile_environments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_profile_environments_id_seq OWNED BY user_profile_environments.id;


--
-- TOC entry 185 (class 1259 OID 558831)
-- Name: user_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_types (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.user_types OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 558829)
-- Name: user_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_types_id_seq OWNER TO postgres;

--
-- TOC entry 3538 (class 0 OID 0)
-- Dependencies: 184
-- Name: user_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_types_id_seq OWNED BY user_types.id;


--
-- TOC entry 193 (class 1259 OID 558887)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    full_name character varying(120),
    auth_token character varying(255),
    email_address character varying(256) NOT NULL,
    user_type_id integer NOT NULL,
    current_environment_id integer
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 558885)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 3539 (class 0 OID 0)
-- Dependencies: 192
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 3250 (class 2604 OID 559009)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY access_levels ALTER COLUMN id SET DEFAULT nextval('access_levels_id_seq'::regclass);


--
-- TOC entry 3261 (class 2604 OID 559128)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY access_type_classifier ALTER COLUMN id SET DEFAULT nextval('access_type_classifier_id_seq'::regclass);


--
-- TOC entry 3249 (class 2604 OID 559001)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY access_types ALTER COLUMN id SET DEFAULT nextval('access_types_id_seq'::regclass);


--
-- TOC entry 3255 (class 2604 OID 559067)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY actions ALTER COLUMN id SET DEFAULT nextval('actions_id_seq'::regclass);


--
-- TOC entry 3256 (class 2604 OID 559090)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY actions_args ALTER COLUMN id SET DEFAULT nextval('actions_args_id_seq'::regclass);


--
-- TOC entry 3245 (class 2604 OID 558948)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY communication_types ALTER COLUMN id SET DEFAULT nextval('communication_types_id_seq'::regclass);


--
-- TOC entry 3246 (class 2604 OID 558956)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY device_communications ALTER COLUMN id SET DEFAULT nextval('device_communications_id_seq'::regclass);


--
-- TOC entry 3243 (class 2604 OID 558915)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY device_types ALTER COLUMN id SET DEFAULT nextval('device_types_id_seq'::regclass);


--
-- TOC entry 3244 (class 2604 OID 558923)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY devices ALTER COLUMN id SET DEFAULT nextval('devices_id_seq'::regclass);


--
-- TOC entry 3234 (class 2604 OID 558842)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY environment_types ALTER COLUMN id SET DEFAULT nextval('environment_types_id_seq'::regclass);


--
-- TOC entry 3238 (class 2604 OID 558860)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY environments ALTER COLUMN id SET DEFAULT nextval('environments_id_seq'::regclass);


--
-- TOC entry 3263 (class 2604 OID 559165)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY frequency_levels ALTER COLUMN id SET DEFAULT nextval('frequency_levels_id_seq'::regclass);


--
-- TOC entry 3248 (class 2604 OID 558978)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY functionalities ALTER COLUMN id SET DEFAULT nextval('functionalities_id_seq'::regclass);


--
-- TOC entry 3235 (class 2604 OID 558850)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY localization_types ALTER COLUMN id SET DEFAULT nextval('localization_types_id_seq'::regclass);


--
-- TOC entry 3258 (class 2604 OID 559104)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY log_events ALTER COLUMN id SET DEFAULT nextval('log_events_id_seq'::regclass);


--
-- TOC entry 3262 (class 2604 OID 559151)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_credentials ALTER COLUMN id SET DEFAULT nextval('user_credentials_id_seq'::regclass);


--
-- TOC entry 3252 (class 2604 OID 559035)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_in_environment ALTER COLUMN id SET DEFAULT nextval('user_in_environment_id_seq'::regclass);


--
-- TOC entry 3251 (class 2604 OID 559027)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_profile_environments ALTER COLUMN id SET DEFAULT nextval('user_profile_environments_id_seq'::regclass);


--
-- TOC entry 3233 (class 2604 OID 558834)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_types ALTER COLUMN id SET DEFAULT nextval('user_types_id_seq'::regclass);


--
-- TOC entry 3242 (class 2604 OID 558890)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 3493 (class 0 OID 559006)
-- Dependencies: 208
-- Data for Name: access_levels; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO access_levels (id, impact_factor, environment_type_id, access_type_id) VALUES (1, 0, 1, 1);
INSERT INTO access_levels (id, impact_factor, environment_type_id, access_type_id) VALUES (2, 10, 1, 5);
INSERT INTO access_levels (id, impact_factor, environment_type_id, access_type_id) VALUES (3, 0, 2, 1);
INSERT INTO access_levels (id, impact_factor, environment_type_id, access_type_id) VALUES (4, 2, 2, 2);
INSERT INTO access_levels (id, impact_factor, environment_type_id, access_type_id) VALUES (5, 5, 2, 3);
INSERT INTO access_levels (id, impact_factor, environment_type_id, access_type_id) VALUES (6, 8, 2, 4);
INSERT INTO access_levels (id, impact_factor, environment_type_id, access_type_id) VALUES (7, 10, 2, 5);
INSERT INTO access_levels (id, impact_factor, environment_type_id, access_type_id) VALUES (8, 0, 3, 5);


--
-- TOC entry 3540 (class 0 OID 0)
-- Dependencies: 207
-- Name: access_levels_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('access_levels_id_seq', 20, true);


--
-- TOC entry 3505 (class 0 OID 559125)
-- Dependencies: 220
-- Data for Name: access_type_classifier; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (1, 1, 2, 1, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (2, 1, 2, 1, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (3, 1, 2, 1, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (4, 1, 2, 1, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (5, 1, 2, 1, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (6, 1, 2, 1, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (7, 1, 2, 1, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (8, 1, 2, 1, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (9, 1, 2, 0, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (10, 1, 2, 0, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (11, 1, 2, 0, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (12, 1, 2, 0, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (13, 1, 2, 0, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (14, 1, 2, 0, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (15, 1, 2, 0, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (16, 1, 2, 0, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (17, 1, 3, 1, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (18, 1, 3, 1, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (19, 1, 3, 1, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (20, 1, 3, 1, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (21, 1, 3, 1, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (22, 1, 3, 1, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (23, 1, 3, 1, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (24, 1, 3, 1, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (25, 1, 3, 0, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (26, 1, 3, 0, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (27, 1, 3, 0, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (28, 1, 3, 0, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (29, 1, 3, 0, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (30, 1, 3, 0, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (31, 1, 3, 0, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (32, 1, 3, 0, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (33, 1, 1, 1, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (34, 1, 1, 1, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (35, 1, 1, 1, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (36, 1, 1, 1, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (37, 1, 1, 1, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (38, 1, 1, 1, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (39, 1, 1, 1, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (40, 1, 1, 1, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (41, 1, 1, 0, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (42, 1, 1, 0, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (43, 1, 1, 0, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (44, 1, 1, 0, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (45, 1, 1, 0, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (46, 1, 1, 0, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (47, 1, 1, 0, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (48, 1, 1, 0, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (49, 2, 2, 1, 'd', 'y', 2, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (50, 2, 2, 1, 'd', 'y', 2, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (51, 2, 2, 1, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (52, 2, 2, 1, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (53, 2, 2, 1, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (54, 2, 2, 1, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (55, 2, 2, 1, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (56, 2, 2, 1, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (57, 2, 2, 0, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (58, 2, 2, 0, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (59, 2, 2, 0, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (60, 2, 2, 0, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (61, 2, 2, 0, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (62, 2, 2, 0, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (63, 2, 2, 0, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (64, 2, 2, 0, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (65, 2, 3, 1, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (66, 2, 3, 1, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (67, 2, 3, 1, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (68, 2, 3, 1, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (69, 2, 3, 1, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (70, 2, 3, 1, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (71, 2, 3, 1, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (72, 2, 3, 1, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (73, 2, 3, 0, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (74, 2, 3, 0, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (75, 2, 3, 0, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (76, 2, 3, 0, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (77, 2, 3, 0, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (78, 2, 3, 0, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (79, 2, 3, 0, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (80, 2, 3, 0, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (81, 2, 1, 1, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (82, 2, 1, 1, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (83, 2, 1, 1, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (84, 2, 1, 1, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (85, 2, 1, 1, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (86, 2, 1, 1, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (87, 2, 1, 1, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (88, 2, 1, 1, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (89, 2, 1, 0, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (90, 2, 1, 0, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (91, 2, 1, 0, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (92, 2, 1, 0, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (93, 2, 1, 0, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (94, 2, 1, 0, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (95, 2, 1, 0, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (96, 2, 1, 0, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (97, 3, 2, 1, 'd', 'y', 3, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (98, 3, 2, 1, 'd', 'y', 3, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (99, 3, 2, 1, 'd', 'n', 3, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (100, 3, 2, 1, 'd', 'n', 3, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (101, 3, 2, 1, 'd', 'n', 3, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (102, 3, 2, 1, 'n', 'y', 3, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (103, 3, 2, 1, 'n', 'y', 3, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (104, 3, 2, 1, 'n', 'y', 3, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (105, 3, 2, 1, 'n', 'n', 2, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (106, 3, 2, 1, 'n', 'n', 2, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (107, 3, 2, 1, 'n', 'n', 2, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (108, 3, 2, 0, 'd', 'y', 2, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (109, 3, 2, 0, 'd', 'y', 2, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (110, 3, 2, 0, 'd', 'y', 2, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (111, 3, 2, 0, 'd', 'n', 2, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (112, 3, 2, 0, 'd', 'n', 2, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (113, 3, 2, 0, 'd', 'n', 2, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (114, 3, 2, 0, 'n', 'y', 2, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (115, 3, 2, 0, 'n', 'y', 2, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (116, 3, 2, 0, 'n', 'y', 2, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (117, 3, 2, 0, 'n', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (118, 3, 2, 0, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (119, 3, 2, 0, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (120, 3, 3, 1, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (121, 3, 3, 1, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (122, 3, 3, 1, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (123, 3, 3, 1, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (124, 3, 3, 1, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (125, 3, 3, 1, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (126, 3, 3, 1, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (127, 3, 3, 1, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (128, 3, 3, 1, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (129, 3, 3, 1, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (130, 3, 3, 1, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (131, 3, 3, 1, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (132, 3, 3, 0, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (133, 3, 3, 0, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (134, 3, 3, 0, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (135, 3, 3, 0, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (136, 3, 3, 0, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (137, 3, 3, 0, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (138, 3, 3, 0, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (139, 3, 3, 0, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (140, 3, 3, 0, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (141, 3, 3, 0, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (142, 3, 3, 0, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (143, 3, 3, 0, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (144, 3, 1, 1, 'd', 'y', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (145, 3, 1, 1, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (146, 3, 1, 1, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (147, 3, 1, 1, 'd', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (148, 3, 1, 1, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (149, 3, 1, 1, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (150, 3, 1, 1, 'n', 'y', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (151, 3, 1, 1, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (152, 3, 1, 1, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (153, 3, 1, 1, 'n', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (154, 3, 1, 1, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (155, 3, 1, 1, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (156, 3, 1, 0, 'd', 'y', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (157, 3, 1, 0, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (158, 3, 1, 0, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (159, 3, 1, 0, 'd', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (160, 3, 1, 0, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (161, 3, 1, 0, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (162, 3, 1, 0, 'n', 'y', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (163, 3, 1, 0, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (164, 3, 1, 0, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (165, 3, 1, 0, 'n', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (166, 3, 1, 0, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (167, 3, 1, 0, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (168, 4, 2, 1, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (169, 4, 2, 1, 'd', 'y', 4, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (170, 4, 2, 1, 'd', 'y', 3, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (171, 4, 2, 1, 'd', 'n', 4, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (172, 4, 2, 1, 'd', 'n', 4, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (173, 4, 2, 1, 'd', 'n', 3, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (174, 4, 2, 1, 'n', 'y', 4, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (175, 4, 2, 1, 'n', 'y', 4, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (176, 4, 2, 1, 'n', 'y', 3, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (177, 4, 2, 1, 'n', 'n', 4, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (178, 4, 2, 1, 'n', 'n', 4, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (179, 4, 2, 1, 'n', 'n', 4, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (180, 4, 2, 0, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (181, 4, 2, 0, 'd', 'y', 4, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (182, 4, 2, 0, 'd', 'y', 3, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (183, 4, 2, 0, 'd', 'n', 4, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (184, 4, 2, 0, 'd', 'n', 4, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (185, 4, 2, 0, 'd', 'n', 4, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (186, 4, 2, 0, 'n', 'y', 3, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (187, 4, 2, 0, 'n', 'y', 3, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (188, 4, 2, 0, 'n', 'y', 3, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (189, 4, 2, 0, 'n', 'n', 3, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (190, 4, 2, 0, 'n', 'n', 3, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (191, 4, 2, 0, 'n', 'n', 3, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (192, 4, 3, 1, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (193, 4, 3, 1, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (194, 4, 3, 1, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (195, 4, 3, 1, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (196, 4, 3, 1, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (197, 4, 3, 1, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (198, 4, 3, 1, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (199, 4, 3, 1, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (200, 4, 3, 1, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (201, 4, 3, 1, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (202, 4, 3, 1, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (203, 4, 3, 1, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (204, 4, 3, 0, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (205, 4, 3, 0, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (206, 4, 3, 0, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (207, 4, 3, 0, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (208, 4, 3, 0, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (209, 4, 3, 0, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (210, 4, 3, 0, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (211, 4, 3, 0, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (212, 4, 3, 0, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (213, 4, 3, 0, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (214, 4, 3, 0, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (215, 4, 3, 0, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (216, 4, 1, 1, 'd', 'y', 3, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (217, 4, 1, 1, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (218, 4, 1, 1, 'd', 'y', 2, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (219, 4, 1, 1, 'd', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (220, 4, 1, 1, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (221, 4, 1, 1, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (222, 4, 1, 1, 'n', 'y', 3, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (223, 4, 1, 1, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (224, 4, 1, 1, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (225, 4, 1, 1, 'n', 'n', 2, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (226, 4, 1, 1, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (227, 4, 1, 1, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (228, 4, 1, 0, 'd', 'y', 3, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (229, 4, 1, 0, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (230, 4, 1, 0, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (231, 4, 1, 0, 'd', 'n', 2, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (232, 4, 1, 0, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (233, 4, 1, 0, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (234, 4, 1, 0, 'n', 'y', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (235, 4, 1, 0, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (236, 4, 1, 0, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (237, 4, 1, 0, 'n', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (238, 4, 1, 0, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (239, 4, 1, 0, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (240, 5, 2, 1, 'd', 'y', 4, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (241, 5, 2, 1, 'd', 'y', 4, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (242, 5, 2, 1, 'd', 'y', 3, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (243, 5, 2, 1, 'd', 'n', 3, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (244, 5, 2, 1, 'd', 'n', 3, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (245, 5, 2, 1, 'd', 'n', 2, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (246, 5, 2, 1, 'n', 'y', 4, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (247, 5, 2, 1, 'n', 'y', 4, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (248, 5, 2, 1, 'n', 'y', 3, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (249, 5, 2, 1, 'n', 'n', 3, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (250, 5, 2, 1, 'n', 'n', 3, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (251, 5, 2, 1, 'n', 'n', 2, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (252, 5, 2, 0, 'd', 'y', 3, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (253, 5, 2, 0, 'd', 'y', 2, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (254, 5, 2, 0, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (255, 5, 2, 0, 'd', 'n', 2, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (256, 5, 2, 0, 'd', 'n', 2, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (257, 5, 2, 0, 'd', 'n', 2, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (258, 5, 2, 0, 'n', 'y', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (259, 5, 2, 0, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (260, 5, 2, 0, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (261, 5, 2, 0, 'n', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (262, 5, 2, 0, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (263, 5, 2, 0, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (264, 5, 3, 1, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (265, 5, 3, 1, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (266, 5, 3, 1, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (267, 5, 3, 1, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (268, 5, 3, 1, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (269, 5, 3, 1, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (270, 5, 3, 1, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (271, 5, 3, 1, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (272, 5, 3, 1, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (273, 5, 3, 1, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (274, 5, 3, 1, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (275, 5, 3, 1, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (276, 5, 3, 0, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (277, 5, 3, 0, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (278, 5, 3, 0, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (279, 5, 3, 0, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (280, 5, 3, 0, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (281, 5, 3, 0, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (282, 5, 3, 0, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (283, 5, 3, 0, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (284, 5, 3, 0, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (285, 5, 3, 0, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (286, 5, 3, 0, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (287, 5, 3, 0, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (288, 5, 1, 1, 'd', 'y', 2, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (289, 5, 1, 1, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (290, 5, 1, 1, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (291, 5, 1, 1, 'd', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (292, 5, 1, 1, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (293, 5, 1, 1, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (294, 5, 1, 1, 'n', 'y', 2, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (295, 5, 1, 1, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (296, 5, 1, 1, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (297, 5, 1, 1, 'n', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (298, 5, 1, 1, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (299, 5, 1, 1, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (300, 5, 1, 0, 'd', 'y', 2, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (301, 5, 1, 0, 'd', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (302, 5, 1, 0, 'd', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (303, 5, 1, 0, 'd', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (304, 5, 1, 0, 'd', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (305, 5, 1, 0, 'd', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (306, 5, 1, 0, 'n', 'y', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (307, 5, 1, 0, 'n', 'y', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (308, 5, 1, 0, 'n', 'y', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (309, 5, 1, 0, 'n', 'n', 1, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (310, 5, 1, 0, 'n', 'n', 1, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (311, 5, 1, 0, 'n', 'n', 1, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (312, 6, 2, 1, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (313, 6, 2, 1, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (314, 6, 2, 1, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (315, 6, 2, 1, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (316, 6, 2, 1, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (317, 6, 2, 1, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (318, 6, 2, 1, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (319, 6, 2, 1, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (320, 6, 2, 1, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (321, 6, 2, 1, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (322, 6, 2, 1, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (323, 6, 2, 1, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (324, 6, 2, 0, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (325, 6, 2, 0, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (326, 6, 2, 0, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (327, 6, 2, 0, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (328, 6, 2, 0, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (329, 6, 2, 0, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (330, 6, 2, 0, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (331, 6, 2, 0, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (332, 6, 2, 0, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (333, 6, 2, 0, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (334, 6, 2, 0, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (335, 6, 2, 0, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (336, 6, 3, 1, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (337, 6, 3, 1, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (338, 6, 3, 1, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (339, 6, 3, 1, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (340, 6, 3, 1, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (341, 6, 3, 1, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (342, 6, 3, 1, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (343, 6, 3, 1, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (344, 6, 3, 1, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (345, 6, 3, 1, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (346, 6, 3, 1, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (347, 6, 3, 1, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (348, 6, 3, 0, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (349, 6, 3, 0, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (350, 6, 3, 0, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (351, 6, 3, 0, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (352, 6, 3, 0, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (353, 6, 3, 0, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (354, 6, 3, 0, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (355, 6, 3, 0, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (356, 6, 3, 0, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (357, 6, 3, 0, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (358, 6, 3, 0, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (359, 6, 3, 0, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (360, 6, 1, 1, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (361, 6, 1, 1, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (362, 6, 1, 1, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (363, 6, 1, 1, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (364, 6, 1, 1, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (365, 6, 1, 1, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (366, 6, 1, 1, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (367, 6, 1, 1, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (368, 6, 1, 1, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (369, 6, 1, 1, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (370, 6, 1, 1, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (371, 6, 1, 1, 'n', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (372, 6, 1, 0, 'd', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (373, 6, 1, 0, 'd', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (374, 6, 1, 0, 'd', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (375, 6, 1, 0, 'd', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (376, 6, 1, 0, 'd', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (377, 6, 1, 0, 'd', 'n', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (378, 6, 1, 0, 'n', 'y', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (379, 6, 1, 0, 'n', 'y', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (380, 6, 1, 0, 'n', 'y', 5, 1);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (381, 6, 1, 0, 'n', 'n', 5, 3);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (382, 6, 1, 0, 'n', 'n', 5, 2);
INSERT INTO access_type_classifier (id, user_profile_environment_id, environment_type_id, weekday, shift, workday, access_type_id, frequency_level_id) VALUES (383, 6, 1, 0, 'n', 'n', 5, 1);


--
-- TOC entry 3541 (class 0 OID 0)
-- Dependencies: 219
-- Name: access_type_classifier_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('access_type_classifier_id_seq', 400, true);


--
-- TOC entry 3491 (class 0 OID 558998)
-- Dependencies: 206
-- Data for Name: access_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO access_types (id, name) VALUES (1, 'Blocked');
INSERT INTO access_types (id, name) VALUES (2, 'Guest');
INSERT INTO access_types (id, name) VALUES (3, 'Basic');
INSERT INTO access_types (id, name) VALUES (4, 'Advanced');
INSERT INTO access_types (id, name) VALUES (5, 'Administrative');


--
-- TOC entry 3542 (class 0 OID 0)
-- Dependencies: 205
-- Name: access_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('access_types_id_seq', 20, true);


--
-- TOC entry 3499 (class 0 OID 559064)
-- Dependencies: 214
-- Data for Name: actions; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (1, 1, 1, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (2, 1, 2, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (3, 1, 3, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (4, 1, 4, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (5, 1, 5, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (6, 1, 6, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (7, 1, 7, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (8, 1, 8, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (9, 1, 9, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (10, 1, 10, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (11, 1, 11, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (12, 1, 12, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (13, 1, 13, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (14, 1, 14, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (15, 1, 15, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (16, 2, 5, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (17, 3, 1, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (18, 3, 2, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (19, 3, 3, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (20, 3, 4, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (21, 3, 5, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (22, 3, 6, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (23, 3, 7, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (24, 3, 8, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (25, 3, 9, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (26, 3, 10, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (27, 3, 11, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (28, 3, 12, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (29, 3, 13, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (30, 3, 14, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (31, 3, 15, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (32, 4, 1, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (33, 4, 2, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (34, 4, 3, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (35, 4, 4, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (36, 4, 5, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (37, 4, 6, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (38, 4, 12, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (39, 4, 13, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (40, 4, 14, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (41, 4, 15, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (42, 5, 1, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (43, 5, 2, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (44, 5, 4, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (45, 5, 5, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (46, 5, 6, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (47, 5, 12, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (48, 5, 13, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (49, 5, 14, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (50, 5, 15, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (51, 6, 4, NULL, 'off', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (52, 6, 5, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (53, 6, 6, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (54, 6, 14, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (55, 6, 15, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (56, 7, 5, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (57, 8, 5, NULL, 'on', NULL, NULL, NULL, NULL);
INSERT INTO actions (id, access_level_id, functionality_id, custom_environment_id, action_name, start_date, end_date, start_daily_interval, interval_duration) VALUES (58, 8, 2, 7, 'on', NULL, NULL, NULL, NULL);


--
-- TOC entry 3501 (class 0 OID 559087)
-- Dependencies: 216
-- Data for Name: actions_args; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3543 (class 0 OID 0)
-- Dependencies: 215
-- Name: actions_args_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('actions_args_id_seq', 1, false);


--
-- TOC entry 3544 (class 0 OID 0)
-- Dependencies: 213
-- Name: actions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('actions_id_seq', 80, true);


--
-- TOC entry 3484 (class 0 OID 558945)
-- Dependencies: 199
-- Data for Name: communication_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO communication_types (id, name) VALUES (1, 'Socket');
INSERT INTO communication_types (id, name) VALUES (2, 'WebService SOAP');
INSERT INTO communication_types (id, name) VALUES (3, 'WebService Rest');
INSERT INTO communication_types (id, name) VALUES (4, 'Google Cloud Message');
INSERT INTO communication_types (id, name) VALUES (5, 'USB');
INSERT INTO communication_types (id, name) VALUES (6, 'RS-232');
INSERT INTO communication_types (id, name) VALUES (7, 'HTTP');


--
-- TOC entry 3545 (class 0 OID 0)
-- Dependencies: 198
-- Name: communication_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('communication_types_id_seq', 20, true);


--
-- TOC entry 3486 (class 0 OID 558953)
-- Dependencies: 201
-- Data for Name: device_communications; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO device_communications (id, name, address, parameters, address_format, port, device_id, communication_type_id, preferred_order) VALUES (1, 'Ethernet', '127.0.0.1', NULL, 'IPv4', '80', 1, 3, 1);
INSERT INTO device_communications (id, name, address, parameters, address_format, port, device_id, communication_type_id, preferred_order) VALUES (2, 'Wi-fi', '127.0.0.1', NULL, 'url', '80', 2, 3, 2);
INSERT INTO device_communications (id, name, address, parameters, address_format, port, device_id, communication_type_id, preferred_order) VALUES (3, 'Wi-fi', 'SERVICE_KEY', 'DEVICE_KEY', 'IPv4', '80', 2, 4, 1);


--
-- TOC entry 3546 (class 0 OID 0)
-- Dependencies: 200
-- Name: device_communications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('device_communications_id_seq', 20, true);


--
-- TOC entry 3489 (class 0 OID 558981)
-- Dependencies: 204
-- Data for Name: device_functionalities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO device_functionalities (device_id, functionality_id) VALUES (1, 1);
INSERT INTO device_functionalities (device_id, functionality_id) VALUES (1, 2);
INSERT INTO device_functionalities (device_id, functionality_id) VALUES (1, 3);
INSERT INTO device_functionalities (device_id, functionality_id) VALUES (1, 5);
INSERT INTO device_functionalities (device_id, functionality_id) VALUES (1, 9);
INSERT INTO device_functionalities (device_id, functionality_id) VALUES (1, 15);
INSERT INTO device_functionalities (device_id, functionality_id) VALUES (2, 1);
INSERT INTO device_functionalities (device_id, functionality_id) VALUES (2, 2);
INSERT INTO device_functionalities (device_id, functionality_id) VALUES (2, 3);
INSERT INTO device_functionalities (device_id, functionality_id) VALUES (2, 5);
INSERT INTO device_functionalities (device_id, functionality_id) VALUES (2, 9);


--
-- TOC entry 3480 (class 0 OID 558912)
-- Dependencies: 195
-- Data for Name: device_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO device_types (id, name) VALUES (1, 'Android');
INSERT INTO device_types (id, name) VALUES (2, 'Notebook');
INSERT INTO device_types (id, name) VALUES (3, 'Arduino');
INSERT INTO device_types (id, name) VALUES (4, 'VMAndroid');
INSERT INTO device_types (id, name) VALUES (5, 'iOS');
INSERT INTO device_types (id, name) VALUES (6, 'RFID Card');


--
-- TOC entry 3547 (class 0 OID 0)
-- Dependencies: 194
-- Name: device_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('device_types_id_seq', 20, true);


--
-- TOC entry 3482 (class 0 OID 558920)
-- Dependencies: 197
-- Data for Name: devices; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO devices (id, code, name, device_type_id, user_id, current_environment_id) VALUES (1, '1234554321', 'VitualMachineAndroid_2.3', 4, 1, NULL);
INSERT INTO devices (id, code, name, device_type_id, user_id, current_environment_id) VALUES (2, '1111111111', 'Tablet Lab. 205', 1, 1, NULL);
INSERT INTO devices (id, code, name, device_type_id, user_id, current_environment_id) VALUES (3, '5E-EF-8-9B-54', 'RFID Card - Borges', 6, 1, NULL);


--
-- TOC entry 3548 (class 0 OID 0)
-- Dependencies: 196
-- Name: devices_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('devices_id_seq', 20, true);


--
-- TOC entry 3510 (class 0 OID 559168)
-- Dependencies: 225
-- Data for Name: environment_frequency_levels; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO environment_frequency_levels (environment_id, frequency_level_id, min, max, period, metric) VALUES (1, 1, 0, 15, 20, 'd');
INSERT INTO environment_frequency_levels (environment_id, frequency_level_id, min, max, period, metric) VALUES (1, 2, 15, 50, 20, 'd');
INSERT INTO environment_frequency_levels (environment_id, frequency_level_id, min, max, period, metric) VALUES (1, 3, 50, 100, 20, 'd');


--
-- TOC entry 3472 (class 0 OID 558839)
-- Dependencies: 187
-- Data for Name: environment_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO environment_types (id, name) VALUES (1, 'Restrict');
INSERT INTO environment_types (id, name) VALUES (2, 'Private');
INSERT INTO environment_types (id, name) VALUES (3, 'Public');


--
-- TOC entry 3549 (class 0 OID 0)
-- Dependencies: 186
-- Name: environment_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('environment_types_id_seq', 20, true);


--
-- TOC entry 3476 (class 0 OID 558857)
-- Dependencies: 191
-- Data for Name: environments; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO environments (id, name, location, shape, operating_range, version, parent_environment_id, environment_type_id, localization_type_id, level) VALUES (1, 'Porto Alegre', '01010000A0E6100000FEFFFFFF81123EC0656666C6BC9649C00000000000002440', '0103000020E61000000100000006000000FEFFFF1F5E9949C0FEFFFF7F16F63DC002000080C9A549C0FEFFFF7F7E1B3EC0F9FFFF5FAA9B49C0F4FFFF7FF5393EC004000060538849C00200008051183EC0FEFFFF7F8A8E49C007000000AEF83DC0FEFFFF1F5E9949C0FEFFFF7F16F63DC0', 17550.7860000000001, 1, NULL, 3, 1, 0);
INSERT INTO environments (id, name, location, shape, operating_range, version, parent_environment_id, environment_type_id, localization_type_id, level) VALUES (2, 'Campus Vale UFRGS', '01010000A0E6100000040000D869123EC0FFFFFFC75E8F49C00000000000000000', '0103000020E61000000100000005000000FCFFFFFF848F49C0FCFFFFFFC4103EC0FBFFFFDF1F9049C00D0000407B133EC003000040BA8E49C0FAFFFF1F2B143EC0000000001C8F49C00E0000003C113EC0FCFFFFFF848F49C0FCFFFFFFC4103EC0', 903.524000000000001, 1, 1, 3, 1, 1);
INSERT INTO environments (id, name, location, shape, operating_range, version, parent_environment_id, environment_type_id, localization_type_id, level) VALUES (3, 'Prédio Informática 72', '01010000A0E6100000F8FFFF0F89113EC0FFFFFFC76B8F49C00000000000000000', '0103000020E61000000100000005000000030000406A8F49C0F4FFFFDF75113EC0000000A0738F49C0F5FFFFFF96113EC0FDFFFF5F6D8F49C0FCFFFF3F9C113EC0FBFFFFDF638F49C0FAFFFF1F7B113EC0030000406A8F49C0F4FFFFDF75113EC0', 33.1779999999999973, 1, 2, 2, 2, 2);
INSERT INTO environments (id, name, location, shape, operating_range, version, parent_environment_id, environment_type_id, localization_type_id, level) VALUES (4, 'Apartamento do Borges', '01010000A0E610000000000018340A3EC002000050BF9A49C00000000000000000', '0103000020E6100000010000000500000003000040C29A49C0FFFFFF3F3F0A3EC0FBFFFF7FBB9A49C0010000C0380A3EC005000080BC9A49C0FCFFFF3F2C0A3EC004000000C39A49C0050000202C0A3EC003000040C29A49C0FFFFFF3F3F0A3EC0', 20.8260000000000005, 2, 1, 2, 2, 1);
INSERT INTO environments (id, name, location, shape, operating_range, version, parent_environment_id, environment_type_id, localization_type_id, level) VALUES (5, 'Laboratório 205', '01010000A0E6100000019C61B88E113EC0000000336F8F49C00000000000000000', '0103000020E610000001000000050000000000401F708F49C01B0CE8308B113EC00000007C718F49C05EB7499A8F113EC0000000526E8F49C02509650992113EC0000000EA6C8F49C063C15F018E113EC00000401F708F49C01B0CE8308B113EC0', 5, 2, 3, 2, 2, 3);
INSERT INTO environments (id, name, location, shape, operating_range, version, parent_environment_id, environment_type_id, localization_type_id, level) VALUES (6, 'SENAI Porto Alegre', '01010000A0E6100000D51AD5941FFB3DC0000000F1CF8E49C00000000000000000', '0103000020E6100000010000000B00000000000072D68E49C08F0B5E0A0EFB3DC000008037CD8E49C0C6EF68BC0DFB3DC000008037CD8E49C059EB781F06FB3DC00000000BC58E49C05AC983D105FB3DC000008021C58E49C074F20B7B17FB3DC000000051CA8E49C074F20B7B17FB3DC00000803ACA8E49C046DECF2719FB3DC00000008CCB8E49C00B16AF111AFB3DC0000080A2CB8E49C06029F53E1FFB3DC000008088D68E49C06EB0EF651FFB3DC000000072D68E49C08F0B5E0A0EFB3DC0', 110, 1, 1, 3, 1, 1);
INSERT INTO environments (id, name, location, shape, operating_range, version, parent_environment_id, environment_type_id, localization_type_id, level) VALUES (7, 'Faculdade de Tecnologia Porto Alegre - FATEC SENAI', '01010000A0E61000008E907E4814FB3DC00000007BCD8E49C00000000000000000', '0103000020E6100000010000001700000000000047B68E49C0148F373700FB3DC0000000DEC48E49C06232DAA5FBFA3DC000000013CC8E49C0DC5C1020FAFA3DC00000009CD98E49C0FBB2FABBFAFA3DC0000000FBE38E49C02A08E557FBFA3DC000000030EB8E49C080CB6DB1FEFA3DC000000073F38E49C05A28C09003FB3DC0000000CAF68E49C00848127008FB3DC0000000D8F78E49C0270E6F010DFB3DC00000007EF78E49C0E6467FB413FB3DC0000000CDF38E49C06A2F79031BFB3DC0000000EFEF8E49C06FD0302624FB3DC00000009BE98E49C05F9DDC962DFB3DC000000063E58E49C03BAC21C432FB3DC000000074E38E49C0974B19133AFB3DC00000000FDF8E49C0DDC796BB44FB3DC000000061D88E49C0920708B24FFB3DC0000000C1D28E49C0D27199BE59FB3DC0000000A5D08E49C054282ACA5CFB3DC000000051CA8E49C0DABE60424DFB3DC00000004CC08E49C0EBA8A51C2FFB3DC000000025BA8E49C0F0CD3D8815FB3DC000000047B68E49C0148F373700FB3DC0', 48, 1, 6, 3, 1, 2);


--
-- TOC entry 3550 (class 0 OID 0)
-- Dependencies: 190
-- Name: environments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('environments_id_seq', 7, true);


--
-- TOC entry 3509 (class 0 OID 559162)
-- Dependencies: 224
-- Data for Name: frequency_levels; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO frequency_levels (id, name, level) VALUES (1, 'less_frequent', 1);
INSERT INTO frequency_levels (id, name, level) VALUES (2, 'normal', 2);
INSERT INTO frequency_levels (id, name, level) VALUES (3, 'frequent', 3);


--
-- TOC entry 3551 (class 0 OID 0)
-- Dependencies: 223
-- Name: frequency_levels_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('frequency_levels_id_seq', 20, true);


--
-- TOC entry 3488 (class 0 OID 558975)
-- Dependencies: 203
-- Data for Name: functionalities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO functionalities (id, name) VALUES (1, 'Bluetooth');
INSERT INTO functionalities (id, name) VALUES (2, 'Silent Mode');
INSERT INTO functionalities (id, name) VALUES (3, 'Vibrate Alert');
INSERT INTO functionalities (id, name) VALUES (4, 'Airplane Mode');
INSERT INTO functionalities (id, name) VALUES (5, 'Wi-Fi');
INSERT INTO functionalities (id, name) VALUES (6, 'Mobile Network Data Access');
INSERT INTO functionalities (id, name) VALUES (7, 'System Volume');
INSERT INTO functionalities (id, name) VALUES (8, 'Media Volume');
INSERT INTO functionalities (id, name) VALUES (9, 'Ringer Volume');
INSERT INTO functionalities (id, name) VALUES (10, 'Screen Timeout');
INSERT INTO functionalities (id, name) VALUES (11, 'Screen Brightness');
INSERT INTO functionalities (id, name) VALUES (12, 'SMS');
INSERT INTO functionalities (id, name) VALUES (13, 'Launch App');
INSERT INTO functionalities (id, name) VALUES (14, 'Camera Access');
INSERT INTO functionalities (id, name) VALUES (15, 'GPS');


--
-- TOC entry 3552 (class 0 OID 0)
-- Dependencies: 202
-- Name: functionalities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('functionalities_id_seq', 40, true);


--
-- TOC entry 3474 (class 0 OID 558847)
-- Dependencies: 189
-- Data for Name: localization_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO localization_types (id, name, "precision", metric) VALUES (1, 'GPS', 600, 'm2');
INSERT INTO localization_types (id, name, "precision", metric) VALUES (2, 'RFID', 3, 'm2');


--
-- TOC entry 3553 (class 0 OID 0)
-- Dependencies: 188
-- Name: localization_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('localization_types_id_seq', 20, true);


--
-- TOC entry 3503 (class 0 OID 559101)
-- Dependencies: 218
-- Data for Name: log_events; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3554 (class 0 OID 0)
-- Dependencies: 217
-- Name: log_events_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('log_events_id_seq', 1, false);


--
-- TOC entry 3468 (class 0 OID 558821)
-- Dependencies: 183
-- Data for Name: play_evolutions; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (1, '96d482bb06650d8692ddbeb112084cfd7bde4487', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS user_types CASCADE;
CREATE TABLE user_types (
id serial not null primary key,
name varchar(100) not null
);', 'DROP TABLE user_types;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (2, '0a30c35a39dea69b20ad342eab8fcef7094108cd', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS environment_types CASCADE;
CREATE TABLE environment_types (
id serial not null primary key,
name varchar(100) not null
);', 'DROP TABLE environment_types;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (3, '4dda3e35a85edb38871acc854c333ad73f705bbf', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS localization_types CASCADE;
CREATE TABLE localization_types (
id serial NOT NULL primary key,
name character varying(100) NOT NULL,
precision double precision not null default 5.0,
metric varchar(20) not null default ''m2''
);', 'DROP TABLE localization_types;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (4, 'f9389ccda8c895b99f9dd2f58e09b49855f28d46', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS environments CASCADE;
CREATE TABLE environments (
id serial not null primary key,
name varchar(100) not null,

-- replacement for (lat, lon, alt)
location geometry(PointZ,4326) not null,

-- replacement for environment_points, uses a POLYGON
shape geometry(Polygon, 4326),

operating_range double precision not null default 1.0,
version integer not null default 0,

parent_environment_id integer,
environment_type_id integer not null,
localization_type_id integer NOT NULL,

foreign key (parent_environment_id) references environments (id),
foreign key (environment_type_id) references environment_types (id),
foreign key (localization_type_id) references localization_types (id)
);

CREATE INDEX environment_location_idx ON environments USING GIST (location);
CREATE INDEX environment_shape_idx ON environments USING GIST (shape);', 'DROP INDEX IF EXISTS environment_location_idx;
DROP INDEX IF EXISTS environment_shape_idx;
DROP TABLE environments;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (5, 'aca8b72c2083a0f403e38c39a376e62d7f6830b7', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
id 	serial not null primary key,
name varchar(100) not null unique,
full_name character varying(120),

auth_token varchar(255),
email_address varchar(256) not null,
sha_password bytea not null,

user_type_id integer not null,
current_environment_id integer,

constraint uq_user_email_address unique (email_address),
foreign key (user_type_id) references user_types (id),
foreign key (current_environment_id) references environments (id)
);', 'DROP TABLE users;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (6, '65ce7a91ebf6f834eb42d490904cfba4a29c7161', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS device_types CASCADE;
CREATE TABLE device_types (
id serial not null primary key,
name varchar(100) not null
);', 'DROP TABLE device_types;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (7, '6107b20d68ba6591b575056a792888d77a986885', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS devices CASCADE;
CREATE TABLE devices (
id  serial not null primary key,
code varchar(50) not null unique,
name varchar(100) not null,

device_type_id integer not null,
user_id integer,
current_environment_id integer,

foreign key (device_type_id) references device_types (id),
foreign key (user_id) references users (id),
foreign key (current_environment_id) references environments (id)
);', 'DROP TABLE devices;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (8, 'a73d9239c2c8ece708f4f858f78842d4337c9b8d', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS communication_types CASCADE;
CREATE TABLE communication_types (
id serial not null primary key,
name varchar(100) not null
);', 'DROP TABLE communication_types;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (9, '53e869e4adb733cde3b2c90155a386dcfe2c6d2d', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS device_communications CASCADE;
CREATE TABLE device_communications (
id serial not null primary key,
name varchar(100) not null,
address varchar(100) not null,
parameters text,
address_format varchar(100),
port varchar(50),

device_id integer not null,
communication_type_id integer not null,
preferred_order integer not null default 1,

foreign key (device_id) references devices (id),
foreign key (communication_type_id) references communication_types (id)
);', 'DROP TABLE device_communications;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (10, 'ac113e5233044bb07c82225c694e38b674b732be', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS functionalities CASCADE;
CREATE TABLE functionalities (
id serial not null primary key,
name varchar(100) not null
);', 'DROP TABLE functionalities;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (11, '9dd29dfac244df08776f8ef208c46acd971ffefd', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS device_functionalities CASCADE;
CREATE TABLE device_functionalities (
device_id integer not null,
functionality_id integer not null,
primary key (device_id, functionality_id),
foreign key (device_id) references devices (id),
foreign key (functionality_id) references functionalities (id)
);', 'DROP TABLE device_functionalities;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (12, '8b751ad759e54438df897ccdb51eee17917e6b49', '2015-09-09 00:00:00', '--DROP TABLE IF EXISTS environment_points CASCADE;
--CREATE TABLE environment_points (
--	id serial not null primary key,
--	latitude double precision not null,
--	longitude double precision not null,
--	altitude double precision not null default 0.0,
--	point_order integer,

--	environment_id integer not null,

--	foreign key (environment_id) references environments (id)
--);', '--DROP TABLE environment_points;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (13, 'd8612aa2eda2b2fbcf4e472d420388c9adaac804', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS access_types CASCADE;
CREATE TABLE access_types (
id serial not null primary key,
name varchar(100) not null
);', 'DROP TABLE access_types;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (14, 'c94674f5b1fe5bf086f7f694746a190f5a25fef7', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS access_levels CASCADE;
CREATE TABLE access_levels (
id serial not null primary key,
impact_factor double precision not null,

environment_type_id integer not null,
access_type_id integer not null,

foreign key (environment_type_id) references environment_types (id),
foreign key (access_type_id) references access_types (id)
);', 'DROP TABLE access_levels;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (15, 'afc1f050942e34ef3e8cacd14dafaf2dc4f6021c', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS user_profile_environments CASCADE;
CREATE TABLE user_profile_environments (
id serial not null primary key,
name varchar(100) not null
);', 'DROP TABLE user_profile_environments;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (25, '4d286c9212a04829595a96bf28dbdd3c1332f471', '2015-09-09 00:00:00', 'ALTER TABLE access_type_classifier ADD COLUMN frequency_level_id integer NOT NULL;

ALTER TABLE access_type_classifier ADD CONSTRAINT access_type_classifier_frequency_level_id_fkey FOREIGN KEY (frequency_level_id)
REFERENCES frequency_levels (id);', 'ALTER TABLE access_type_classifier DROP CONSTRAINT access_type_classifier_frequency_level_id_fkey;
ALTER TABLE access_type_classifier DROP COLUMN frequency_level_id;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (26, 'e284465d66e68a9f17c82fe80794e53c77851b8c', '2015-09-09 00:00:00', 'ALTER TABLE users DROP COLUMN sha_password;', 'ALTER TABLE users ADD COLUMN sha_password bytea NULL;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (27, 'd1ec480cbe70644c0f58b732a458e417e4df603f', '2015-09-09 00:00:00', 'ALTER TABLE log_events DROP COLUMN event;
ALTER TABLE log_events DROP COLUMN code;
ALTER TABLE log_events ADD COLUMN exiting BOOLEAN NOT NULL DEFAULT FALSE;', 'ALTER TABLE log_events ADD COLUMN event CHARACTER VARYING(100);
ALTER TABLE log_events ADD COLUMN code CHARACTER VARYING(100);
ALTER TABLE log_events DROP COLUMN exiting;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (16, '4274a077e458a62622b3608cdc0681e4e444b292', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS user_in_environment CASCADE;
CREATE TABLE user_in_environment (
id serial not null unique,

user_id integer not null,
user_profile_environment_id integer not null default 1, -- 1 is Unknown for the environment
environment_id integer not null,
impact_factor double precision default 0.0,
current_access_type_id integer not null,

primary key (user_id,environment_id),

foreign key (user_profile_environment_id)
references user_profile_environments (id),
foreign key (environment_id) references environments (id),
foreign key (user_id) references users (id),
foreign key (current_access_type_id) references access_types (id)
);', 'DROP TABLE user_in_environment;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (17, 'c6c18f3163b4534b89e622d61ea74230dd490ec1', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS actions CASCADE;
CREATE TABLE actions (
id serial not null primary key,

access_level_id integer not null,
functionality_id integer not null,
custom_environment_id integer,

action_name varchar(100) not null,

start_date timestamp,
end_date timestamp,

start_daily_interval integer, -- tempo em segundos 60 = 1 min, 3600 = 1 hora etc. - até a hora 0
interval_duration integer, -- tempo em segundos 60 = 1 min, 3600 = 1 hora etc.

foreign key (access_level_id) references access_levels (id),
foreign key (functionality_id) references functionalities (id),
foreign key (custom_environment_id) references environments (id)
);', 'DROP TABLE actions;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (18, 'a7b4614104fd210bba8e84d4967b560d6ca09f9e', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS actions_args CASCADE;
CREATE TABLE actions_args (
id serial not null primary key,
label varchar(100) default null,
arg_value varchar(100) not null,

action_id integer not null,

foreign key (action_id) references actions (id)
);', 'DROP TABLE actions_args;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (19, '07fc043eea845c776250af8c3c72cdf2144b72f1', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS log_events CASCADE;
CREATE TABLE log_events (
id serial not null primary key,
log_time timestamp not null default now(),
shift char(1), -- ''d'' for diurnal shift, ''n'' for nocturnal shift
weekday int, -- 1 for yes, 2 for not
workday char(1), -- diz seo dia é dia útil ou se é um feriado, paralização ou final de semana que não se pode trabalhae. y for yes, n for not
event varchar(100) not null,
code varchar(100),

user_id integer,
device_id integer,
environment_id integer not null,

foreign key (environment_id) references environments (id),
foreign key (user_id) references users (id),
foreign key (device_id) references devices (id)
);', 'DROP TABLE log_events;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (20, 'de853a73d3d182f8420b91855d100b4388cbc3ca', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS access_type_classifier CASCADE;
CREATE TABLE access_type_classifier (
id serial not null primary key,

user_profile_environment_id integer not null,
environment_type_id integer not null,
weekday int not null,
shift char(1) not null,
workday char(1) not null,

access_type_id integer not null,

foreign key (user_profile_environment_id) references user_profile_environments (id),
foreign key (environment_type_id) references environment_types (id),
foreign key (access_type_id) references access_types (id)
);', 'DROP TABLE access_type_classifier;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (21, 'a9fb2070a639d79bc7f989de81b2ae93cafb7c22', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS user_credentials CASCADE;
CREATE TABLE user_credentials (
id serial not null primary key,
external_id character varying(255) NOT NULL,
system character varying(50) NOT NULL,
user_id integer NOT NULL,

foreign key (user_id) references users (id)
);

DROP INDEX IF EXISTS user_credentials_external_id_system_idx CASCADE;
CREATE INDEX user_credentials_external_id_system_idx ON user_credentials
USING btree
(
external_id ASC NULLS LAST,
system ASC NULLS LAST
);', 'DROP INDEX IF EXISTS user_credentials_external_id_system_idx CASCADE;
DROP TABLE user_credentials;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (22, 'c98bf7ed0ce6b6b3da2f4992a1d7eb4b1fd5bf75', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS frequency_levels CASCADE;

CREATE TABLE frequency_levels (
id serial not null primary key,
name character varying(100) NOT NULL,
level smallint NOT NULL
);', 'DROP TABLE frequency_levels;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (23, 'c8b541ddee0197fa21801ef3fe634de48d5e2596', '2015-09-09 00:00:00', 'DROP TABLE IF EXISTS environment_frequency_levels CASCADE;

CREATE TABLE environment_frequency_levels (
environment_id integer NOT NULL,
frequency_level_id integer NOT NULL,
min double precision NOT NULL,
max double precision NOT NULL,
period integer NOT NULL,
metric character(1) NOT NULL,

CONSTRAINT environment_frequency_levels_pkey PRIMARY KEY (environment_id, frequency_level_id),
FOREIGN KEY (environment_id) REFERENCES environments (id),
FOREIGN KEY (frequency_level_id) REFERENCES frequency_levels (id)
);', 'DROP TABLE environment_frequency_levels;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (24, '65d8c8526ead2355af89d5d95e06c4f1f94f65e8', '2015-09-09 00:00:00', 'ALTER TABLE user_in_environment ADD COLUMN current_frequency_level_id integer NULL;

ALTER TABLE user_in_environment ADD CONSTRAINT user_in_environment_current_freq_lev_fkey FOREIGN KEY (current_frequency_level_id)
REFERENCES frequency_levels (id);', 'ALTER TABLE user_in_environment DROP CONSTRAINT user_in_environment_current_freq_lev_fkey;
ALTER TABLE user_in_environment DROP COLUMN current_frequency_level_id;', 'applied', '');
INSERT INTO play_evolutions (id, hash, applied_at, apply_script, revert_script, state, last_problem) VALUES (28, '5e6bc805cfc9260f7780617979c3e8dafdbb8bf2', '2015-09-09 00:00:00', 'ALTER TABLE environments ADD COLUMN level integer not null default 0;', 'ALTER TABLE environments DROP COLUMN level;', 'applied', '');


--
-- TOC entry 3231 (class 0 OID 557794)
-- Dependencies: 171
-- Data for Name: spatial_ref_sys; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3507 (class 0 OID 559148)
-- Dependencies: 222
-- Data for Name: user_credentials; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO user_credentials (id, external_id, system, user_id) VALUES (1, '1234', 'SIGA-i', 2);


--
-- TOC entry 3555 (class 0 OID 0)
-- Dependencies: 221
-- Name: user_credentials_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('user_credentials_id_seq', 20, true);


--
-- TOC entry 3497 (class 0 OID 559032)
-- Dependencies: 212
-- Data for Name: user_in_environment; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO user_in_environment (id, user_id, user_profile_environment_id, environment_id, impact_factor, current_access_type_id, current_frequency_level_id) VALUES (1, 1, 3, 1, 0, 5, NULL);
INSERT INTO user_in_environment (id, user_id, user_profile_environment_id, environment_id, impact_factor, current_access_type_id, current_frequency_level_id) VALUES (2, 1, 5, 2, 0, 5, NULL);
INSERT INTO user_in_environment (id, user_id, user_profile_environment_id, environment_id, impact_factor, current_access_type_id, current_frequency_level_id) VALUES (3, 1, 5, 3, 0, 3, NULL);
INSERT INTO user_in_environment (id, user_id, user_profile_environment_id, environment_id, impact_factor, current_access_type_id, current_frequency_level_id) VALUES (4, 1, 6, 4, 0, 5, NULL);
INSERT INTO user_in_environment (id, user_id, user_profile_environment_id, environment_id, impact_factor, current_access_type_id, current_frequency_level_id) VALUES (5, 1, 5, 5, 0, 3, NULL);


--
-- TOC entry 3556 (class 0 OID 0)
-- Dependencies: 211
-- Name: user_in_environment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('user_in_environment_id_seq', 5, true);


--
-- TOC entry 3495 (class 0 OID 559024)
-- Dependencies: 210
-- Data for Name: user_profile_environments; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO user_profile_environments (id, name) VALUES (1, 'Unknown');
INSERT INTO user_profile_environments (id, name) VALUES (2, 'Transient');
INSERT INTO user_profile_environments (id, name) VALUES (3, 'User');
INSERT INTO user_profile_environments (id, name) VALUES (4, 'Responsible');
INSERT INTO user_profile_environments (id, name) VALUES (5, 'Student');
INSERT INTO user_profile_environments (id, name) VALUES (6, 'Manager');


--
-- TOC entry 3557 (class 0 OID 0)
-- Dependencies: 209
-- Name: user_profile_environments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('user_profile_environments_id_seq', 20, true);


--
-- TOC entry 3470 (class 0 OID 558831)
-- Dependencies: 185
-- Data for Name: user_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO user_types (id, name) VALUES (1, 'Normal');
INSERT INTO user_types (id, name) VALUES (2, 'Administrador');
INSERT INTO user_types (id, name) VALUES (3, 'Aluno');
INSERT INTO user_types (id, name) VALUES (4, 'Professor');
INSERT INTO user_types (id, name) VALUES (5, 'Coordenador');


--
-- TOC entry 3558 (class 0 OID 0)
-- Dependencies: 184
-- Name: user_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('user_types_id_seq', 20, true);


--
-- TOC entry 3478 (class 0 OID 558887)
-- Dependencies: 193
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users (id, name, full_name, auth_token, email_address, user_type_id, current_environment_id) VALUES (1, 'borges', 'Guilherme A. Borges', NULL, 'guilhermeborges@gmail.com', 1, NULL);
INSERT INTO users (id, name, full_name, auth_token, email_address, user_type_id, current_environment_id) VALUES (2, 'valderi', 'Valderi R. Q. Leithardt', NULL, 'valderi@gmail.com', 1, NULL);


--
-- TOC entry 3559 (class 0 OID 0)
-- Dependencies: 192
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 20, true);


--
-- TOC entry 3299 (class 2606 OID 559011)
-- Name: access_levels_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY access_levels
    ADD CONSTRAINT access_levels_pkey PRIMARY KEY (id);


--
-- TOC entry 3313 (class 2606 OID 559130)
-- Name: access_type_classifier_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY access_type_classifier
    ADD CONSTRAINT access_type_classifier_pkey PRIMARY KEY (id);


--
-- TOC entry 3297 (class 2606 OID 559003)
-- Name: access_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY access_types
    ADD CONSTRAINT access_types_pkey PRIMARY KEY (id);


--
-- TOC entry 3309 (class 2606 OID 559093)
-- Name: actions_args_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY actions_args
    ADD CONSTRAINT actions_args_pkey PRIMARY KEY (id);


--
-- TOC entry 3307 (class 2606 OID 559069)
-- Name: actions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY actions
    ADD CONSTRAINT actions_pkey PRIMARY KEY (id);


--
-- TOC entry 3289 (class 2606 OID 558950)
-- Name: communication_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY communication_types
    ADD CONSTRAINT communication_types_pkey PRIMARY KEY (id);


--
-- TOC entry 3291 (class 2606 OID 558962)
-- Name: device_communications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY device_communications
    ADD CONSTRAINT device_communications_pkey PRIMARY KEY (id);


--
-- TOC entry 3295 (class 2606 OID 558985)
-- Name: device_functionalities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY device_functionalities
    ADD CONSTRAINT device_functionalities_pkey PRIMARY KEY (device_id, functionality_id);


--
-- TOC entry 3283 (class 2606 OID 558917)
-- Name: device_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY device_types
    ADD CONSTRAINT device_types_pkey PRIMARY KEY (id);


--
-- TOC entry 3285 (class 2606 OID 558927)
-- Name: devices_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY devices
    ADD CONSTRAINT devices_code_key UNIQUE (code);


--
-- TOC entry 3287 (class 2606 OID 558925)
-- Name: devices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY devices
    ADD CONSTRAINT devices_pkey PRIMARY KEY (id);


--
-- TOC entry 3320 (class 2606 OID 559172)
-- Name: environment_frequency_levels_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY environment_frequency_levels
    ADD CONSTRAINT environment_frequency_levels_pkey PRIMARY KEY (environment_id, frequency_level_id);


--
-- TOC entry 3269 (class 2606 OID 558844)
-- Name: environment_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY environment_types
    ADD CONSTRAINT environment_types_pkey PRIMARY KEY (id);


--
-- TOC entry 3275 (class 2606 OID 558867)
-- Name: environments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY environments
    ADD CONSTRAINT environments_pkey PRIMARY KEY (id);


--
-- TOC entry 3318 (class 2606 OID 559167)
-- Name: frequency_levels_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY frequency_levels
    ADD CONSTRAINT frequency_levels_pkey PRIMARY KEY (id);


--
-- TOC entry 3293 (class 2606 OID 558980)
-- Name: functionalities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY functionalities
    ADD CONSTRAINT functionalities_pkey PRIMARY KEY (id);


--
-- TOC entry 3271 (class 2606 OID 558854)
-- Name: localization_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY localization_types
    ADD CONSTRAINT localization_types_pkey PRIMARY KEY (id);


--
-- TOC entry 3311 (class 2606 OID 559107)
-- Name: log_events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY log_events
    ADD CONSTRAINT log_events_pkey PRIMARY KEY (id);


--
-- TOC entry 3265 (class 2606 OID 558828)
-- Name: play_evolutions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY play_evolutions
    ADD CONSTRAINT play_evolutions_pkey PRIMARY KEY (id);


--
-- TOC entry 3277 (class 2606 OID 558899)
-- Name: uq_user_email_address; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uq_user_email_address UNIQUE (email_address);


--
-- TOC entry 3316 (class 2606 OID 559153)
-- Name: user_credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_credentials
    ADD CONSTRAINT user_credentials_pkey PRIMARY KEY (id);


--
-- TOC entry 3303 (class 2606 OID 559041)
-- Name: user_in_environment_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_in_environment
    ADD CONSTRAINT user_in_environment_id_key UNIQUE (id);


--
-- TOC entry 3305 (class 2606 OID 559039)
-- Name: user_in_environment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_in_environment
    ADD CONSTRAINT user_in_environment_pkey PRIMARY KEY (user_id, environment_id);


--
-- TOC entry 3301 (class 2606 OID 559029)
-- Name: user_profile_environments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_profile_environments
    ADD CONSTRAINT user_profile_environments_pkey PRIMARY KEY (id);


--
-- TOC entry 3267 (class 2606 OID 558836)
-- Name: user_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_types
    ADD CONSTRAINT user_types_pkey PRIMARY KEY (id);


--
-- TOC entry 3279 (class 2606 OID 558897)
-- Name: users_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_name_key UNIQUE (name);


--
-- TOC entry 3281 (class 2606 OID 558895)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3272 (class 1259 OID 558883)
-- Name: environment_location_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX environment_location_idx ON environments USING gist (location);


--
-- TOC entry 3273 (class 1259 OID 558884)
-- Name: environment_shape_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX environment_shape_idx ON environments USING gist (shape);


--
-- TOC entry 3314 (class 1259 OID 559159)
-- Name: user_credentials_external_id_system_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX user_credentials_external_id_system_idx ON user_credentials USING btree (external_id, system);


--
-- TOC entry 3334 (class 2606 OID 559017)
-- Name: access_levels_access_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY access_levels
    ADD CONSTRAINT access_levels_access_type_id_fkey FOREIGN KEY (access_type_id) REFERENCES access_types(id);


--
-- TOC entry 3333 (class 2606 OID 559012)
-- Name: access_levels_environment_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY access_levels
    ADD CONSTRAINT access_levels_environment_type_id_fkey FOREIGN KEY (environment_type_id) REFERENCES environment_types(id);


--
-- TOC entry 3349 (class 2606 OID 559141)
-- Name: access_type_classifier_access_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY access_type_classifier
    ADD CONSTRAINT access_type_classifier_access_type_id_fkey FOREIGN KEY (access_type_id) REFERENCES access_types(id);


--
-- TOC entry 3348 (class 2606 OID 559136)
-- Name: access_type_classifier_environment_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY access_type_classifier
    ADD CONSTRAINT access_type_classifier_environment_type_id_fkey FOREIGN KEY (environment_type_id) REFERENCES environment_types(id);


--
-- TOC entry 3350 (class 2606 OID 559188)
-- Name: access_type_classifier_frequency_level_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY access_type_classifier
    ADD CONSTRAINT access_type_classifier_frequency_level_id_fkey FOREIGN KEY (frequency_level_id) REFERENCES frequency_levels(id);


--
-- TOC entry 3347 (class 2606 OID 559131)
-- Name: access_type_classifier_user_profile_environment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY access_type_classifier
    ADD CONSTRAINT access_type_classifier_user_profile_environment_id_fkey FOREIGN KEY (user_profile_environment_id) REFERENCES user_profile_environments(id);


--
-- TOC entry 3340 (class 2606 OID 559070)
-- Name: actions_access_level_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY actions
    ADD CONSTRAINT actions_access_level_id_fkey FOREIGN KEY (access_level_id) REFERENCES access_levels(id);


--
-- TOC entry 3343 (class 2606 OID 559094)
-- Name: actions_args_action_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY actions_args
    ADD CONSTRAINT actions_args_action_id_fkey FOREIGN KEY (action_id) REFERENCES actions(id);


--
-- TOC entry 3342 (class 2606 OID 559080)
-- Name: actions_custom_environment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY actions
    ADD CONSTRAINT actions_custom_environment_id_fkey FOREIGN KEY (custom_environment_id) REFERENCES environments(id);


--
-- TOC entry 3341 (class 2606 OID 559075)
-- Name: actions_functionality_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY actions
    ADD CONSTRAINT actions_functionality_id_fkey FOREIGN KEY (functionality_id) REFERENCES functionalities(id);


--
-- TOC entry 3330 (class 2606 OID 558968)
-- Name: device_communications_communication_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY device_communications
    ADD CONSTRAINT device_communications_communication_type_id_fkey FOREIGN KEY (communication_type_id) REFERENCES communication_types(id);


--
-- TOC entry 3329 (class 2606 OID 558963)
-- Name: device_communications_device_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY device_communications
    ADD CONSTRAINT device_communications_device_id_fkey FOREIGN KEY (device_id) REFERENCES devices(id);


--
-- TOC entry 3331 (class 2606 OID 558986)
-- Name: device_functionalities_device_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY device_functionalities
    ADD CONSTRAINT device_functionalities_device_id_fkey FOREIGN KEY (device_id) REFERENCES devices(id);


--
-- TOC entry 3332 (class 2606 OID 558991)
-- Name: device_functionalities_functionality_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY device_functionalities
    ADD CONSTRAINT device_functionalities_functionality_id_fkey FOREIGN KEY (functionality_id) REFERENCES functionalities(id);


--
-- TOC entry 3328 (class 2606 OID 558938)
-- Name: devices_current_environment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY devices
    ADD CONSTRAINT devices_current_environment_id_fkey FOREIGN KEY (current_environment_id) REFERENCES environments(id);


--
-- TOC entry 3326 (class 2606 OID 558928)
-- Name: devices_device_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY devices
    ADD CONSTRAINT devices_device_type_id_fkey FOREIGN KEY (device_type_id) REFERENCES device_types(id);


--
-- TOC entry 3327 (class 2606 OID 558933)
-- Name: devices_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY devices
    ADD CONSTRAINT devices_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 3352 (class 2606 OID 559173)
-- Name: environment_frequency_levels_environment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY environment_frequency_levels
    ADD CONSTRAINT environment_frequency_levels_environment_id_fkey FOREIGN KEY (environment_id) REFERENCES environments(id);


--
-- TOC entry 3353 (class 2606 OID 559178)
-- Name: environment_frequency_levels_frequency_level_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY environment_frequency_levels
    ADD CONSTRAINT environment_frequency_levels_frequency_level_id_fkey FOREIGN KEY (frequency_level_id) REFERENCES frequency_levels(id);


--
-- TOC entry 3322 (class 2606 OID 558873)
-- Name: environments_environment_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY environments
    ADD CONSTRAINT environments_environment_type_id_fkey FOREIGN KEY (environment_type_id) REFERENCES environment_types(id);


--
-- TOC entry 3323 (class 2606 OID 558878)
-- Name: environments_localization_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY environments
    ADD CONSTRAINT environments_localization_type_id_fkey FOREIGN KEY (localization_type_id) REFERENCES localization_types(id);


--
-- TOC entry 3321 (class 2606 OID 558868)
-- Name: environments_parent_environment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY environments
    ADD CONSTRAINT environments_parent_environment_id_fkey FOREIGN KEY (parent_environment_id) REFERENCES environments(id);


--
-- TOC entry 3346 (class 2606 OID 559118)
-- Name: log_events_device_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY log_events
    ADD CONSTRAINT log_events_device_id_fkey FOREIGN KEY (device_id) REFERENCES devices(id);


--
-- TOC entry 3344 (class 2606 OID 559108)
-- Name: log_events_environment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY log_events
    ADD CONSTRAINT log_events_environment_id_fkey FOREIGN KEY (environment_id) REFERENCES environments(id);


--
-- TOC entry 3345 (class 2606 OID 559113)
-- Name: log_events_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY log_events
    ADD CONSTRAINT log_events_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 3351 (class 2606 OID 559154)
-- Name: user_credentials_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_credentials
    ADD CONSTRAINT user_credentials_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 3338 (class 2606 OID 559057)
-- Name: user_in_environment_current_access_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_in_environment
    ADD CONSTRAINT user_in_environment_current_access_type_id_fkey FOREIGN KEY (current_access_type_id) REFERENCES access_types(id);


--
-- TOC entry 3339 (class 2606 OID 559183)
-- Name: user_in_environment_current_freq_lev_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_in_environment
    ADD CONSTRAINT user_in_environment_current_freq_lev_fkey FOREIGN KEY (current_frequency_level_id) REFERENCES frequency_levels(id);


--
-- TOC entry 3336 (class 2606 OID 559047)
-- Name: user_in_environment_environment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_in_environment
    ADD CONSTRAINT user_in_environment_environment_id_fkey FOREIGN KEY (environment_id) REFERENCES environments(id);


--
-- TOC entry 3337 (class 2606 OID 559052)
-- Name: user_in_environment_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_in_environment
    ADD CONSTRAINT user_in_environment_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 3335 (class 2606 OID 559042)
-- Name: user_in_environment_user_profile_environment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_in_environment
    ADD CONSTRAINT user_in_environment_user_profile_environment_id_fkey FOREIGN KEY (user_profile_environment_id) REFERENCES user_profile_environments(id);


--
-- TOC entry 3325 (class 2606 OID 558905)
-- Name: users_current_environment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_current_environment_id_fkey FOREIGN KEY (current_environment_id) REFERENCES environments(id);


--
-- TOC entry 3324 (class 2606 OID 558900)
-- Name: users_user_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_user_type_id_fkey FOREIGN KEY (user_type_id) REFERENCES user_types(id);


--
-- TOC entry 3517 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-09-09 15:50:43 BRT

--
-- PostgreSQL database dump complete
--

