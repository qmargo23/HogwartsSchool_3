-- liquibase formatted sql

-- changeset by_owner:1
CREATE INDEX student_name_index ON student (name);

-- changeset by_owner:2
CREATE INDEX faculty_nc_index ON faculty (name, color);