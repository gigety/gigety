CREATE DATABASE IF NOT EXISTS gigety_um; 
CREATE DATABASE IF NOT EXISTS gigety_users; 
GRANT ALL PRIVILEGES ON gigety_users.* to 'gigety'@'%';
GRANT ALL PRIVILEGES ON gigety_um.* to 'gigety'@'%'; 
