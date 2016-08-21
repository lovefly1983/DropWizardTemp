# Users table
CREATE TABLE IF NOT EXISTS `users` (
   `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
   `name` varchar(100) NOT NULL,
   `email` varchar(50) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

# Images table
CREATE TABLE IF NOT EXISTS images (
   `id` INT(11) UNSIGNED NOT NULL,
   `user_id` INT(11) NOT NULL,
   `path` VARCHAR(250) NOT NULL,
   PRIMARY KEY (`id`),
   INDEX `user_id_idx` (`user_id`),
   CONSTRAINT `users_images` FOREIGN KEY (`user_id`) references users(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#Misc
ALTER TABLE `users` ADD unique(`name`);
ALTER TABLE `users` MODIFY COLUMN `name` VARCHAR(100) NOT NULL;
ALTER TABLE `images` MODIFY COLUMN `path` VARCHAR(250) NOT NULL;