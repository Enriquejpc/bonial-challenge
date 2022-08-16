# bonial - challenge

## API - Goal 
The main goal is develop an API to retrieve every restaurant inside the user range. Moreover, it's necessary expose 2 enpoints to createOrUpdate restaurants and another to retrieve restaurant data by Id

## Architecture
As development pattern I used hexagonal architecture the main advantages are readability, flexibility, decoupling and scalability. As you can see is a model of several layers, the business logic is the same. It does not matter where you have to find the data. An improvement could be replace toDomain and fromDomain methods for mapper interface.
![alt text](docs/hexagonal.png)

## Practices
I have included several design pattern such as Builder, Command, just to name a few.
Clean code and unit testing.
In the future is better add different final variable to application.yml to add properties through configuration beans(Inject properties).

## Development

### Endpoints

1. curl --location --request GET 'http://localhost:8080/api/v1/challenge/locations/search?x=2&y=3'
   **Response**:``` {
   "user-locations": "x=2.0, y=3.0",
   "locations": [
   {
   "id": "19e1545c-8b65-4d83-82f9-7fcad4a23114",
   "name": "Mantra Restaurant",
   "coordinates": "x=2.0, y=2.0",
   "distance": 1.0
   },
   {
   "id": "19e1545c-8b65-4d83-82f9-7fcad4a23115",
   "name": "Goji",
   "coordinates": "x=3.0, y=3.0",
   "distance": 1.0
   }
   ]
   }```

2. curl --location --request PUT 'http://localhost:8080/api/v1/challenge/locations/50e1545c-8c95-4d83-82f9-7fcad4a21120' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "id": "50e1545c-8c95-4d83-82f9-7fcad4a21120",
   "name": "Nuevooo Steakhauseeee",
   "type": "Restaurant",
   "coordinates": "x=24.0, y=24.0",
   "opening-hours": "09:00AM-09:00PM",
   "image": "https://tinyurl.com",
   "radius": 2.0
   }'
   **Response**: ``` {
   "id": "50e1545c-8c95-4d83-82f9-7fcad4a21120",
   "name": "Nuevooo Steakhauseeee",
   "type": "Restaurant",
   "coordinates": "x=24.0, y=24.0",
   "opening-hours": "09:00AM-09:00PM",
   "image": "https://tinyurl.com"
   }```
3. curl --location --request GET 'http://localhost:8080/api/v1/challenge/locations/50e1545c-8c95-4d83-82f9-7fcad4a21120' \
   --data-raw ''


!**important**: To validate the body I used `org.springframework.boot:spring-boot-starter-validation`, so I can add fields/json constraints @Min, @Size, @Positive...

!**important**: controllerAdvice was added for error handling

!**important**: There is an adapter (file) for bulk data, is executed at the beginning of the application. It reads data file from resources folder.

### [Core: businessCase]
To execute e2e flow from controller until repository there are interfaces(interfaces/in, interfaces/out) for each business case in the core. Core has all the business logic. 

### [Repository]
JPA implementation with a simgle entity. My aproach is based in a non-relational database, but for this challenge I chose the traditional implemntation.
IMHO, I'd prefer handle the database in an async(or Events)  to avoid thread lock.

### [Database]
IMHO I think that the best option for this business case is Mongo or Algolia, because a big search could take some time then we need to improve our performance taking advantage of our stack.
I used local H2 spring boot, to achieve the goal I've implemented 2 scripts (schema.sql, data-h2.sql).
Property ddl-auto: validate to check that *.sql are correct. 
schema: challengeDB
user: sa
password: [empty] 

### [Unit test]

To show good practices I've included several unit tests in different layers.

### [Dependency manager]
Gradle

## Local execution
1. Pull from: https://github.com/Enriquejpc/bonial-challenge.git
2. Run application
 ### Docker 
   docker build -t com.bonial/challenge:1.0 .
   docker run -p 8080:8080 com.bonial/challenge:1.0



