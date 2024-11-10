create table if not exists user_accounts
(
    id serial primary key,
    login varchar unique,
    password varchar
);
