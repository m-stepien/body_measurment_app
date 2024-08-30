CREATE table IF NOT EXISTS additional_circumference (
	id SERIAL primary key,
	thighL numeric(5,2),
	thighR numeric(5,2),
	armL numeric(4,2),
	armR numeric(4,2),
	calfL numeric(4,2),
	calfR numeric(4,2),
	forearmL numeric(4,2),
	forearmR numeric(4,2),
	neck numeric(4,2)
)

CREATE table IF NOT EXISTS basic_circumference (
	id SERIAL primary key,
	measurment_date DATE not null,
	weist numeric(5,2) not null,
	chest numeric(5,2) not null,
	abdominal numeric(5,2) not null,
	hip numeric(5,2) not null,
	additional_measurement_id INT,
	FOREIGN KEY (additional_measurement_id) REFERENCES additional_circumference(id) ON DELETE cascade
)

create table IF NOT EXISTS basic_body_data(
    id serial primary key,
    measurment_date DATE not null,
    height_in_cm numeric(5,2) not null,
    weight_in_kg numeric(5,2) not null,
    body_fat_percent INT
)