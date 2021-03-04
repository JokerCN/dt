CREATE USER 'nacos-test'@'%' IDENTIFIED BY '34PzNawBHfcr6bJhq6jj';
CREATE SCHEMA IF NOT EXISTS `nacos-test-metrics`;
GRANT ALL ON `nacos-test-metrics`.* TO 'nacos-test'@'%';