CREATE TABLE "Users" (
	"Id" SERIAL,
	"Email" VARCHAR(255),
	"Salt" VARCHAR(255),
	"Hashed Password" VARCHAR(255),
	PRIMARY KEY ("Id")
);
