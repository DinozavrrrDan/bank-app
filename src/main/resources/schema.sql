CREATE TABLE IF NOT EXISTS user_accounts
(
    id SERIAL PRIMARY KEY,
    login VARCHAR UNIQUE,
    password VARCHAR
);
CREATE TABLE IF NOT EXISTS incomes
(
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES user_accounts(id) ON DELETE CASCADE,
    source_in VARCHAR,
    amount DECIMAL(10, 2),
    date_added DATE
);

CREATE TABLE IF NOT EXISTS categories
(
    id SERIAL PRIMARY KEY,
    user_id  INT  REFERENCES user_accounts(id) ON DELETE CASCADE,
    description VARCHAR,
    date_added DATE
);

CREATE TABLE IF NOT EXISTS expenses
(
    id SERIAL PRIMARY KEY,
    user_id  INT  REFERENCES user_accounts(id) ON DELETE CASCADE,
    description VARCHAR,
    amount DECIMAL(10, 2),
    date_added DATE,
    category_id INT REFERENCES categories(id) ON DELETE CASCADE
);



