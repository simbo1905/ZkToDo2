

CREATE TABLE reminder
(
  reminder_id bigint NOT NULL,
  date timestamp without time zone,
  name character varying(255),
  priority integer,
  CONSTRAINT reminder_pkey PRIMARY KEY (reminder_id )
)
