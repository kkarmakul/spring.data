
DROP TABLE client;
DROP TABLE client_phones;
CREATE TABLE client_phones(
  id bigserial NOT NULL,
  phone_number VARCHAR(15),
  phone_type VARCHAR(15),
  client_id bigint,
  CONSTRAINT phone_pkey PRIMARY KEY (id),
  CONSTRAINT client_phones_fk FOREIGN KEY (client_id)
      REFERENCES client (id) MATCH SIMPLE
);

INSERT INTO client(family_name, first_name) values ('A', 'B');
INSERT INTO client_phones(phone_number, phone_type, client_id) values ('7', 1, 1);
SELECT * FROM client_phones;