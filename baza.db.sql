BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "User" (
	"UserID"	INTEGER,
	"UserName"	TEXT,
	"Password"	TEXT,
	PRIMARY KEY("UserID")
);
CREATE TABLE IF NOT EXISTS "Weight" (
	"Date"	TEXT,
	"UserID"	INTEGER,
	"Weight"	REAL
);
CREATE TABLE IF NOT EXISTS "Comment" (
	"CommentID"	INTEGER,
	"Comment"	TEXT,
	PRIMARY KEY("CommentID")
);
INSERT INTO "User" VALUES (1,'User','1234');
INSERT INTO "User" VALUES (2,'Korisnik','123');
INSERT INTO "User" VALUES (3,'ime','password');
INSERT INTO "Weight" VALUES ('04.02.2023.',1,89.0);
INSERT INTO "Weight" VALUES ('02.02.2023.',1,55.0);
INSERT INTO "Weight" VALUES ('13.3.2023',1,54.0);
INSERT INTO "Weight" VALUES ('22.4.2021',1,44.0);
INSERT INTO "Weight" VALUES ('8.2.2023.',1,123.0);
INSERT INTO "Weight" VALUES ('9.2.2023.',1,88.7);
INSERT INTO "Weight" VALUES ('10.2.2023.',1,76.0);
INSERT INTO "Weight" VALUES ('10.2.2023.',1,99.0);
INSERT INTO "Weight" VALUES ('1.2.2023.',1,123.0);
INSERT INTO "Weight" VALUES ('12.12.2012.',3,123.0);
INSERT INTO "Weight" VALUES ('12.12.2012.',3,123.0);
INSERT INTO "Weight" VALUES ('12.12.2012.',3,123.0);
INSERT INTO "Comment" VALUES (1,'You got a bit of weight');
INSERT INTO "Comment" VALUES (1,'Great progress');
INSERT INTO "Comment" VALUES (1,'Keep going');
COMMIT;
