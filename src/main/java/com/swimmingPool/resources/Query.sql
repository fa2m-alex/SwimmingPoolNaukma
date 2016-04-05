CREATE TABLE `coach` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `surname` VARCHAR(255) NOT NULL,
  `birthday` DATE NOT NULL
);

CREATE TABLE `competition_type` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `type` VARCHAR(255) NOT NULL
);

CREATE TABLE `discipline` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL
);

CREATE TABLE `competition` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `date` DATE NOT NULL,
  `type_id` INTEGER NOT NULL,
  `discipline_id` INTEGER NOT NULL,
  `is_opened` BOOLEAN
);

CREATE INDEX `idx_competition__discipline_id` ON `competition` (`discipline_id`);

CREATE INDEX `idx_competition__type_id` ON `competition` (`type_id`);

ALTER TABLE `competition` ADD CONSTRAINT `fk_competition__discipline_id` FOREIGN KEY (`discipline_id`) REFERENCES `discipline` (`id`);

ALTER TABLE `competition` ADD CONSTRAINT `fk_competition__type_id` FOREIGN KEY (`type_id`) REFERENCES `competition_type` (`id`);

CREATE TABLE `swimmer` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `surname` VARCHAR(255) NOT NULL,
  `birthday` DATE NOT NULL,
  `growth` INTEGER NOT NULL,
  `coach_id` INTEGER,
  `rating_mark` INTEGER
);

CREATE INDEX `idx_swimmer__coach_id` ON `swimmer` (`coach_id`);

ALTER TABLE `swimmer` ADD CONSTRAINT `fk_swimmer__coach_id` FOREIGN KEY (`coach_id`) REFERENCES `coach` (`id`) ON DELETE SET NULL;

CREATE TABLE `competition_table` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `competition_id` INTEGER NOT NULL,
  `swimmer_id` INTEGER NOT NULL,
  `place` INTEGER NOT NULL,
  `time` INTEGER NOT NULL
);

CREATE INDEX `idx_competition_table__competition_id` ON `competition_table` (`competition_id`);

CREATE INDEX `idx_competition_table__swimmer_id` ON `competition_table` (`swimmer_id`);

ALTER TABLE `competition_table` ADD CONSTRAINT `fk_competition_table__competition_id` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`) ON DELETE CASCADE ;

ALTER TABLE `competition_table` ADD CONSTRAINT `fk_competition_table__swimmer_id` FOREIGN KEY (`swimmer_id`) REFERENCES `swimmer` (`id`);

CREATE TABLE `injury` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `date` DATE NOT NULL,
  `swimmer_id` INTEGER
);

CREATE INDEX `idx_injury__swimmer_id` ON `injury` (`swimmer_id`);

ALTER TABLE `injury` ADD CONSTRAINT `fk_injury__swimmer_id` FOREIGN KEY (`swimmer_id`) REFERENCES `swimmer` (`id`) ON DELETE CASCADE;