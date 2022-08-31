-- 1. Автоматическая генерация значения ID (Автоинкремент, Serial)
create table person
(
    id    int GENERATED BY DEFAULT AS IDENTITY,
    name  varchar,
    age   int,
    email varchar
);

-- 2. Ограничения (Constraints) - NOT NULL, UNIQUE, PRIMARY KEY, CHECK
create table person
(
    id    int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name  varchar NOT NULL,
    age   int CHECK (age > 0),
    email varchar UNIQUE
)