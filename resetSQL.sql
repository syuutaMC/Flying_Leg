/**
 * データベース初期化用SQL
 * Author:  syuuta
 * Created: 2021/02/23
 */
DROP TABLE CUSTOMERS CASCADE CONSTRAINT;
DROP TABLE EMPLOYEES CASCADE CONSTRAINT;
DROP TABLE EMPLOYEE_TYPES CASCADE CONSTRAINT;
DROP TABLE ITEMS CASCADE CONSTRAINT;
DROP TABLE ITEM_CATEGORIES CASCADE CONSTRAINT;
DROP TABLE ORDERS CASCADE CONSTRAINT;
DROP TABLE ORDER_DETAILS CASCADE CONSTRAINT;
DROP TABLE STORES CASCADE CONSTRAINT;

create table CUSTOMERS
(
    CUSTOMER_NUMBER NUMERIC(6) not null constraint CUSTOMER_NUMBER_PK primary key,
    NAME VARCHAR2(30),
    PHONE_NUMBER VARCHAR2(20) constraint CUSTOMERS_PHONE_NUMBER_UQ UNIQUE,
    ADDRESS VARCHAR2(50),
    DELIVERY_NOTE VARCHAR2(30)
);

INSERT INTO customers(CUSTOMER_NUMBER, NAME, PHONE_NUMBER, ADDRESS, DELIVERY_NOTE)
VALUES(1, 'サカモト シュウタ', '07011112222', '東京都千代田区千代田1-1', 'オートロックマンション');

INSERT INTO customers(CUSTOMER_NUMBER, NAME, PHONE_NUMBER, ADDRESS, DELIVERY_NOTE)
VALUES(2, 'マツイ トモキ', '07022223333', '東京都千代田区永田町1-7-1', '団地');

INSERT INTO customers(CUSTOMER_NUMBER, NAME, PHONE_NUMBER, ADDRESS, DELIVERY_NOTE)
VALUES(3, 'スズキ ヨシノブ', '0333699333', '新宿区百人町1-25-4', '');

INSERT INTO customers(CUSTOMER_NUMBER, NAME, PHONE_NUMBER, ADDRESS, DELIVERY_NOTE)
VALUES(4, 'スガワラ ダイスケ', '0333334444', '新宿区西新宿2-2-2', '');

INSERT INTO customers(CUSTOMER_NUMBER, NAME, PHONE_NUMBER, ADDRESS, DELIVERY_NOTE)
VALUES(5, 'ナラハラ ケイコ', '0311112222', '東京都千代田区千代田1-1', '');

-- ALTER TABLE customers
-- 	MODIFY customer_number default CUSTOMERS_CUSTOMER_NO.NEXTVAL;

create table EMPLOYEE_TYPES
(
	TYPE_NUMBER VARCHAR2(10) not null constraint TYPE_NUMBER_PK primary key,
	TYPE_NAME VARCHAR2(20) not null constraint EMPLOYEE_TYPES_TYPE_NAME_UQ UNIQUE
);

create table EMPLOYEES
(
    EMPLOYEE_NAME VARCHAR2(30) not null,
    PASSWORD VARCHAR2(20) not null,
    EMPLOYEE_NUMBER NUMERIC(4) not null constraint EMPLOYEE_NUMBER_PK primary key,
    TYPE_NUMBER VARCHAR2(10) not null,
    CONSTRAINT EMP_NUMBER_EMP_TYPE_NUMBER_FK
            FOREIGN KEY(type_number) REFERENCES EMPLOYEE_TYPES(type_number)
);

INSERT INTO EMPLOYEE_TYPES(TYPE_NUMBER, TYPE_NAME)
VALUES('M', 'マネージャ');

INSERT INTO EMPLOYEE_TYPES(TYPE_NUMBER, TYPE_NAME)
VALUES('A', 'アルバイト');

INSERT INTO EMPLOYEES(EMPLOYEE_NUMBER, EMPLOYEE_NAME, PASSWORD, TYPE_NUMBER)
VALUES (1, 'サカモト シュウタ', 'test0123', 'M');

INSERT INTO EMPLOYEES(EMPLOYEE_NUMBER, EMPLOYEE_NAME, PASSWORD, TYPE_NUMBER)
VALUES (2, 'マツイ トモキ', 'test0123', 'A');

-- ALTER TABLE employees
-- 	MODIFY employees_number default EMPLOYEES_EMPLOYEE_NO.NEXTVAL.nextval;

CREATE TABLE item_categories
(
    CATEGORY_NUMBER VARCHAR2(4) not null constraint CATEGORY_NUMBER_PK primary key,
    CATEGORY_NAME VARCHAR2(20) not null
);

create table ITEMS
(
    ITEM_NUMBER VARCHAR2(4) not null constraint ITEM_NUMBER_PK primary key,
    ITEM_NAME VARCHAR2(30) not null,
    UNIT_PRICE NUMERIC(7) not null,
    CATEGORY_NUMBER VARCHAR2(4),
    CONSTRAINT ITEMS_CATEGORY_NO_FK
        FOREIGN KEY(category_number) REFERENCES item_categories(category_number)
);

INSERT INTO item_categories(category_number, category_name) VALUES('M', 'メインメニュー');
INSERT INTO item_categories(category_number, category_name) VALUES('S', 'サイドメニュー');
INSERT INTO item_categories(category_number, category_name) VALUES('D', 'ドリンクメニュー');

