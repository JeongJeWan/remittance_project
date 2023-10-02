CREATE TABLE `users_status` (
                                `status_code` varchar(10) NOT NULL,
                                `name` varchar(10) NOT NULL,

                                PRIMARY KEY(`status_code`)
);

CREATE TABLE `users` (
                         `user_id` bigint NOT NULL auto_increment,
                         `status_code` varchar(10) NOT NULL,
                         `username` varchar(30) NOT NULL,
                         `balance` int NOT NULL,
                         `max_limit` int NOT NULL,

                         PRIMARY KEY(`user_id`),
                         FOREIGN KEY (`status_code`) REFERENCES `users_status`(`status_code`)
);

CREATE TABLE `remittance` (
                              `remittance_id` bigint NOT NULL auto_increment,
                              `sender_id` bigint NOT NULL,
                              `receiver_id` bigint NOT NULL,
                              `amount` int NOT NULL,
                              `created_at` timestamp NOT NULL,

                              PRIMARY KEY (`remittance_id`),
                              FOREIGN KEY (`sender_id`) REFERENCES `users`(`user_id`),
                              FOREIGN KEY (`receiver_id`) REFERENCES `users`(`user_id`)
)


