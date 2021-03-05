#!/bin/bash
service mysql start
mysql -h localhost -uroot -paC8Jhd12U3 < db-config.sql
mysql -h localhost -unacos-test -p34PzNawBHfcr6bJhq6jj < initialize.sql
tail -f /dev/null
