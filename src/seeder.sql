use adlister_db;

INSERT INTO users (username, email, password)
VALUES ('bob-o', 'bobby@email.com', 'password'),
       ('sally-smith', 'sally@email.com', 'password');

INSERT INTO ads (user_id, title, description)
VALUES

(2, '2001 Honda Civic', '500,000 miles and it still runs!'),

(2, '2015 F-150', 'It is a white truck'),

(1, '1965 Mustang', 'Fully restored'),

(1, '3 tires from a 1996 Toyota Camry', 'Cannot find the 4th'),

(1, '2020 Tesla Model-3', 'Cannot find battery but is a Tesla');
