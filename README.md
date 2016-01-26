# SnackBot

database.db:

CREATE TABLE "messages" (
	`nick`	TEXT NOT NULL,
	`message`	TEXT NOT NULL
)

CREATE TABLE "seen" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`lastNick`	TEXT NOT NULL,
	`login`	TEXT NOT NULL,
	`hostname`	TEXT NOT NULL,
	`time`	NUMERIC NOT NULL
)

definitions.db

CREATE TABLE `define` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
	`word`	TEXT NOT NULL UNIQUE,
	`definition`	TEXT NOT NULL,
	`setby`	TEXT NOT NULL
)