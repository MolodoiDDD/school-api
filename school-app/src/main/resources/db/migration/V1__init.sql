DROP TABLE IF EXISTS teachers;
CREATE TABLE teachers (
    id BIGSERIAL PRIMARY KEY,
	last_name VARCHAR(255) NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	middle_name VARCHAR(255),
    created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
	deleted BOOLEAN DEFAULT FALSE
);
COMMENT ON TABLE teachers IS 'Таблица преподавателей школы';

COMMENT ON COLUMN teachers.id IS 'ID преподавателя';
COMMENT ON COLUMN teachers.last_name IS 'Фамилия преподавателя';
COMMENT ON COLUMN teachers.first_name IS 'Имя преподавателя';
COMMENT ON COLUMN teachers.middle_name IS 'Отчество преподавателя (может отсутствовать)';
COMMENT ON COLUMN teachers.created IS 'Дата и время создания записи о преподавателе';
COMMENT ON COLUMN teachers.modified IS 'Дата и время последнего изменения записи';
COMMENT ON COLUMN teachers.deleted IS 'Логическое удаления (FALSE = существует)';

DROP TABLE IF EXISTS classes;
CREATE TABLE classes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE,
    created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
	deleted BOOLEAN DEFAULT FALSE
);
COMMENT ON TABLE classes IS 'Таблица классов';

COMMENT ON COLUMN classes.id IS 'ID класса';
COMMENT ON COLUMN classes.name IS 'Название класса (9А, 10Б и т.д)';
COMMENT ON COLUMN classes.created IS 'Дата и время создания записи о классe';
COMMENT ON COLUMN classes.modified IS 'Дата и время последнего изменения';
COMMENT ON COLUMN classes.deleted IS 'Логическое удаления (FALSE = существует)';

DROP TABLE IF EXISTS subjects;
CREATE TABLE subjects (
	id BIGSERIAL PRIMARY KEY,
	subject_name VARCHAR(255) NOT NULL UNIQUE,
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
	deleted BOOLEAN DEFAULT FALSE
);

COMMENT ON TABLE subjects IS 'Таблица учебных предметов';

COMMENT ON COLUMN subjects.id IS 'ID предмета';
COMMENT ON COLUMN subjects.subject_name IS 'Название предмета';
COMMENT ON COLUMN subjects.created IS 'Дата и время создания записи о предмете';
COMMENT ON COLUMN subjects.modified IS 'Дата и время последнего изменения';
COMMENT ON COLUMN subjects.deleted IS 'Логическое удаления (FALSE = существует)';

DROP TABLE IF EXISTS rooms;
CREATE TABLE rooms (
	id BIGSERIAL PRIMARY KEY,
	room_number INT NOT NULL CHECK (room_number > 0) UNIQUE,
	capacity INT CHECK (capacity > 0),
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
	deleted BOOLEAN DEFAULT FALSE
);

COMMENT ON TABLE rooms IS 'Таблица кабинетов';

COMMENT ON COLUMN rooms.id IS 'ID кабинета';
COMMENT ON COLUMN rooms.room_number IS 'Номер кабинета';
COMMENT ON COLUMN rooms.capacity IS 'Вместимость кабинета';
COMMENT ON COLUMN rooms.created IS 'Дата и время создания записи о кабинете';
COMMENT ON COLUMN rooms.modified IS 'Дата и время последнего изменения';
COMMENT ON COLUMN rooms.deleted IS 'Логическое удаления (FALSE = существует)';
	

DROP TABLE IF EXISTS lessons;
CREATE TABLE lessons (
    id BIGSERIAL PRIMARY KEY,
	
    class_id BIGINT NOT NULL REFERENCES classes(id),
    teacher_id BIGINT NOT NULL REFERENCES teachers(id),
	subject_id BIGINT NOT NULL REFERENCES subjects(id),
	room_id BIGINT NOT NULL REFERENCES rooms(id),
	

    day_of_week INTEGER NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
	
    created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
	deleted BOOLEAN DEFAULT FALSE,
	
    UNIQUE(day_of_week, start_time, room_id),
	
	UNIQUE(teacher_id, day_of_week, start_time),
	
	UNIQUE(class_id, day_of_week, start_time)
);

COMMENT ON TABLE lessons IS 'Таблица уроков';

COMMENT ON COLUMN lessons.id IS 'ID урока в расписании';
COMMENT ON COLUMN lessons.class_id IS 'ID ключа класса';
COMMENT ON COLUMN lessons.teacher_id IS 'ID ключа преподавателя';
COMMENT ON COLUMN lessons.subject_id IS 'ID ключа предмета';
COMMENT ON COLUMN lessons.room_id IS 'Номер кабинета';

COMMENT ON COLUMN lessons.day_of_week IS 'День недели занития: 1 = ПН, 7 = ВС';
COMMENT ON COLUMN lessons.start_time IS 'Время начала урока';
COMMENT ON COLUMN lessons.end_time IS 'Время окончания урока';

COMMENT ON COLUMN lessons.created IS 'Дата и время создания записи об уроке';
COMMENT ON COLUMN lessons.modified IS 'Дата и время последнего изменения';
COMMENT ON COLUMN lessons.deleted IS 'Логическое удаления (FALSE = существует)';