INSERT INTO items(item_number, item_name, unit_price, category_number)
    VALUES ('M01', 'ステーキS', 1000, 'M');

INSERT INTO items(item_number, item_name, unit_price, category_number)
    VALUES ('M02', 'ステーキM', 1250, 'M');

INSERT INTO items(item_number, item_name, unit_price, category_number)
    VALUES ('M03', 'ステーキL', 1500, 'M');

INSERT INTO items(item_number, item_name, unit_price, category_number)
    VALUES ('S01', 'やばいサラダ', 200, 'S');

INSERT INTO items(item_number, item_name, unit_price, category_number)
    VALUES ('S02', '海藻サラダ', 230, 'S');

INSERT INTO items(item_number, item_name, unit_price, category_number)
    VALUES ('S03', 'スイートコーン', 250, 'S');

INSERT INTO items(item_number, item_name, unit_price, category_number)
    VALUES ('S04', 'ポテト', 200, 'S');

INSERT INTO items(item_number, item_name, unit_price, category_number)
    VALUES ('D01', 'アセロラジュース', 200, 'D');

INSERT INTO items(item_number, item_name, unit_price, category_number)
    VALUES ('D02', 'やばいお茶', 300, 'D');

INSERT INTO items(item_number, item_name, unit_price, category_number)
    VALUES ('D03', '元気ドリンコ', 100, 'D');


create table STORES
(
    STORE_NUMBER CHAR(3) not null constraint STORE_NUMBER_PK primary key,
    STORE_NAME VARCHAR2(30) not null,
    PHONE_NUMBER VARCHAR2(20) not null
);

INSERT INTO stores(store_number, store_name, phone_number)
    VALUES('001', '大久保店', '05011110000');

create table ORDER_DETAILS
(
    ITEM_NUMBER VARCHAR2(4),
    ORDER_NUMBER NUMERIC(6),
    ORDER_QUANTITY NUMERIC(4),
    CONSTRAINT ORDER_DETAILS_ITEM_NO_FK
            FOREIGN KEY(item_number) REFERENCES items(item_number)
);

create table ORDERS
(
    ORDER_NUMBER NUMERIC(6) not null constraint ORDER_NUMBER_PK primary key,
    CUSTOMER_NUMBER NUMERIC(6) not null,
    DELIVERY_TIME TIMESTAMP(7) default sysdate not null,
    DELIVERY_TO_ADDRESS VARCHAR2(50) not null,
    PAYMENT_DAY TIMESTAMP(7),
    STORE_NUMBER CHAR(3) not null
);

ALTER TABLE orders ADD CONSTRAINT ORDER_CUSTOMER_NO_FK
            FOREIGN KEY(customer_number) REFERENCES customers(customer_number);

ALTER TABLE orders ADD CONSTRAINT ORDERS_STORE_NUMBER_FK
            FOREIGN KEY(store_number) REFERENCES stores(store_number);

ALTER TABLE order_details ADD CONSTRAINT ORDER_DETAILS_ORDER_NO_FK
            FOREIGN KEY(order_number) REFERENCES orders(order_number);
-- 
INSERT INTO ORDERS (ORDER_NUMBER, CUSTOMER_NUMBER, DELIVERY_TO_ADDRESS, PAYMENT_DAY, STORE_NUMBER)
    VALUES('001', '1', '東京都千代田区千代田1-1', '', '001');

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('M03', 1, 3);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S01', 1, 1);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S02', 1, 1);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('D01', 1, 1);

--
INSERT INTO ORDERS (ORDER_NUMBER, CUSTOMER_NUMBER, DELIVERY_TO_ADDRESS, PAYMENT_DAY, STORE_NUMBER)
    VALUES('002', '2', '東京都千代田区永田町1-7-1', '', '001');

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('M02', 2, 3);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S01', 2, 1);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S02', 2, 1);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('D02', 2, 1);

--
INSERT INTO ORDERS (ORDER_NUMBER, CUSTOMER_NUMBER, DELIVERY_TO_ADDRESS, PAYMENT_DAY, STORE_NUMBER)
    VALUES('003', '3', '東京都千代田区千代田1-1', '', '001');

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('M03', 3, 3);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S01', 3, 1);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S02', 3, 1);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('D03', 3, 1);

--
INSERT INTO ORDERS (ORDER_NUMBER, CUSTOMER_NUMBER, DELIVERY_TO_ADDRESS, PAYMENT_DAY, STORE_NUMBER)
    VALUES('004', '3', '新宿区百人町1-25-4', '', '001');

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('M02', 4, 10);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S01', 4, 10);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S02', 4, 10);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('D01', 4, 10);

--
INSERT INTO ORDERS (ORDER_NUMBER, CUSTOMER_NUMBER, DELIVERY_TO_ADDRESS, PAYMENT_DAY, STORE_NUMBER)
    VALUES('005', '2', '東京都千代田区永田町1-7-1', '', '001');

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('M03', 5, 3);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S01', 5, 3);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S02', 5, 3);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('D01', 5, 3);

--
INSERT INTO ORDERS (ORDER_NUMBER, CUSTOMER_NUMBER, DELIVERY_TO_ADDRESS, PAYMENT_DAY, STORE_NUMBER)
    VALUES('006', '1', '東京都千代田区千代田1-1', '', '001');

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('M03', 6, 3);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S01', 6, 1);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('S02', 6, 1);

INSERT INTO ORDER_DETAILS (item_number, order_number, order_quantity)
    VALUES('D01', 6, 1);