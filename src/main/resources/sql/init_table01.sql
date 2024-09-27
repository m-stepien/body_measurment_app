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
	waist numeric(5,2) not null,
	chest numeric(5,2) not null,
	abdominal numeric(5,2) not null,
	hip numeric(5,2) not null
)

CREATE table IF NOT EXISTS circumference_data(
    id SERIAL primary key,
    additional_circumference_id INT,
    basic_circumference_id INT,
    measurement_date DATE not null,
    FOREIGN KEY (additional_circumference_id) REFERENCES additional_circumference(id) ON DELETE cascade,
    FOREIGN KEY (basic_circumference_id) REFERENCES basic_circumference(id) ON DELETE cascade
)
