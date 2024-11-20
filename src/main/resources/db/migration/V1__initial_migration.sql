BEGIN;

-- Definição de tipos customizados
CREATE TYPE user_type AS ENUM ('ADMIN', 'CUSTOMER', 'RESTAURANT');
CREATE TYPE payment_type AS ENUM ('CREDIT_CARD', 'DEBIT_CARD', 'CASH', 'PIX');
CREATE TYPE order_status AS ENUM ('PENDING', 'CONFIRMED', 'OUT_FOR_DELIVERY', 'COMPLETED', 'CANCELLED');

-- Tabelas
CREATE TABLE IF NOT EXISTS public.users
(
    id bigserial NOT NULL,
    email character varying NOT NULL UNIQUE,
    password character varying NOT NULL,
    type user_type NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.restaurants
(
    id bigserial NOT NULL,
    user_id bigserial NOT NULL,
    name character varying NOT NULL,
    image character varying,
    postal_code character varying NOT NULL,
    address_line_1 character varying NOT NULL,
    address_line_2 character varying,
    city character varying NOT NULL,
    state character varying NOT NULL,
    country character varying NOT NULL,
    delivery_price numeric(10, 2) NOT NULL,
    delivery_radius numeric NOT NULL CHECK (delivery_radius > 0),
    latitude numeric(9, 6) NOT NULL,
    longitude numeric(9, 6) NOT NULL,
    phone_number character varying NOT NULL UNIQUE,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_restaurant_user FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.categories
(
    id bigserial NOT NULL,
    restaurant_id bigserial NOT NULL,
    name character varying NOT NULL,
    PRIMARY KEY (id),
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_category_restaurant FOREIGN KEY (restaurant_id) REFERENCES public.restaurants (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.products
(
    id bigserial NOT NULL,
    category_id bigserial NOT NULL,
    name character varying NOT NULL,
    description character varying NOT NULL,
    price numeric(10, 2) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES public.categories (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.customers
(
    id bigserial NOT NULL,
    user_id bigserial NOT NULL,
    name character varying NOT NULL,
    phone_number character varying NOT NULL UNIQUE,
    cpf character varying NOT NULL UNIQUE,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_customer_user FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.customer_addresses
(
    id bigserial NOT NULL,
    customer_id bigserial NOT NULL,
    postal_code character varying NOT NULL,
    address_line_1 character varying NOT NULL,
    address_line_2 character varying,
    city character varying NOT NULL,
    state character varying NOT NULL,
    country character varying NOT NULL,
    latitude numeric(9, 6) NOT NULL,
    longitude numeric(9, 6) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_customer_address_customer FOREIGN KEY (customer_id) REFERENCES public.customers (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.carts
(
    id bigserial NOT NULL,
    restaurant_id bigserial NOT NULL,
    customer_address_id bigserial NOT NULL,
    payment_type payment_type NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_cart_restaurant FOREIGN KEY (restaurant_id) REFERENCES public.restaurants (id) ON DELETE RESTRICT,
    CONSTRAINT fk_cart_customer_address FOREIGN KEY (customer_address_id) REFERENCES public.customer_addresses (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.cart_items
(
    id bigserial NOT NULL,
    cart_id bigserial NOT NULL,
    product_id bigserial NOT NULL,
    quantity integer NOT NULL CHECK (quantity > 0),
    notes character varying,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES public.carts (id) ON DELETE RESTRICT,
    CONSTRAINT fk_cart_item_product FOREIGN KEY (product_id) REFERENCES public.products (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.orders
(
    id bigserial NOT NULL,
    restaurant_id bigserial NOT NULL,
    customer_id bigserial NOT NULL,
    postal_code character varying NOT NULL,
    address_line_1 character varying NOT NULL,
    address_line_2 character varying,
    city character varying NOT NULL,
    state character varying NOT NULL,
    country character varying NOT NULL,
    latitude numeric(9, 6) NOT NULL,
    longitude numeric(9, 6) NOT NULL,
    payment_type payment_type NOT NULL,
    total_price numeric(10, 2) CHECK (total_price >= 0),
    status order_status NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_restaurant FOREIGN KEY (restaurant_id) REFERENCES public.restaurants (id) ON DELETE RESTRICT,
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES public.customers (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.order_items
(
    id bigserial NOT NULL,
    order_id bigserial NOT NULL,
    product_id bigserial NOT NULL,
    quantity integer NOT NULL CHECK (quantity > 0),
    notes character varying,
    price numeric(10, 2) NOT NULL CHECK (price >= 0),
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES public.orders (id) ON DELETE RESTRICT,
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES public.products (id) ON DELETE RESTRICT
);

END;
