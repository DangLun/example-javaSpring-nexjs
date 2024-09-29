CREATE TABLE Customers (
    customer_id SERIAL PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(15),
    customer_picture VARCHAR(255),
    customer_email VARCHAR(255) UNIQUE
);

CREATE TABLE Roles (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Accounts (
    account_id SERIAL PRIMARY KEY,
    account_username VARCHAR(255) UNIQUE NOT NULL,
	account_password VARCHAR(255),
	role_id integer REFERENCES Roles(role_id) ON DELETE CASCADE,
	account_isban Boolean default false,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	account_log_date TIMESTAMP,
    customer_id INTEGER REFERENCES Customers(customer_id) ON DELETE CASCADE
);

CREATE TABLE Status (
    status_id SERIAL PRIMARY KEY,
    status_name VARCHAR(255) NOT NULL
);

CREATE TABLE Orders (
    order_id SERIAL PRIMARY KEY,
    order_date TIMESTAMP,
	order_address varchar(255),
	status_id integer references Status(status_id) on DELETE CASCADE,
	customer_id integer references Customers(customer_id) on DELETE CASCADE
);

CREATE TABLE Authors (
    author_id serial primary key NOT NULL,
    author_name varchar(255) NOT NULL,
    author_address varchar(255),
    author_bio varchar(255),
	author_phone varchar(15),
	create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create Table Topics (
	topic_id serial primary key NOT NULL,
	topic_name varchar(255),
	create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Books (
    book_id SERIAL PRIMARY KEY,
    book_name varchar(255) NOT NULL,
	book_price float,
	book_dics text,
	book_picture varchar(255),
	book_quantity integer,
	book_discount float,
	author_id integer REFERENCES Authors(author_id) on delete cascade,
	topic_id integer REFERENCES Topics(topic_id) on delete cascade,
	create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE OrderDetails (
    book_id INTEGER NOT NULL,
    order_id INTEGER NOT NULL,
    od_buy_quantity INTEGER NOT NULL,
    od_buy_price NUMERIC(10, 2) NOT NULL,
    PRIMARY KEY (book_id, order_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id) on delete cascade,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id) on delete cascade
);

CREATE TABLE Carts (
    book_id INTEGER NOT NULL,
    customer_id INTEGER NOT NULL,
    cart_quantiy INTEGER,
    PRIMARY KEY (book_id, customer_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id) on delete cascade,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) on delete cascade
);

-- Add Row Data

INSERT INTO Customers (customer_name, customer_phone, customer_picture, customer_email) VALUES
('Alice Johnson', '123-456-7890', 'alice.jpg', 'alice@example.com'),
('Bob Smith', '234-567-8901', 'bob.jpg', 'bob@example.com'),
('Charlie Brown', '345-678-9012', 'charlie.jpg', 'charlie@example.com'),
('David Wilson', '456-789-0123', 'david.jpg', 'david@example.com'),
('Emily Davis', '567-890-1234', 'emily.jpg', 'emily@example.com');

INSERT INTO Roles (role_name) VALUES
('Admin'),
('Manager'),
('Sales'),
('Customer Service'),
('Support');

INSERT INTO Accounts (account_username, account_password, role_id, account_isban, account_log_date, customer_id) VALUES
('alice_johnson', 'password123', 1, FALSE, CURRENT_TIMESTAMP, 1),
('bob_smith', 'password456', 2, FALSE, CURRENT_TIMESTAMP, 2),
('charlie_brown', 'password789', 3, FALSE, CURRENT_TIMESTAMP, 3),
('david_wilson', 'password321', 4, FALSE, CURRENT_TIMESTAMP, 4),
('emily_davis', 'password654', 5, FALSE, CURRENT_TIMESTAMP, 5);
INSERT INTO Status (status_name) VALUES
('Pending'),
('Processing'),
('Shipped'),
('Delivered'),
('Cancelled');

INSERT INTO Orders (order_date, order_address, status_id, customer_id) VALUES
(CURRENT_TIMESTAMP, '123 Elm St', 1, 1),
(CURRENT_TIMESTAMP, '456 Oak St', 2, 2),
(CURRENT_TIMESTAMP, '789 Pine St', 3, 3),
(CURRENT_TIMESTAMP, '101 Maple St', 4, 4),
(CURRENT_TIMESTAMP, '202 Birch St', 5, 5);

INSERT INTO Authors (author_name, author_address, author_bio, author_phone) VALUES
('John Doe', '123 Author Rd', 'Famous writer of fiction.', '555-1234'),
('Jane Roe', '456 Writer Ave', 'Known for non-fiction works.', '555-5678'),
('Mark Twain', '789 Literary Ln', 'Classic American author.', '555-9101'),
('J.K. Rowling', '101 Magic St', 'Author of the Harry Potter series.', '555-1122'),
('Stephen King', '202 Horror Blvd', 'Master of horror fiction.', '555-3344');

INSERT INTO Topics (topic_name) VALUES
('Fiction'),
('Non-Fiction'),
('Science Fiction'),
('Fantasy'),
('Horror');

INSERT INTO Books (book_name, book_price, book_dics, book_picture, book_quantity, book_discount, author_id, topic_id) VALUES
('Book One', 19.99, 'A thrilling fiction book.', 'book_one.jpg', 10, 0.1, 1, 1),
('Book Two', 29.99, 'An informative non-fiction book.', 'book_two.jpg', 15, 0.2, 2, 2),
('Book Three', 39.99, 'A science fiction adventure.', 'book_three.jpg', 5, 0.15, 3, 3),
('Book Four', 49.99, 'A fantasy novel with magic.', 'book_four.jpg', 8, 0.25, 4, 4),
('Book Five', 59.99, 'A horror story with chilling details.', 'book_five.jpg', 3, 0.3, 5, 5);

INSERT INTO OrderDetails (book_id, order_id, od_buy_quantity, od_buy_price) VALUES
(1, 1, 2, 19.99),
(2, 2, 1, 29.99),
(3, 3, 3, 39.99),
(4, 4, 1, 49.99),
(5, 5, 2, 59.99);

INSERT INTO Carts (book_id, customer_id, cart_quantiy) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 3, 3),
(4, 4, 1),
(5, 5, 2);






