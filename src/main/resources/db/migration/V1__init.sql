create table users (
    id bigserial primary key,
    username varchar(100) not null unique,
    password varchar(255) not null,
    role varchar(20) not null,
    enabled boolean not null default true,
    created_at timestamp not null
);

create table members (
    id bigserial primary key,
    full_name varchar(150) not null,
    phone varchar(20) not null unique,
    email varchar(150),
    gender varchar(20),
    dob date,
    address varchar(255),
    joined_date date not null,
    status varchar(20) not null
);

create table membership_plans (
    id bigserial primary key,
    name varchar(100) not null unique,
    duration_days integer not null,
    price numeric(12,2) not null,
    description varchar(255),
    is_active boolean not null default true
);

create table memberships (
    id bigserial primary key,
    member_id bigint not null references members(id),
    plan_id bigint not null references membership_plans(id),
    start_date date not null,
    end_date date not null,
    status varchar(20) not null,
    created_at timestamp not null
);

create table payments (
    id bigserial primary key,
    member_id bigint not null references members(id),
    membership_id bigint not null references memberships(id),
    amount_paid numeric(12,2) not null,
    payment_date date not null,
    method varchar(20) not null,
    remarks varchar(255),
    created_at timestamp not null
);

create table attendance (
    id bigserial primary key,
    member_id bigint not null references members(id),
    date date not null,
    check_in_time time,
    check_out_time time,
    marked_by_user_id bigint references users(id),
    created_at timestamp not null,
    unique(member_id, date)
);
