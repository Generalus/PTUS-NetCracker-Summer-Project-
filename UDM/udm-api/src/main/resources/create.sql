-- Создание таблиц
--create table ABSTRACT_PROPERTY (PROPERTY_ID bigint generated by default as identity, ATTR_ID bigint not null, OBJECT_ID bigint not null, primary key (PROPERTY_ID))
--create table UD_ATTRIBUTE (ATTR_ID bigint generated by default as identity, ABBRIVIATION varchar(255), NAME varchar(255), IS_SYSTEM boolean, ATTR_TYPE_ID bigint not null, primary key (ATTR_ID))
--create table UD_ATTRIBUTE_TYPE (ATTR_TYPE_ID bigint generated by default as identity, ABBRIVIATION varchar(255), ATTR_TYPE_HANDLER varchar(255), NAME varchar(255), ATTR_PARAMS varchar(255), primary key (ATTR_TYPE_ID))
--create table UD_OBJECT (OBJECT_ID bigint generated by default as identity, NAME varchar(255), PARENT_OBJECT_ID bigint, OBJECT_TYPE_ID bigint not null, primary key (OBJECT_ID))
--create table UD_OBJECT_TYPE (OBJECT_TYPE_ID bigint generated by default as identity, ABBRIVIATION varchar(255), NAME varchar(255), PARENT_TYPE_ID bigint, primary key (OBJECT_TYPE_ID))
--create table UD_OBJECT_TYPE_ATTRIBUTES (OBJECT_TYPE_ID bigint not null, ATTR_ID bigint not null, primary key (OBJECT_TYPE_ID, ATTR_ID))
--create table UD_USER_DATA (USER_ID bigint generated by default as identity, USER_ABBRIVIATION varchar(255), USER_LOGIN varchar(255), USER_OBJECT_ID bigint, USER_PASSWORD varchar(255), USER_CATEGORY integer, primary key (USER_ID))
--alter table ABSTRACT_PROPERTY add constraint FK8ttvv08pu0h0ja75jn0h4ldf9 foreign key (ATTR_ID) references UD_ATTRIBUTE
--alter table ABSTRACT_PROPERTY add constraint FK8ufmyqj8bhi6rso5io94ecfe1 foreign key (OBJECT_ID) references UD_OBJECT
--alter table ABSTRACT_PROPERTY add constraint FKdxrdne73a9r4q5ya5gcl689cm foreign key (OBJECT_ID) references UD_ATTRIBUTE
--alter table UD_ATTRIBUTE add constraint FK50mujrft4hhhvgrq9gqefpu0m foreign key (ATTR_TYPE_ID) references UD_ATTRIBUTE_TYPE
--alter table UD_OBJECT add constraint FK3gmti2wxa4eyw5mwemtmish28 foreign key (PARENT_OBJECT_ID) references UD_OBJECT
--alter table UD_OBJECT add constraint FKeinpyt2bho174h45idnucchr0 foreign key (OBJECT_TYPE_ID) references UD_OBJECT_TYPE
--alter table UD_OBJECT_TYPE add constraint FKpnh3tqa43pdmoefibb6q990sk foreign key (PARENT_TYPE_ID) references UD_OBJECT_TYPE
--alter table UD_OBJECT_TYPE_ATTRIBUTES add constraint FK5vs0etx6s607cvmt1nb7gowd9 foreign key (ATTR_ID) references UD_ATTRIBUTE
--alter table UD_OBJECT_TYPE_ATTRIBUTES add constraint FK8vfsdm6vfarq5nttlewnkb0rm foreign key (OBJECT_TYPE_ID) references UD_OBJECT_TYPE
CREATE TABLE UD_ATTRIBUTE_TYPE(ATTR_TYPE_ID BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, NAME VARCHAR(50) NOT NULL, ABBRIVIATION VARCHAR(20) UNIQUE NOT NULL, ATTR_PARAMS VARCHAR(50), ATTR_TYPE_HANDLER VARCHAR(50) NOT NULL)
CREATE TABLE UD_ATTRIBUTE(ATTR_ID BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, ATTR_TYPE_ID BIGINT NOT NULL REFERENCES UD_ATTRIBUTE_TYPE(ATTR_TYPE_ID), ABBRIVIATION VARCHAR(20) UNIQUE NOT NULL, NAME VARCHAR(50) NOT NULL, ATTR_TAGS VARCHAR(50), ATTR_PARAMS VARCHAR(50), IS_SYSTEM BOOLEAN NOT NULL)
CREATE TABLE UD_OBJECT_TYPE(OBJECT_TYPE_ID BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, PARENT_TYPE_ID BIGINT REFERENCES UD_OBJECT_TYPE(OBJECT_TYPE_ID), NAME VARCHAR(50) NOT NULL, ABBRIVIATION VARCHAR(20) UNIQUE, IS_SYSTEM BOOLEAN NOT NULL)
CREATE TABLE UD_OBJECT_TYPE_ATTRIBUTES(OBJECT_TYPE_ID BIGINT REFERENCES UD_OBJECT_TYPE(OBJECT_TYPE_ID), ATTR_ID BIGINT REFERENCES UD_ATTRIBUTE(ATTR_ID), PRIMARY KEY(OBJECT_TYPE_ID,ATTR_ID))
CREATE TABLE UD_OBJECT(OBJECT_ID BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, OBJECT_TYPE_ID BIGINT NOT NULL REFERENCES UD_OBJECT_TYPE(OBJECT_TYPE_ID), PARENT_OBJECT_ID BIGINT REFERENCES UD_OBJECT(OBJECT_ID), NAME VARCHAR(50) NOT NULL, ABBRIVIATION VARCHAR(20), SECURITY_LEVEL INTEGER, OWNER_USER_ID BIGINT)
CREATE TABLE UD_USER_DATA(USER_ID BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, USER_ABBRIVIATION VARCHAR(50) UNIQUE NOT NULL, USER_LOGIN VARCHAR(50) UNIQUE NOT NULL, USER_PASSWORD VARCHAR(50) NOT NULL, USER_OBJECT_ID BIGINT REFERENCES UD_OBJECT(OBJECT_ID), USER_CATEGORY INTEGER NOT NULL)
ALTER TABLE UD_OBJECT ADD CONSTRAINT UD_OBJECT_OWNER_USER_ID_fkey FOREIGN KEY (OWNER_USER_ID) REFERENCES UD_USER_DATA (USER_ID)
CREATE TABLE USER_PERMISSION(OBJECT_ID BIGINT REFERENCES UD_OBJECT(OBJECT_ID), USER_ID BIGINT REFERENCES UD_USER_DATA(USER_ID), OWNER_FLAG BOOLEAN, ACCESS_FLAG BOOLEAN, PRIMARY KEY(OBJECT_ID,USER_ID))
CREATE TABLE ABSTRACT_PROPERTY(OBJECT_ID BIGINT REFERENCES UD_OBJECT(OBJECT_ID), PROPERTY_ID BIGINT, ATTR_ID BIGINT REFERENCES UD_ATTRIBUTE(ATTR_ID), PRIMARY KEY(OBJECT_ID,PROPERTY_ID,ATTR_ID))
CREATE TABLE UD_COUNTER_PROPERTY(PROPERTY_ID BIGINT GENERATED BY DEFAULT AS IDENTITY, ATTR_ID BIGINT REFERENCES UD_ATTRIBUTE(ATTR_ID), DATE TIMESTAMP, VALUE_PROPERTY_ID BIGINT NOT NULL, PRIMARY KEY(PROPERTY_ID,ATTR_ID,DATE))
CREATE TABLE UD_PROPERTY(PROPERTY_ID BIGINT GENERATED BY DEFAULT AS IDENTITY, ATTR_ID BIGINT REFERENCES UD_ATTRIBUTE(ATTR_ID), STRING_VALUE VARCHAR(200), REFERENCE_VALUE BIGINT REFERENCES UD_OBJECT(OBJECT_ID), NUMERIC_VALUE INTEGER, BINARY_VALUE BLOB, CHANGE_DATE TIMESTAMP NOT NULL, CREATE_DATE TIMESTAMP NOT NULL, PRIMARY KEY(PROPERTY_ID,ATTR_ID))