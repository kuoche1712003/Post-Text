CREATE TABLE `post_text`.`user` (
  `user_id` VARCHAR(25) NOT NULL,
  `name` VARCHAR(50) NULL,
  `credentials` VARCHAR(60) NULL,
  PRIMARY KEY (`user_id`));
CREATE TABLE `post_text`.`follower` (
  `follower_id` VARCHAR(25) NOT NULL,
  `user_id` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`follower_id`, `user_id`));
CREATE TABLE `post_text`.`like` (
  `text_id` INT NOT NULL,
  `user_id` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`text_id`, `user_id`));
CREATE TABLE `post_text`.`text` (
  `text_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(25) NULL,
  `content` TEXT NULL,
  `post_time` TIMESTAMP NULL,
  PRIMARY KEY (`text_id`));
CREATE TABLE `post_text`.`comment` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `text_id` INT NULL,
  `user_id` VARCHAR(25) NULL,
  `content` TEXT NULL,
  `reply_time` TIMESTAMP NULL,
  PRIMARY KEY (`comment_id`));