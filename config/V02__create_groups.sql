create table groups (
	id SERIAL primary key,
	name character VARYING(100) unique not null,
	teacher_id int not null REFERENCES users(id),
	is_active bool not null DEFAULT true
);

create table user_group (
	user_id int not null REFERENCES users(id),
	group_id int not null REFERENCES groups(id),
	primary key (user_id, group_id)
);
